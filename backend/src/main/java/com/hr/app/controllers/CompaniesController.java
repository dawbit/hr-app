package com.hr.app.controllers;

import com.hr.app.enums.FileType;
import com.hr.app.ftp.FileStorageService;
import com.hr.app.models.api_helpers.FileCorrectness;
import com.hr.app.models.api_helpers.RegisterCompanyCommandDto;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.models.dto.UploadFileResponse;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class CompaniesController {

    private final String serviceUrlParam = "/companies";

    @Autowired
    private ICompaniesRepository companiesRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private ICeosRepository ceosRepository;

    @Autowired
    private IAccountTypesRepository accountTypesRepository;

    @Autowired
    private IHrUsersRepository hrUsersRepository;

    @Autowired
    private FileStorageService fileStorageService;

    //TODO paging
    @GetMapping("/companies/all")
    public Object getAllCompanies(HttpServletResponse response) {
        List<CompaniesModel> companiesModelList;
        try {
            companiesModelList = companiesRepository.findAll();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }

        if(!companiesModelList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_OK);
            return companiesModelList;
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ResponseTransfer("Companies not found");
        }
    }

    // /companies/find?q='example'
    @GetMapping(serviceUrlParam + "/find")
    public List<CompaniesModel> getAllCompaniesByAntything(@RequestParam String q) {
        return companiesRepository.findCompanyByAnything(q);
    }

    @Transactional
    @PostMapping(serviceUrlParam + "/add")
    public ResponseTransfer addCompanies(@RequestBody RegisterCompanyCommandDto registerCompanyCommandDto, HttpServletResponse response) {

        UsersModel usersModel;
        if(registerCompanyCommandDto.getName().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("NAME_IS_EMPTY");
        }
        if(registerCompanyCommandDto.getLocation().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("LOCATION_IS_EMPTY");
        }
        if(registerCompanyCommandDto.getAbout().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("ABOUT_IS_EMPTY");
        }
        CompaniesModel companiesModel=new CompaniesModel(registerCompanyCommandDto);
        try {
            usersModel = getUsersModel();

            if(companyExists(registerCompanyCommandDto.getName())) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return new ResponseTransfer("COMPANY_NAME_EXISTS");
            }
            if(usersModel.getFKuserAccountTypes().getRoleId() == 1) {
                companiesRepository.save(companiesModel);
            } else {
                AccountTypesModel accountTypesModel = getRoleById(2);
                usersModel.setFKuserAccountTypes(accountTypesModel);
                usersRepository.save(usersModel);
                companiesRepository.save(companiesModel);
                CeosModel ceosModel = new CeosModel(usersModel, companiesModel);
                ceosRepository.save(ceosModel);
            }
            return new ResponseTransfer("Company successfully saved");
        } catch (Exception e ){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }
    }

    @GetMapping(serviceUrlParam + "/companyid/{id}")
    public Object getCompanyById(@PathVariable int id, HttpServletResponse response) {

        CompaniesModel companiesModel;

        try {
            companiesModel = getCompanyById(id);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }

        if(companiesModel==null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ResponseTransfer("Company not found");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            return companiesModel;
        }
    }

    //TODO paging
    @GetMapping(serviceUrlParam + "companyname/{name}")
    public Object getCompaniesByName(@PathVariable String name, HttpServletResponse response) {
        List<CompaniesModel> companiesModelList;

        try {
            companiesModelList = getCompanyByName(name);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }
        if(companiesModelList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ResponseTransfer("Company not found");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            return companiesModelList;
        }
    }

    @GetMapping(serviceUrlParam + "/current")
    public String getCurrentCompany(HttpServletResponse response) {
        long userId = getUsersModel().getId();
        HrUsersModel hrUsersModel = hrUsersRepository.findByFKhrUserUserId(userId);
        CeosModel ceosModel = ceosRepository.findByFKceoUserId(userId);
        String companyName = "";
        if(Objects.nonNull(hrUsersModel) && Objects.nonNull(hrUsersModel.getFKhrUserCompany())){
            companyName = hrUsersModel.getFKhrUserCompany().getName();
        } else if (Objects.nonNull(ceosModel) && Objects.nonNull(ceosModel.getFKceoCompany())) {
            companyName = ceosModel.getFKceoCompany().getName();
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); //204
        }
        return companyName;
    }

    @Transactional
    @PostMapping(serviceUrlParam + "/upload-company-image")
    Object uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        UsersModel currentUser;
        CompaniesModel companiesModel;
        try {
            currentUser = getUsersModel();
            CeosModel ceosModel = ceosRepository.findByFKceoUserId(currentUser.getId());
            HrUsersModel hrUsersModel = hrUsersRepository.findByFKhrUserUserId(currentUser.getId());
            if(ceosModel != null) {
                companiesModel = ceosModel.getFKceoCompany();
            } else if(hrUsersModel== null) {
                companiesModel = hrUsersModel.getFKhrUserCompany();
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return new ResponseTransfer("NOT_ALLOWED");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }

        if(file == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("File is required");
        }
        if(!(FileCorrectness.fileExtensionisCorrect(file, "jpg") || !FileCorrectness.fileExtensionisCorrect(file, "png"))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("ONLY JPG AND PNG ALLOWED");
        }
        if(!FileCorrectness.fileSizeIsOk(file)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("File is too big");
        }

        String fileName;
        String fileDownloadUri;

        try {
            fileName = fileStorageService.storeFile(file, companiesModel.getName(), FileType.COMPANY_IMAGE);

            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            companiesModel.setImageUrl(fileDownloadUri);
            companiesRepository.save(companiesModel);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    private CompaniesModel getCompanyById(long id) {
        return companiesRepository.findById(id);
    }

    private List<CompaniesModel> getCompanyByName(String companyName) {
        return companiesRepository.findAllByName(companyName);
    }

    private UsersModel getUsersModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private AccountTypesModel getRoleById(long id) {
        return accountTypesRepository.findByRoleId(id);
    }

    private boolean companyExists(String name) {
        return companiesRepository.findByName(name) != null;
    }

}

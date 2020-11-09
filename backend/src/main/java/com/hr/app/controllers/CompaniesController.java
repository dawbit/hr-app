package com.hr.app.controllers;

import com.hr.app.models.database.*;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public ResponseTransfer addCompanies(@RequestBody CompaniesModel companiesModel, HttpServletResponse response) {

        UsersModel usersModel;

        try {
            usersModel = getUsersModel();

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

}

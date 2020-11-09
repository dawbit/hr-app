package com.hr.app.controllers;

import com.hr.app.models.database.AccountTypesModel;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.models.database.CeosModel;
import com.hr.app.models.database.CompaniesModel;
import com.hr.app.models.database.UsersModel;
import com.hr.app.repositories.IAccountTypesRepository;
import com.hr.app.repositories.ICeosRepository;
import com.hr.app.repositories.ICompaniesRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@RestController
public class CompaniesController {

    @Autowired
    private ICompaniesRepository companiesRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private ICeosRepository ceosRepository;

    @Autowired
    private IAccountTypesRepository accountTypesRepository;

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
    @GetMapping("/companies/find")
    public List<CompaniesModel> getAllCompaniesByAntything(@RequestParam String q) {
        return companiesRepository.findCompanyByAnything(q);
    }

    @Transactional
    @PostMapping("/companies/add")
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

    @GetMapping("/companies/companyid/{id}")
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
    @GetMapping("/companies/companyname/{name}")
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

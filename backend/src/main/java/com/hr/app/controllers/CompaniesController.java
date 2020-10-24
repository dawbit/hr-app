package com.hr.app.controllers;

import com.hr.app.models.api_helpers.ResponseTransfer;
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

    @GetMapping("/companies/all")
    public List<CompaniesModel> getAllCompanies() {
        System.out.println("ss");
        return companiesRepository.findAll();
    }

    @GetMapping("/companies/find")
    public List<CompaniesModel> getAllCompaniesByAntything(@RequestParam String q) {
        return companiesRepository.findCompanyByAnything(q);
    }

    @Transactional
    @PostMapping("companies/add")
    public ResponseTransfer addCompanies(@RequestBody CompaniesModel companiesModel) {
        CompaniesModel company = companiesModel;
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        UsersModel user = usersRepository.findByLogin(name);
        try {
            if (getCompanyById(company.getId()) != null) {
                return new ResponseTransfer("nie udalo sie");
            } else {
                companiesRepository.save(company);
                if(user.getFKuserAccountTypes().getRoleId()!=1) {
                    user.setFKuserAccountTypes(accountTypesRepository.findByRoleId(2));
                }
                ceosRepository.save(new CeosModel(user, company));
                return new ResponseTransfer("Udalo sie");
            }
        }
        catch (Exception e) {
            return new ResponseTransfer("nie udalo sie");
        }
    }

    private CompaniesModel getCompanyById(long id) {
        return companiesRepository.findById(id);
    }

}

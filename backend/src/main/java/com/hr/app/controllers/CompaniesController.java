package com.hr.app.controllers;

import com.hr.app.models.CeosModel;
import com.hr.app.models.CompaniesModel;
import com.hr.app.models.ResponseTransfer;
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
    ICompaniesRepository companiesRepository;

    @Autowired
    IUsersRepository usersRepository;

    @Autowired
    ICeosRepository ceosRepository;

    @GetMapping("/companies/all")
    public List<CompaniesModel> getAllCompanies() {
        return companiesRepository.findAll();
    }

    @Transactional
    @PostMapping("companies/add")
    public ResponseTransfer addCompanies(@RequestBody CompaniesModel companiesModel) {
        CompaniesModel company = companiesModel;
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            if (getCompanyById(company.getId()) != null) {
                return new ResponseTransfer("nie udalo sie");
            } else {
                companiesRepository.save(company);
                ceosRepository.save(new CeosModel(usersRepository.findByLogin(name), company));
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

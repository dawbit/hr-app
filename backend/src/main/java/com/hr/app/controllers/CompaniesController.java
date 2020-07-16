package com.hr.app.controllers;

import com.hr.app.models.CompaniesModel;
import com.hr.app.repositories.ICompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CompaniesController {

    @Autowired
    ICompaniesRepository companiesRepository;

    @GetMapping("/companies/all")
    public List<CompaniesModel> getAllCompanies() {
        return companiesRepository.findAll();
    }

    @PostMapping("companies/add")
    public CompaniesModel addCompanies(@RequestBody CompaniesModel companiesModel) {
        try {
            if (getCompanyById(companiesModel.getId()) != null) {
                return null;
            } else {
                return companiesRepository.save(companiesModel);
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    private CompaniesModel getCompanyById(long id) {
        return companiesRepository.findById(id);
    }

}

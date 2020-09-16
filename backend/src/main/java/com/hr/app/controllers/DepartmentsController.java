package com.hr.app.controllers;

import com.hr.app.models.*;
import com.hr.app.repositories.ICeosRepository;
import com.hr.app.repositories.ICompaniesRepository;
import com.hr.app.repositories.IDepartmentsRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class DepartmentsController {

    @Autowired
    IDepartmentsRepository departmentsRepository;

    @Autowired
    IUsersRepository usersRepository;

    @Autowired
    ICeosRepository ceosRepository;

    @Autowired
    ICompaniesRepository companiesRepository;

    @PostMapping("departments/add")
    public ResponseTransfer addDepartment(@RequestBody DepartmentsModel departmentsModel){
        DepartmentsModel department = departmentsModel;
        CompaniesModel company = getCompany();

        if(company==null){
            return new ResponseTransfer("Nie można");
        }
        else {
            department.setFKdepartmentCompany(company);
            departmentsRepository.save(department);
            return new ResponseTransfer("udało się");
        }
    }

    private CompaniesModel getCompany(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        try{
            UsersModel user = usersRepository.findByLogin(name);
            CeosModel ceo = ceosRepository.findByFKceoUserId(user.getId());
            return companiesRepository.findById(ceo.getFKceoCompany().getId());
        }
        catch (Exception e){
            return null;
        }
    }

}

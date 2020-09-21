package com.hr.app.controllers;

import com.hr.app.models.*;
import com.hr.app.repositories.*;
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

    @Autowired
    IHrUsersRepository hrUsersRepository;

    @PostMapping("departments/add")
    public ResponseTransfer addDepartment(@RequestBody DepartmentsModel departmentsModel){
        DepartmentsModel department = departmentsModel;
        CompaniesModel company = getCompany();

        if(company==null){
            return new ResponseTransfer("Nie można");
        }
        else {
            try {
                department.setFKdepartmentCompany(company);
                departmentsRepository.save(department);
                return new ResponseTransfer("udało się");
            }
            catch (Exception e) {
                return new ResponseTransfer("Nie powiodło się dodawanie");
            }
        }
    }

    @PostMapping("departments/adduser")
    public ResponseTransfer addUserToDepartment(@RequestBody UserToDepartmentModel userToDepartmentModel){

        DepartmentsModel departmentsModel;
        try{
            departmentsModel = departmentsRepository.findById(userToDepartmentModel.getDepartmentId());
        } catch (Exception e ){
            return new ResponseTransfer("cos poszlo nie tak");
        }

        if(departmentsModel.getFKdepartmentCompany().getId() != getCompany().getId()){
            return new ResponseTransfer("nie masz praw");
        }
        else {
            try{
                HrUsersModel user  = hrUsersRepository.findByFKhrUserUserId(userToDepartmentModel.getUserId());
                user.setFKhrUserDepartment(departmentsModel);
                hrUsersRepository.save(user);
                return new ResponseTransfer("udało się");
            }
            catch (Exception e ) {
                return new ResponseTransfer("cos poszlo nie tak");
            }
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

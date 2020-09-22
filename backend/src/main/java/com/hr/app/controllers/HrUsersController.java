package com.hr.app.controllers;

import com.hr.app.models.api_helpers.ResponseTransfer;
import com.hr.app.models.database.CeosModel;
import com.hr.app.models.database.CompaniesModel;
import com.hr.app.models.database.HrUsersModel;
import com.hr.app.repositories.ICeosRepository;
import com.hr.app.repositories.IHrUsersRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class HrUsersController {

    @Autowired
    IHrUsersRepository hrUsersRepository;

    @Autowired
    IUsersRepository usersRepository;

    @Autowired
    ICeosRepository ceosRepository;

    @PostMapping("hrusers/add")
    public ResponseTransfer addNewHrUser(@RequestBody long userId){

        CompaniesModel company = getCompany();
        if(company==null){
            return new  ResponseTransfer("Nie masz uprawnień");
        }
        else {
            try {
                hrUsersRepository.save(new HrUsersModel(usersRepository.findById(userId), company));
                return new ResponseTransfer("udało się");
            } catch (Exception e){
                return new ResponseTransfer("nie udalo sie");
            }
        }
    }

    private CompaniesModel getCompany(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            CeosModel ceo = ceosRepository.findByFKceoUserId(usersRepository.findByLogin(name).getId());
            return ceo.getFKceoCompany();
        }
        catch (Exception e){
            return null;
        }
    }
}

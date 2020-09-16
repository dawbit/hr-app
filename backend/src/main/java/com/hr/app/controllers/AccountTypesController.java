package com.hr.app.controllers;

import com.hr.app.models.AccountTypesModel;
import com.hr.app.models.QuizModel;
import com.hr.app.models.ResponseTransfer;
import com.hr.app.models.UsersModel;
import com.hr.app.repositories.IAccountTypesRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AccountTypesController {

    @Autowired
    private IAccountTypesRepository accountTypesRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @PostMapping("accounttype/add")
    public ResponseTransfer addAccountType(@RequestBody AccountTypesModel accountTypesModel) {

        if(checkIfUserIsAdmin()){
            try{
                accountTypesRepository.save(accountTypesModel);
            }
            catch (Exception e){
                return new ResponseTransfer("Nie udało się zapisać");
            }
            return new ResponseTransfer("Udało się");
        }
        else {
            return new ResponseTransfer("Nie masz uprawnień");
        }
    }

    private boolean checkIfUserIsAdmin(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountTypesModel accountTypeModel;

        try{
            long id = usersRepository.findByLogin(name).getFKuserAccountTypes().getRoleId();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}

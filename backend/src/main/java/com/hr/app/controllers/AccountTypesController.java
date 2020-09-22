package com.hr.app.controllers;

import com.hr.app.models.database.AccountTypesModel;
import com.hr.app.models.api_helpers.ResponseTransfer;
import com.hr.app.repositories.IAccountTypesRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class AccountTypesController {

    @Autowired
    private IAccountTypesRepository accountTypesRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @PostMapping("accounttype/add")
    public ResponseTransfer addAccountType(@RequestBody AccountTypesModel accountTypesModel, HttpServletResponse response) {
        if(checkIfUserIsAdmin()){
            try{
                if(checkIfAccountTypeIdOdNameAlreadyExists(accountTypesModel)){
                    response.setStatus(HttpServletResponse.SC_CONFLICT); //409
                    return new ResponseTransfer("Account type id or name already exists");
                }
                else {
                    response.setStatus(HttpServletResponse.SC_OK); //200
                    accountTypesRepository.save(accountTypesModel);
                    return new ResponseTransfer("Saved successfully");
                }
            }
            catch (Exception e){
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                return new ResponseTransfer("Something went wrong");
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); //409
            return new ResponseTransfer("No permission");
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

    private boolean checkIfAccountTypeIdOdNameAlreadyExists(AccountTypesModel accountTypesModel){
        return (accountTypesRepository.findByRoleId(accountTypesModel.getRoleId())!=null
                ||
                accountTypesRepository.findByRoleName(accountTypesModel.getRoleName()) != null);
    }
}

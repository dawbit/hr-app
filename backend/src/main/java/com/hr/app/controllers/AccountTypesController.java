package com.hr.app.controllers;

import com.hr.app.models.database.AccountTypesModel;
import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.ResponseTransfer;
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

    @PostMapping("/accounttype/add")
    public ResponseTransfer addAccountType(@RequestBody AccountTypesModel accountTypesModel, HttpServletResponse response) {

        AccountTypesModel accountTypesModelById;
        AccountTypesModel accountTypesModelByName;

        try {
            accountTypesModelById = getAccountTypesModelById(accountTypesModel);
            accountTypesModelByName = getAccountTypesModelByName(accountTypesModel);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }
        if(accountTypesModelById != null || accountTypesModelByName != null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT); //409
            return new ResponseTransfer("Account type id or name already exists");
        } else {
            accountTypesRepository.save(accountTypesModel);
            response.setStatus(HttpServletResponse.SC_OK); //200
            return new ResponseTransfer("Saved successfully");
        }
    }

    private AccountTypesModel getAccountTypesModelById(AccountTypesModel accountTypesModel) {
        return accountTypesRepository.findByRoleId(accountTypesModel.getRoleId());
    }

    private AccountTypesModel getAccountTypesModelByName(AccountTypesModel accountTypesModel) {
        return accountTypesRepository.findByRoleName(accountTypesModel.getRoleName());
    }
}

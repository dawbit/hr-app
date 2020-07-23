package com.hr.app.controllers;

import com.hr.app.models.ResponseTransfer;
import com.hr.app.models.UsersModel;
import com.hr.app.repositories.IAccountTypesRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;


@CrossOrigin
@RestController
public class UsersController {
    @Autowired
    EntityManager entityManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IAccountTypesRepository accountTypes;

    @Autowired
    IUsersRepository user;

    @PostMapping("/user/register")
    @ResponseBody
    public ResponseTransfer saveUser(@RequestBody UsersModel userModel) {
        try {
            if (getUserByIdForSave(userModel.getId()) != null || doesUserExist(userModel.getLogin()) ||
                doesUserExist(userModel.getEmail())) {
                return new ResponseTransfer("User already exists");
            }
            else {
                userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
                user.save(userModel);
                return new ResponseTransfer("User saved");
            }
        }
        catch (Exception exc) {
            return new ResponseTransfer("Not saved", exc.toString());
        }
    }

    private UsersModel getUserByIdForSave(long id) {
        return user.findById(id);
    }

    private boolean doesUserExist(String login) {
        return (user.findByLogin(login) != null);
    }
}

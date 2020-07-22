package com.hr.app.controllers;

import com.hr.app.models.UsersModel;
import com.hr.app.repositories.IAccountTypesRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    //@Transactional
    public String saveUser(@RequestBody UsersModel userModel) {
        try {
            if (getUserByIdForSave(userModel.getId()) != null || doesUserExist(userModel.getLogin()) ) {
                return "User already exists, or incorrect input format";
            }
            else {
                userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
                user.save(userModel);
                return "User saved";
            }
        }
        catch (Exception exc) {
            return "Not saved. Exception: " + exc.getMessage();
        }
    }

    private UsersModel getUserByIdForSave(long id) {
        return user.findById(id);
    }

    private boolean doesUserExist(String login) {
        return (user.findByLogin(login) != null);
    }
}

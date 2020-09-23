package com.hr.app.controllers;

import com.hr.app.models.api_helpers.ResponseTransfer;
import com.hr.app.models.database.UsersModel;
import com.hr.app.repositories.IAccountTypesRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@CrossOrigin
@RestController
public class UsersController {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAccountTypesRepository accountTypes;

    @Autowired
    private IUsersRepository usersRepository;

    @PostMapping("/user/register")
    @ResponseBody
    public ResponseTransfer saveUser(@RequestBody UsersModel userModel, HttpServletResponse response) {
        try {
            if (getUserByIdForSave(userModel.getId()) != null || doesUserExist(userModel.getLogin()) ||
                doesUserExist(userModel.getEmail())) {
                response.setStatus(HttpServletResponse.SC_CONFLICT); // ERROR 409
                return new ResponseTransfer("User already exists");
            }
            else {
                userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
                usersRepository.save(userModel);
                return new ResponseTransfer("User saved");
            }
        }
        catch (Exception exc) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // ERROR 500
            return new ResponseTransfer("Not saved", exc.toString());
        }
    }

        @GetMapping("users/getall")
    public List<UsersModel> getQuiz(){
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            UsersModel user = usersRepository.findByLogin(name);

            if(user.getFKuserAccountTypes().getRoleId()!=1){
                return null;
            }
            else {
                List<UsersModel> allUsers = usersRepository.findAll();
                for(UsersModel object : allUsers) {
                    object.setFKuserAccountTypes(null);
                }
                return usersRepository.findAll();
            }
    }


    private UsersModel getUserByIdForSave(long id) {
        return usersRepository.findById(id);
    }

    private boolean doesUserExist(String login) {
        return (usersRepository.findByLogin(login) != null);
    }
}

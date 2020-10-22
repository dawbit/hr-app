package com.hr.app.controllers;

import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.repositories.IHrUsersRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ModelsTesterController {

    @Autowired
    private IUsersRepository usersRepository;

    @GetMapping("tester/get")
    public UsersModel addQuiz() {
        return usersRepository.findById(1);
    }
}

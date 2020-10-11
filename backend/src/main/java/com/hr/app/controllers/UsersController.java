package com.hr.app.controllers;

import com.hr.app.models.api_helpers.ResponseTransfer;
import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.UserDto;
import com.hr.app.repositories.IAccountTypesRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
public class UsersController {

    private final String serviceUrlParam = "/user";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAccountTypesRepository accountTypes;

    @Autowired
    private IUsersRepository usersRepository;

    @PostMapping(serviceUrlParam + "/register")
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

    @GetMapping(serviceUrlParam + "/getall")
    public ArrayList<UserDto> getQuiz(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UsersModel user = usersRepository.findByLogin(name);

        if(user.getFKuserAccountTypes().getRoleId()!=1){
            return null;
        }
        else {
            ArrayList<UserDto> listOfUserDto = new ArrayList<>();
            List<UsersModel> allUsers = usersRepository.findAll();
            for (UsersModel usermodel : allUsers) {
                listOfUserDto.add(new UserDto(usermodel));
            }
            return listOfUserDto;
        }
    }

    @GetMapping(serviceUrlParam + "/getUser/{userid}")
    public UserDto getUser(@PathVariable long userid){
        UsersModel user = usersRepository.findById(userid);
        return new UserDto(user);
    }

    @PutMapping(serviceUrlParam + "/edituser")
    public ResponseTransfer updateUser(@RequestBody UserDto userDto) {
        usersRepository.save(new UsersModel(userDto));
        return new ResponseTransfer("udało się albo nie");
    }

    @DeleteMapping(serviceUrlParam + "/deleteuser/{userid}")
    public ResponseTransfer deleteUser(@PathVariable long userid) {
        UsersModel user = usersRepository.findById(userid);
        usersRepository.delete(user);
        return new ResponseTransfer("udało się albo nie");
    }


    private UsersModel getUserByIdForSave(long id) {
        return usersRepository.findById(id);
    }

    private boolean doesUserExist(String login) {
        return (usersRepository.findByLogin(login) != null);
    }
}

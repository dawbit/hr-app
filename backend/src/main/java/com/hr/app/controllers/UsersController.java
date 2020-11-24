package com.hr.app.controllers;

import com.hr.app.mails.CustomMailing;
import com.hr.app.models.api_helpers.DeleteUserCommandDto;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.models.dto.UserDataWithCvDto;
import com.hr.app.models.dto.UserResultDto;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
    private ICeosRepository ceosRepository;

    @Autowired
    private IAccountTypesRepository accountTypes;

    @Autowired
    private ICompaniesRepository companiesRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IHrUsersRepository hrUsersRepository;

    @Autowired
    private CustomMailing sendMail;

    @Autowired
    private ICvsRepository cvsRepository;


    @PostMapping(serviceUrlParam + "/register")
    @ResponseBody
    public ResponseTransfer saveUser(@RequestBody UsersModel userModel, HttpServletResponse response) {
        try {
            if (getUserById(userModel.getId()) != null || doesUserExist(userModel.getLogin(), userModel.getEmail())) {
                response.setStatus(HttpServletResponse.SC_CONFLICT); // ERROR 409
                return new ResponseTransfer("User already exists");
            }
            else {
                userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
                userModel.setFKuserMailing(new MailingModel());
                usersRepository.saveAndFlush(userModel);
                sendMail.sendRegistrationMessage(userModel.getEmail(), userModel.getLogin());
                return new ResponseTransfer("User saved");
            }
        }
        catch (Exception exc) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // ERROR 500
            return new ResponseTransfer("Not saved", exc.toString());
        }
    }

    @GetMapping(serviceUrlParam + "/getall")
    public Object getAllUsers(HttpServletResponse response){

        List<UsersModel> usersModelsList;
        try {
            usersModelsList = usersRepository.findAll();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }

        ArrayList<UserResultDto> listOfUserResultDto = new ArrayList<>();
        for (UsersModel usermodel : usersModelsList) {
            listOfUserResultDto.add(new UserResultDto(usermodel));
        }
        return listOfUserResultDto;
    }

    @GetMapping(serviceUrlParam + "/getUser/{userid}")
    public Object getUser(@PathVariable long userid, HttpServletResponse response){
        try {
            UsersModel user = usersRepository.findById(userid);
            CvsModel cv = cvsRepository.findByFKcvUserId(user.getId());
            return new UserDataWithCvDto(user, cv.getFileName());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }
    }

    //TODO check if needed and delete
    @PutMapping(serviceUrlParam + "/edituser")
    public ResponseTransfer updateUser(@RequestBody UserResultDto userResultDto, HttpServletResponse response) {
        usersRepository.save(new UsersModel(userResultDto));
        return new ResponseTransfer("udało się albo nie");
    }

    //TODO to jest cieżkie XD trzeba wszystkie powiązania pousuwać. Np odpowiedzi tego usera, nullować quizy które zrobił itd.
    //TODO naraizeni eni działa poprawnie
    @Transactional
    @DeleteMapping(serviceUrlParam + "/deleteuser")
    public ResponseTransfer deleteUser(@RequestParam DeleteUserCommandDto deleteUserCommandDto, HttpServletResponse response) {
        UsersModel userToDelete;
        try {
            userToDelete = getUserById(deleteUserCommandDto.getId());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }
        try {
            if(userToDelete.getFKuserAccountTypes().getRoleId() == 1) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return new ResponseTransfer("You cannot delete admin");
            }
            else if(userToDelete.getFKuserAccountTypes().getRoleId() == 2) {
                CeosModel ceosModel;
                CompaniesModel companiesModel;
                ceosModel = getCeosModelByUser(userToDelete);
                ceosRepository.delete(ceosModel);

            }
            else if (userToDelete.getFKuserAccountTypes().getRoleId() == 3) {
                HrUsersModel hrUsersModel = getHrUser(userToDelete);
            }
            else if (userToDelete.getFKuserAccountTypes().getRoleId() == 4) {

            }
            usersRepository.delete(userToDelete);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new ResponseTransfer("User deleted successfully");
    }

    private UsersModel getUserById(long id) {
        return usersRepository.findById(id);
    }

    private boolean doesUserExist(String login, String email) {
        return (usersRepository.findByLoginOrEmail(login, email) != null);
    }

    private UsersModel getUsersModel(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private HrUsersModel getHrUser(UsersModel usersModel) {
        return hrUsersRepository.findByFKhrUserUserId(usersModel.getId());
    }

    private CeosModel getCeosModelByUser(UsersModel usersModel) {
        return ceosRepository.findByFKceoUserId(usersModel.getId());
    }
}

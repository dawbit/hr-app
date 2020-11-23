package com.hr.app.controllers;

import com.hr.app.models.api_helpers.AddNewHrCommandDto;
import com.hr.app.models.database.AccountTypesModel;
import com.hr.app.models.database.CeosModel;
import com.hr.app.models.database.HrUsersModel;
import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.models.dto.UserResultDto;
import com.hr.app.repositories.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class CeosController {

    private final String serviceUrlParam = "/ceo-panel";

    @Autowired
    private IHrUsersRepository hrUsersRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private ICompaniesRepository companiesRepository;

    @Autowired
    private ICeosRepository ceosRepository;

    @Autowired
    private IAccountTypesRepository accountTypesRepository;


    @GetMapping(serviceUrlParam + "/getusers")
    public Object getAllUsers(HttpServletResponse response){
        try {
            List<UserResultDto> listOfUserResultDto = findUser(usersRepository.findNonHrUsers());
            response.setStatus(HttpServletResponse.SC_OK); // 200
            return listOfUserResultDto;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            return new ResponseTransfer("Internal server error");
        }
    }

    @GetMapping(serviceUrlParam + "/getusers/our")
    public Object getOurHrUsers(HttpServletResponse response){
        UsersModel usersModel;
        CeosModel ceosModel;
        try {
            usersModel = getUsersModel();
            ceosModel = getCeosModelByOwnerId(usersModel.getId());
            List<UserResultDto> listOfUserResultDto = findUser(
                    usersRepository.findOurHrUsers(ceosModel.getFKceoCompany().getId())
            );
            response.setStatus(HttpServletResponse.SC_OK); // 200
            return listOfUserResultDto;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            return new ResponseTransfer("Internal server error");
        }
    }

    @Transactional
    @PostMapping(serviceUrlParam + "/hrusers/add")
    public ResponseTransfer addNewHrUser(@RequestBody AddNewHrCommandDto addNewHrCommandDto,
                                         HttpServletResponse response){
        UsersModel usersModel;
        CeosModel ceosModel;
        UsersModel userToBecomeHr;
        try {
            usersModel = getUsersModel();
            ceosModel = getCeosModelByOwnerId(usersModel.getId());
            userToBecomeHr = getUserById(addNewHrCommandDto.getHrUserId());

            String[] ceoFlags = new String[]{"admin", "ceo"};
            String[] canBecomeHrFlag = new String[]{"admin", "user"};
            List<String> ceoFlagsList = Arrays.asList(ceoFlags);
            List<String> canBecomeHrFlagList = Arrays.asList(canBecomeHrFlag);

            if (!ceoFlagsList.contains(usersModel.getFKuserAccountTypes().getRoleName()) ||
                !canBecomeHrFlagList.contains(userToBecomeHr.getFKuserAccountTypes().getRoleName())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                return new ResponseTransfer("Forbidden command"); //można dodać tylko zwykłych userów na hr
            } else {
                HrUsersModel hrUsersModel = new HrUsersModel(userToBecomeHr, ceosModel.getFKceoCompany());
                AccountTypesModel hrRoleModel = getHrModel();

                if (usersModel.getFKuserAccountTypes().getRoleName() != "admin") {
                    userToBecomeHr.setFKuserAccountTypes(hrRoleModel);
                    usersRepository.save(userToBecomeHr);
                }
                hrUsersRepository.save(hrUsersModel);
                response.setStatus(HttpServletResponse.SC_OK);
                return new ResponseTransfer("Hr user successfully added");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            return new ResponseTransfer("Internal server error");
        }
    }

    @Transactional
    @DeleteMapping(serviceUrlParam + "/hrusers/delete")
    public ResponseTransfer deleteHrUser(@RequestBody AddNewHrCommandDto addNewHrCommandDto,
                                         HttpServletResponse response){
        UsersModel usersModel;
        CeosModel ceosModel;
        UsersModel userToDelete;
        try {
            usersModel = getUsersModel();
            ceosModel = getCeosModelByOwnerId(usersModel.getId());
            userToDelete = getUserById(addNewHrCommandDto.getHrUserId());
            HrUsersModel hrUsersModel = hrUsersRepository.findByFKhrUserUserId(userToDelete.getId());

            String[] ceoFlags = new String[]{"admin", "ceo"};
            String[] canBecomeDeletedFromHr = new String[]{"hr_user"};
            List<String> ceoFlagsList = Arrays.asList(ceoFlags);
            List<String> canBecomeDeletedList = Arrays.asList(canBecomeDeletedFromHr);

            if (!ceoFlagsList.contains(usersModel.getFKuserAccountTypes().getRoleName()) ||
                    !canBecomeDeletedList.contains(userToDelete.getFKuserAccountTypes().getRoleName()) ||
                    ceosModel.getFKceoCompany().getId() != hrUsersModel.getFKhrUserCompany().getId()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                return new ResponseTransfer("Forbidden command");
            } else {
                AccountTypesModel userRoleModel = getUserModel();
                userToDelete.setFKuserAccountTypes(userRoleModel);
                usersRepository.save(userToDelete);
                hrUsersRepository.delete(hrUsersRepository.findByFKhrUserUserId(userToDelete.getId()));
                response.setStatus(HttpServletResponse.SC_OK);
                return new ResponseTransfer("Hr user deleted");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            return new ResponseTransfer("Internal server error");
        }
    }


    private CeosModel getCeosModelByOwnerId(long userId) {
        return ceosRepository.findByFKceoUserId(userId);
    }

    private UsersModel getUserById(long userId){
        return usersRepository.findById(userId);
    }

    private UsersModel getUsersModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private AccountTypesModel getHrModel() {
        return accountTypesRepository.findByRoleId(3);
    }

    private AccountTypesModel getUserModel() {
        return accountTypesRepository.findByRoleId(4);
    }

    private List<UserResultDto> findUser(List<UsersModel> usersModelsList){
        ArrayList<UserResultDto> listOfUserResultDto = new ArrayList<>();
        for (UsersModel usermodel : usersModelsList) {
            listOfUserResultDto.add(new UserResultDto(usermodel));
        }
        return listOfUserResultDto;
    }
}

package com.hr.app.controllers;

import com.hr.app.models.api_helpers.AddNewHrCommandDto;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class HrUsersController {

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

    @Transactional
    @PostMapping("/hrusers/add")
    public ResponseTransfer addNewHrUser(@RequestBody AddNewHrCommandDto addNewHrCommandDto, HttpServletResponse response){

        UsersModel usersModel;
        CeosModel ceosModel;
        UsersModel userToBecomeHr;
        try {
            usersModel = getUsersModel();
            ceosModel = getCeosModelByOwnerId(usersModel.getId());
            userToBecomeHr = getUserById(addNewHrCommandDto.getHrUserId());

            if (userToBecomeHr.getFKuserAccountTypes().getRoleId() !=4) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return new ResponseTransfer("Forbidden command"); //można dodać tylko zwykłych userów na hr
            }

            HrUsersModel hrUsersModel = new HrUsersModel(userToBecomeHr, ceosModel.getFKceoCompany());
            AccountTypesModel hrRoleModel = getHrModel();
            userToBecomeHr.setFKuserAccountTypes(hrRoleModel);

            usersRepository.save(userToBecomeHr);
            hrUsersRepository.save(hrUsersModel);
        } catch (Exception e) {
            System.out.println(e.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new ResponseTransfer("Hr user successfully added");
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
}

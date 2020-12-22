package com.hr.app.controllers;

import com.hr.app.enums.FileType;
import com.hr.app.ftp.FileStorageService;
import com.hr.app.mails.CustomMailing;
import com.hr.app.models.api_helpers.*;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.models.dto.UploadFileResponse;
import com.hr.app.models.dto.UserDataWithCvDto;
import com.hr.app.models.dto.UserResultDto;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping(serviceUrlParam + "/register")
    @ResponseBody
    public ResponseTransfer saveUser(@RequestBody RegisterCommandDto registerCommandDto, HttpServletResponse response) {
        try {
            if(userLoginAlreadyExists(registerCommandDto.getLogin())) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return new ResponseTransfer("LOGIN_EXISTS");
            }
            UsersModel checkEmailUser = usersRepository.findByEmail(registerCommandDto.getEmail());
            if(checkEmailUser!=null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return new ResponseTransfer("EMAIL_EXISTS");
            }
            if(registerCommandDto.getPassword().length() <6) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new ResponseTransfer("WEAK_PASSWORD");
            }
            else {
                registerCommandDto.setPassword(passwordEncoder.encode(registerCommandDto.getPassword()));
                UsersModel usersModel = new UsersModel(registerCommandDto);
                usersModel.setFKuserMailing(new MailingModel());
                usersModel.setFKuserAccountTypes(getDefaultAccountType());
                usersRepository.saveAndFlush(usersModel);
                sendMail.sendRegistrationMessage(registerCommandDto.getEmail(), registerCommandDto.getLogin());
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

    @GetMapping(serviceUrlParam + "/getdata")
    public Object getUserData(HttpServletResponse response){
        try {
            UsersModel user = getUsersModel();
            CvsModel cv = cvsRepository.findByFKcvUserId(user.getId());
            return new UserDataWithCvDto(user, cv.getFileName());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }
    }

    @PostMapping(serviceUrlParam + "/change-email")
    public Object changeEmail(@RequestBody ChangeEmailCommandDto changeEmailCommandDto, HttpServletResponse response) {
        try {
            UsersModel currentUser = getUsersModel();
            UsersModel checkEmailUser = usersRepository.findByEmail(changeEmailCommandDto.getNewEmail());
            if(checkEmailUser!=null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return new ResponseTransfer("EMAIL_EXISTS");
            }
            if(!checkPasswordCorrectness(currentUser.getPassword(), changeEmailCommandDto.getPassword())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return new ResponseTransfer("WRONG_PASSWORD");
            }
            currentUser.setEmail(changeEmailCommandDto.getNewEmail());
            response.setStatus(HttpServletResponse.SC_OK);
            usersRepository.save(currentUser);
            return new ResponseTransfer("SUCCESS");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("SERVER_ERROR");
        }
    }

    @PostMapping(serviceUrlParam + "/change-password")
    public Object changePassword(@RequestBody ChangePasswordCommandDto changePasswordCommandDto, HttpServletResponse response) {
        try {
            UsersModel currentUser = getUsersModel();
            if(changePasswordCommandDto.getNewPassword().length() <6) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new ResponseTransfer("WEAK_PASSWORD");
            }
            if(!checkPasswordCorrectness(currentUser.getPassword(), changePasswordCommandDto.getPassword())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return new ResponseTransfer("WRONG_PASSWORD");
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String newPasswordEncoded = bCryptPasswordEncoder.encode(changePasswordCommandDto.getNewPassword());
            currentUser.setPassword(newPasswordEncoded);
            response.setStatus(HttpServletResponse.SC_OK);
            usersRepository.save(currentUser);
            return new ResponseTransfer("SUCCESS");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("SERVER_ERROR");
        }
    }

    @PostMapping(serviceUrlParam + "/change-phonenumber")
    public Object changePhoneNumber(@RequestBody ChangePhoneNumberDto changePhoneNumberDto, HttpServletResponse response) {
        try {
            UsersModel currentUser = getUsersModel();
            if(changePhoneNumberDto.getPhoneNumber().length() <5) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new ResponseTransfer("TOO_SHORT_NUMBER");
            }

            if(!onlyDigits(changePhoneNumberDto.getPhoneNumber())) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new ResponseTransfer("ONLY_DIGITS_ALLOWED");
            }
            if(!checkPasswordCorrectness(currentUser.getPassword(), changePhoneNumberDto.getPassword())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return new ResponseTransfer("WRONG_PASSWORD");
            }
            currentUser.setPhoneNumber(changePhoneNumberDto.getPhoneNumber());
            response.setStatus(HttpServletResponse.SC_OK);
            usersRepository.save(currentUser);
            return new ResponseTransfer("SUCCESS");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("SERVER_ERROR");
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

    @Transactional
    @PostMapping(serviceUrlParam + "/upload-avatar")
    Object uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) {

        if(file == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("File is required");
        }
        if(!(FileCorrectness.fileExtensionisCorrect(file, "jpg") || !FileCorrectness.fileExtensionisCorrect(file, "png"))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("ONLY JPG AND PNG ALLOWED");
        }
        if(!FileCorrectness.fileSizeIsOk(file)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("File is too big");
        }

        String fileName;
        String fileDownloadUri;

        try {
            UsersModel currentUser = getUsersModel();
            fileName = fileStorageService.storeFile(file, currentUser.getLogin(), FileType.USER_IMAGE);

            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            currentUser.setAvatarUrl(fileDownloadUri);
            usersRepository.save(currentUser);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
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

    private boolean checkPasswordCorrectness(String currentPassword, String newPassword) {
        return BCrypt.checkpw(newPassword, currentPassword);
    }

    private static boolean onlyDigits(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '0'
                    && str.charAt(i) <= '9') {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    private boolean userLoginAlreadyExists(String login) {
        return usersRepository.findByLogin(login) != null;

    }

    private AccountTypesModel getDefaultAccountType() {
        return accountTypes.findByRoleId(4);
    }
}

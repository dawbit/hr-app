package com.hr.app.controllers;

import com.hr.app.enums.FileType;
import com.hr.app.ftp.FileStorageService;
import com.hr.app.models.api_helpers.FileCorrectness;
import com.hr.app.models.database.CvsModel;
import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.models.dto.UploadFileResponse;
import com.hr.app.repositories.ICvsRepository;
import com.hr.app.repositories.IUsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class CvsController {

    private static final Logger logger = LoggerFactory.getLogger(CvsController.class);

    private final String serviceUrlParam = "/cvs";

    @Autowired
    private ICvsRepository cvsRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IUsersRepository usersRepository;

    @Transactional
    @PostMapping(serviceUrlParam + "/uploadCv")
    Object uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) {

        if(file == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("File is required");
        }
        if(!FileCorrectness.fileExtensionisCorrect(file, "pdf")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("File is not pdf");
        }
        if(!FileCorrectness.fileSizeIsOk(file)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("File is too big");
        }

        String fileName;
        String fileDownloadUri;

        try {
            UsersModel currentUser = getUserModel();
            fileName = fileStorageService.storeFile(file, currentUser.getLogin(), FileType.CV);

            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            saveNewCvModel(currentUser, fileName);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    private UsersModel getUserModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private void saveNewCvModel(UsersModel usersModel, String fileName) {
        CvsModel cvsModel = cvsRepository.findByFKcvUserId(usersModel.getId());
        if(cvsModel==null) {
            cvsModel = new CvsModel(usersModel, true, fileName);
        } else {
            cvsModel.setFileName(fileName);
        }
        cvsRepository.save(cvsModel);
    }
}

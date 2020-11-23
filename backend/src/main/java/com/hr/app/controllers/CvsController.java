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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    @GetMapping(serviceUrlParam +"/byid/{id}")
    public CvsModel getCvId(@PathVariable long id) {
        return  cvsRepository.findById(id);
    }

    //TODO ogarnąć to po ogarnięciu Foreign keyów
//    @GetMapping("/cvs/byid/{id}")
//    public CvsModel getUserCvs(@PathVariable long id) {
//        return  cvsRepository.findByUser(id);
//    }

    @GetMapping(serviceUrlParam + "/setcurrent/{userid}/{cvid}")
    public List<CvsModel> setCurrentCv(@PathVariable long userid, @PathVariable long cvid){
        return cvsRepository.findAllByFKcvUserId(userid);
    }

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

    @GetMapping(serviceUrlParam + "/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private UsersModel getUserModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private void saveUserCv() {

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

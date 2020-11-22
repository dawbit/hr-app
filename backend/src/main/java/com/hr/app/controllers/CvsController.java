package com.hr.app.controllers;

import com.hr.app.ftp.FileStorageService;
import com.hr.app.models.database.CvsModel;
import com.hr.app.models.dto.UploadFileResponse;
import com.hr.app.repositories.ICvsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class CvsController {

    private static final Logger logger = LoggerFactory.getLogger(CvsController.class);

    @Autowired
    private ICvsRepository cvsRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/cvs/byid/{id}")
    public CvsModel getCvId(@PathVariable long id) {
        return  cvsRepository.findById(id);
    }

    //TODO ogarnąć to po ogarnięciu Foreign keyów
//    @GetMapping("/cvs/byid/{id}")
//    public CvsModel getUserCvs(@PathVariable long id) {
//        return  cvsRepository.findByUser(id);
//    }

    @GetMapping("/cvs/setcurrent/{userid}/{cvid}")
    public List<CvsModel> setCurrentCv(@PathVariable long userid, @PathVariable long cvid){
        return cvsRepository.findAllByFKcvUserId(userid);
    }


    //////new


    @PostMapping("/uploadFile")
    UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
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
}

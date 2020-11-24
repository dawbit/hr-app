package com.hr.app.ftp;

import com.hr.app.enums.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageUserImageLocation;
    private final Path fileStorageUserCvLocation;
    private final Path fileStorageCompanyImageLocation;

    @Autowired
    public FileStorageService() {
        String currentPath = System.getProperty("user.dir") + "/files";
        this.fileStorageUserImageLocation = Paths.get(currentPath + "/" + FileType.USER_IMAGE.toString())
                .toAbsolutePath().normalize();
        this.fileStorageUserCvLocation = Paths.get(currentPath + "/" + FileType.CV.toString())
                .toAbsolutePath().normalize();
        this.fileStorageCompanyImageLocation = Paths.get(currentPath + "/" + FileType.COMPANY_IMAGE.toString())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageUserImageLocation);
            Files.createDirectories(this.fileStorageUserCvLocation);
            Files.createDirectories(this.fileStorageCompanyImageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String ownerName, FileType fileType) {
        // Normalize file name
        String uuid = UUID.randomUUID().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmm", Locale.ENGLISH);
        String dateTime = sdf.format(new Date());
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = ownerName + "_" + uuid + "_" + dateTime + "." + fileExtension;

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation;
            switch (fileType) {
                case USER_IMAGE:
                    targetLocation = this.fileStorageUserImageLocation.resolve(fileName);
                    break;
                case COMPANY_IMAGE:
                    targetLocation = this.fileStorageCompanyImageLocation.resolve(fileName);
                    break;
                default:
                    targetLocation = this.fileStorageUserCvLocation.resolve(fileName);
            }
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, FileType fileType) {
        try {
            Path filePath;
            switch (fileType) {
                case USER_IMAGE:
                    filePath = this.fileStorageUserImageLocation.resolve(fileName).normalize();
                    break;
                case COMPANY_IMAGE:
                    filePath = this.fileStorageCompanyImageLocation.resolve(fileName).normalize();
                    break;
                default:
                    filePath = this.fileStorageUserCvLocation.resolve(fileName).normalize();
                    break;
            }
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
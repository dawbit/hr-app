package com.hr.app.models.api_helpers;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileCorrectness {
    public static boolean fileExtensionisCorrect(MultipartFile file, String extension) {
        return  StringUtils.getFilenameExtension(file.getOriginalFilename()).contains("pdf");
    }

    public static boolean fileSizeIsOk(MultipartFile file) {
        System.out.println(file.getSize());
        return  file.getSize() > 5000000;
    }
}

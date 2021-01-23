package com.hr.app.models.dto;

import com.hr.app.models.database.UsersModel;

public class UserDataWithCvDto extends UserResultDto {

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UserDataWithCvDto(UsersModel usersModel) {
        super(usersModel);
    }

    public UserDataWithCvDto(String fileName) {
        this.fileName = fileName;
    }

    public UserDataWithCvDto(UsersModel usersModel, String fileName) {
        super(usersModel);
        this.fileName = fileName;
    }
}

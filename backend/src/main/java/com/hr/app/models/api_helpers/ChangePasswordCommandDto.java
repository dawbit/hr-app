package com.hr.app.models.api_helpers;

public class ChangePasswordCommandDto {
    private String password;
    private String newPassword;

    protected ChangePasswordCommandDto() {

    }

    public ChangePasswordCommandDto(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

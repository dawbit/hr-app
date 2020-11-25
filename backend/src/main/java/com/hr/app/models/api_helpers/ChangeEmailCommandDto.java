package com.hr.app.models.api_helpers;

public class ChangeEmailCommandDto {
    private String password;
    private String newEmail;

    protected ChangeEmailCommandDto() {

    }

    public ChangeEmailCommandDto(String password, String newEmail) {
        this.password = password;
        this.newEmail = newEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}

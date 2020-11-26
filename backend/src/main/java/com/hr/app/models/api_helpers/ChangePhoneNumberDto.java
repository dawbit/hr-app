package com.hr.app.models.api_helpers;

public class ChangePhoneNumberDto {
    private String password;
    private String phoneNumber;

    protected ChangePhoneNumberDto() {

    }

    public ChangePhoneNumberDto(String password, String phoneNumber) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

package com.hr.app.models.api_helpers;

public class RegisterCommandDto {

    private String firstName;

    private String middleName;

    private String surname;

    private String email;

    private String phoneNumber;

    private String login;

    private String password;

    public RegisterCommandDto(String firstName, String middleName, String surname, String email, String phoneNumber, String login, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

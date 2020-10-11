package com.hr.app.models.dto;

import com.hr.app.models.database.UsersModel;

public class UserDto {

    private long id;
    private String firstName;
    private String middleName;
    private String surname;
    private String email;
    private String phoneNumber;
    private String login;
    private boolean isActive;

    protected UserDto(){}

    public UserDto(UsersModel usersModel) {
        this.id = usersModel.getId();
        this.email = usersModel.getEmail();
        this.firstName = usersModel.getFirstName();
        this.middleName = usersModel.getMiddleName();
        this.surname = usersModel.getSurname();
        this.phoneNumber = usersModel.getPhoneNumber();
        this.login = usersModel.getLogin();
        this.isActive = usersModel.getIsActive();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public boolean isActive() {
        return isActive;
    }
}

package com.hr.app.models.dto;

public class SimplyUserDto {
    private long id;
    private String login;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    protected SimplyUserDto() {
    }

    public SimplyUserDto(long id, String login) {
        this.id = id;
        this.login = login;
    }
}

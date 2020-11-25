package com.hr.app.models.api_helpers;

public class RegisterCompanyCommandDto {
    private String name;
    private String location;
    private String about;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public RegisterCompanyCommandDto(String name, String location, String about) {
        this.name = name;
        this.location = location;
        this.about = about;
    }
}

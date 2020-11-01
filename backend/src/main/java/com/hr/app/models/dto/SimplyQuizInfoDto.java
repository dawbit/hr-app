package com.hr.app.models.dto;

public class SimplyQuizInfoDto {
    private Long id;
    private String name;
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SimplyQuizInfoDto() {
    }

    public SimplyQuizInfoDto(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }
}

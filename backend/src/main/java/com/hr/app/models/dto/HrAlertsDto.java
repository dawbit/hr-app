package com.hr.app.models.dto;

public class HrAlertsDto {
    private long id;
    private SimplyUserDto simplyUserDto;
    private SimplyQuizInfoDto simplyQuizInfoDto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SimplyUserDto getSimplyUserDto() {
        return simplyUserDto;
    }

    public void setSimplyUserDto(SimplyUserDto simplyUserDto) {
        this.simplyUserDto = simplyUserDto;
    }

    public SimplyQuizInfoDto getSimplyQuizInfoDto() {
        return simplyQuizInfoDto;
    }

    public void setSimplyQuizInfoDto(SimplyQuizInfoDto simplyQuizInfoDto) {
        this.simplyQuizInfoDto = simplyQuizInfoDto;
    }

    public HrAlertsDto() {
    }

    public HrAlertsDto(long id, SimplyUserDto simplyUserDto, SimplyQuizInfoDto simplyQuizInfoDto) {
        this.id = id;
        this.simplyUserDto = simplyUserDto;
        this.simplyQuizInfoDto = simplyQuizInfoDto;
    }
}

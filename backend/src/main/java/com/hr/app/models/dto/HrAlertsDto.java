package com.hr.app.models.dto;

public class HrAlertsDto {
    private SimplyUserDto simplyUserDto;
    private SimplyQuizInfoDto simplyQuizInfoDto;

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

    public HrAlertsDto(SimplyUserDto simplyUserDto, SimplyQuizInfoDto simplyQuizInfoDto) {
        this.simplyUserDto = simplyUserDto;
        this.simplyQuizInfoDto = simplyQuizInfoDto;
    }
}

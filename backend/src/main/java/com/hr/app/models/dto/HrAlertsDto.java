package com.hr.app.models.dto;

public class HrAlertsDto {
    private long alertId;
    private long announcementId;
    private SimplyUserDto simplyUserDto;
    private SimplyQuizInfoDto simplyQuizInfoDto;

    public long getAlertId() {
        return alertId;
    }

    public void setAlertId(long alertId) {
        this.alertId = alertId;
    }

    public long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(long announcementId) {
        this.announcementId = announcementId;
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

    public HrAlertsDto(long alertId, long announcementId, SimplyUserDto simplyUserDto,
                       SimplyQuizInfoDto simplyQuizInfoDto) {
        this.alertId = alertId;
        this.announcementId = announcementId;
        this.simplyUserDto = simplyUserDto;
        this.simplyQuizInfoDto = simplyQuizInfoDto;
    }
}

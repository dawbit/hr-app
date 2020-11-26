package com.hr.app.models.dto;

public class HrAlertsDto {
    private long alertId;
    private long announcementId;
    private boolean isEnded;
    private String announcementTitle;
    private SimplyUserDto simplyUserDto;
    private SimplyQuizInfoDto simplyQuizInfoDto;

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

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

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
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

    public HrAlertsDto(long alertId, long announcementId, String announcementTitle, SimplyUserDto simplyUserDto,
                       SimplyQuizInfoDto simplyQuizInfoDto, boolean isEnded) {
        this.alertId = alertId;
        this.announcementId = announcementId;
        this.announcementTitle = announcementTitle;
        this.simplyUserDto = simplyUserDto;
        this.simplyQuizInfoDto = simplyQuizInfoDto;
        this.isEnded = isEnded;
    }
}

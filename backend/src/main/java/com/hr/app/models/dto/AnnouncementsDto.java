package com.hr.app.models.dto;

public class AnnouncementsDto {

    private String announcementTitle;
    private String announcementDescription;
    private String companyName;
    private String companyAbout;
    private String companyLocation;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementDescription() {
        return announcementDescription;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAbout() {
        return companyAbout;
    }

    public void setCompanyAbout(String companyAbout) {
        this.companyAbout = companyAbout;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public AnnouncementsDto() {}

    public AnnouncementsDto(String announcementTitle, String announcementDescription,
                            String companyName, String companyAbout, String companyLocation) {
        this.announcementTitle = announcementTitle;
        this.announcementDescription = announcementDescription;
        this.companyName = companyName;
        this.companyAbout = companyAbout;
        this.companyLocation = companyLocation;
    }
}

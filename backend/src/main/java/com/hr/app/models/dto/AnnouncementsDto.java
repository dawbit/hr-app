package com.hr.app.models.dto;

public class AnnouncementsDto {

    private long announcementId;
    private String announcementTitle;
    private String announcementDescription;
    private long companyId;
    private String companyName;
    private String companyAbout;
    private String companyLocation;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

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

    public String getAnnouncementDescription() {
        return announcementDescription;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
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

    protected AnnouncementsDto() { }

    public AnnouncementsDto(String announcementTitle, String announcementDescription, String companyName,
                            String companyAbout, String companyLocation) {
        this.announcementTitle = announcementTitle;
        this.announcementDescription = announcementDescription;
        this.companyName = companyName;
        this.companyAbout = companyAbout;
        this.companyLocation = companyLocation;
    }

    public AnnouncementsDto(long announcementId, String announcementTitle, String announcementDescription,
                            long companyId, String companyName, String companyAbout, String companyLocation) {
        this.announcementId = announcementId;
        this.announcementTitle = announcementTitle;
        this.announcementDescription = announcementDescription;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyAbout = companyAbout;
        this.companyLocation = companyLocation;
    }
}

package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "announcements")
public class AnnouncementsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FKannouncementCompany"))
    @JsonBackReference(value = "announcement-company")
    @JsonIgnore
    private CompaniesModel FKannouncementCompany;

    @ManyToOne
    @JoinColumn(name = "hrUser_id", foreignKey = @ForeignKey(name = "FKannouncementHrUser"))
    @JsonBackReference(value = "announcement-hruser")
    @JsonIgnore
    private HrUsersModel FKannouncementHrUser;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKtestAnnouncement")
    @JsonBackReference(value = "testParticipant-announcement")
    private List<TestParticipantModel> testsParticipantsModels;

    @OneToMany(mappedBy = "FKhrAlertAnnouncement")
    @JsonBackReference(value = "hralert-announcement")
    private List<HrAlertModel> hrAlertModels;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public long getId() {
        return id;
    }

    public CompaniesModel getFKannouncementCompany() {
        return FKannouncementCompany;
    }

    public void setFKannouncementCompany(CompaniesModel FKannouncementCompany) {
        this.FKannouncementCompany = FKannouncementCompany;
    }

    public HrUsersModel getFKannouncementHrUser() {
        return FKannouncementHrUser;
    }

    public void setFKannouncementHrUser(HrUsersModel FKannouncementHrUser) {
        this.FKannouncementHrUser = FKannouncementHrUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTestsParticipantsModels(List<TestParticipantModel> testsParticipantsModels) {
        this.testsParticipantsModels = testsParticipantsModels;
    }

    public void setHrAlertModels(List<HrAlertModel> hrAlertModels) {
        this.hrAlertModels = hrAlertModels;
    }

    protected AnnouncementsModel() {
    }

    public AnnouncementsModel(CompaniesModel FKannouncementCompany,
                              HrUsersModel FKannouncementHrUser, String title,
                              String description) {
        this.FKannouncementCompany = FKannouncementCompany;
        this.FKannouncementHrUser = FKannouncementHrUser;
        this.title = title;
        this.description = description;
    }
}

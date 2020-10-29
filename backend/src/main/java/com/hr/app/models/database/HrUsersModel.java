package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hr_users")
public class HrUsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FKhrUserUser"))
    @JsonBackReference(value = "hrUser-user")
    @JsonIgnore
    private UsersModel FKhrUserUser;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FKhrUserCompany"))
    @JsonBackReference(value = "hrUser-company")
    @JsonIgnore
    private CompaniesModel FKhrUserCompany;

    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKannouncementHrUser")
    @JsonBackReference(value = "announcement-hruser")
    private List<AnnouncementsModel> announcementHrUser;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public UsersModel getFKhrUserUser() {
        return FKhrUserUser;
    }

    public CompaniesModel getFKhrUserCompany() {
        return FKhrUserCompany;
    }

    public void setFKhrUserUser(UsersModel FKhrUserUser) {
        this.FKhrUserUser = FKhrUserUser;
    }

    public void setAnnouncementHrUser(List<AnnouncementsModel> announcementHrUser) {
        this.announcementHrUser = announcementHrUser;
    }

    public void setFKhrUserCompany(CompaniesModel FKhrUserCompany) {
        this.FKhrUserCompany = FKhrUserCompany;
    }

    protected HrUsersModel() { }

    public HrUsersModel(UsersModel FKhrUserUser, CompaniesModel FKhrUserCompany) {
        this.FKhrUserUser = FKhrUserUser;
        this.FKhrUserCompany = FKhrUserCompany;
    }

    public HrUsersModel(UsersModel FKhrUserUser, CompaniesModel FKhrUserCompany,
                        List<AnnouncementsModel> announcementHrUser) {
        this.FKhrUserUser = FKhrUserUser;
        this.FKhrUserCompany = FKhrUserCompany;
        this.announcementHrUser = announcementHrUser;
    }
}

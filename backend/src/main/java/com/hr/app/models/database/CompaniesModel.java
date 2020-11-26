package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hr.app.models.api_helpers.RegisterCompanyCommandDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
public class CompaniesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "about", nullable = false)
    private String about;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKpicturesCompany")
    @JsonBackReference(value = "pictures-company")
    private List<CompanyPicturesModel> companyPictures;

    @OneToMany(mappedBy = "FKceoCompany")
    @JsonBackReference(value = "ceo-company")
    private List<CeosModel> ceo;

    @OneToMany(mappedBy = "FKtestCompany")
    @JsonBackReference(value = "test-company")
    private List<TestsModel> tests;

    @OneToMany(mappedBy = "FKhrUserCompany")
    @JsonBackReference(value = "hrUser-company")
    private List<HrUsersModel> hrUsers;

    @OneToMany(mappedBy = "FKannouncementCompany")
    @JsonBackReference(value = "announcement-company")
    private List<AnnouncementsModel> announcementCompany;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getAbout() {
        return about;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setCompanyPictures(List<CompanyPicturesModel> companyPictures) {
        this.companyPictures = companyPictures;
    }

    public void setCeo(List<CeosModel> ceo) {
        this.ceo = ceo;
    }

    public void setTests(List<TestsModel> tests) {
        this.tests = tests;
    }

    public void setHrUsers(List<HrUsersModel> hrUsers) {
        this.hrUsers = hrUsers;
    }

    public void setAnnouncementCompany(List<AnnouncementsModel> announcementCompany) {
        this.announcementCompany = announcementCompany;
    }

    protected CompaniesModel() { }

    public CompaniesModel(String name, String location, String about,
                          List<CompanyPicturesModel> companyPictures, List<CeosModel> ceo,
                          List<TestsModel> tests, List<HrUsersModel> hrUsers, String imageUrl,
                          List<AnnouncementsModel> announcementCompany) {
        this.name = name;
        this.location = location;
        this.about = about;
        this.companyPictures = companyPictures;
        this.ceo = ceo;
        this.tests = tests;
        this.hrUsers = hrUsers;
        this.announcementCompany = announcementCompany;
        this.imageUrl = imageUrl;
    }

    public CompaniesModel(RegisterCompanyCommandDto registerCompanyCommandDto) {
        this.about = registerCompanyCommandDto.getAbout();
        this.location = registerCompanyCommandDto.getLocation();
        this.name = registerCompanyCommandDto.getName();
    }
}

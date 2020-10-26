package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Type;

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

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Lob
    @Column(name = "image")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] image;


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


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================



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

    public byte[] getImage() {
        return image;
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

    public void setImage(byte[] image) {
        this.image = image;
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

    protected CompaniesModel() { }

    public CompaniesModel(String name, String location, String about, byte[] image,
                          List<CompanyPicturesModel> companyPictures, List<CeosModel> ceo,
                          List<TestsModel> tests, List<HrUsersModel> hrUsers) {
        this.name = name;
        this.location = location;
        this.about = about;
        this.image = image;
        this.companyPictures = companyPictures;
        this.ceo = ceo;
        this.tests = tests;
        this.hrUsers = hrUsers;
    }
}

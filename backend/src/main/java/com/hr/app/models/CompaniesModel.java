package com.hr.app.models;

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

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "about")
    private String about;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Lob
    @Column(name = "image")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] image;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FK_picturesCompany")
    @JsonBackReference(value = "pictures-company")
    private List<CompanyPicturesModel> companyPictures;

    @OneToMany(mappedBy = "FK_ceoCompany")
    @JsonBackReference(value = "ceo-company")
    private List<CeosModel> ceo;

    @OneToMany(mappedBy = "FK_departmentCompany")
    @JsonBackReference(value = "departments-company")
    private List<DepartmentsModel> departments;

    @OneToMany(mappedBy = "FK_testCompany")
    @JsonBackReference(value = "test-company")
    private List<TestsModel> tests;

    @OneToMany(mappedBy = "FK_hrUserCompany")
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

    public List<CompanyPicturesModel> getCompanyPictures() {
        return companyPictures;
    }

    public List<CeosModel> getCeo() {
        return ceo;
    }

    public List<DepartmentsModel> getDepartments() {
        return departments;
    }

    public List<TestsModel> getTests() {
        return tests;
    }

    public List<HrUsersModel> getHrUsers() {
        return hrUsers;
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

    public void setDepartments(List<DepartmentsModel> departments) {
        this.departments = departments;
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
                          List<DepartmentsModel> departments, List<TestsModel> tests,
                          List<HrUsersModel> hrUsers) {
        this.name = name;
        this.location = location;
        this.about = about;
        this.image = image;
        this.companyPictures = companyPictures;
        this.ceo = ceo;
        this.departments = departments;
        this.tests = tests;
        this.hrUsers = hrUsers;
    }
}

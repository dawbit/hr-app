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

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    protected CompaniesModel() {}

    public CompaniesModel(String name, String location, String about, byte[] image) {
        this.name = name;
        this.location = location;
        this.about = about;
        this.image = image;
    }
}

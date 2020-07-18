package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "company_pictures")
public class CompanyPicturesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference(value = "picture-company")
    @JsonIgnore
    private CompaniesModel FK_picturesCompany;

    @Column(name = "is_current")
    private boolean isCurrent;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Column(name = "logo")
    private String logo;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Column(name = "background_pic")
    private String backgroundPicture;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Column(name = "first_pic")
    private String firstPicture;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Column(name = "second_pic")
    private String secondPicture;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Column(name = "third_pic")
    private String thirdPicture;


    // =========================================
    // RELATIONSHIPS
    // =========================================


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public CompaniesModel getFK_picturesCompany() {
        return FK_picturesCompany;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public String getLogo() {
        return logo;
    }

    public String getBackgroundPicture() {
        return backgroundPicture;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public String getSecondPicture() {
        return secondPicture;
    }

    public String getThirdPicture() {
        return thirdPicture;
    }

    public void setFK_picturesCompany(CompaniesModel FK_picturesCompany) {
        this.FK_picturesCompany = FK_picturesCompany;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setBackgroundPicture(String backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public void setSecondPicture(String secondPicture) {
        this.secondPicture = secondPicture;
    }

    public void setThirdPicture(String thirdPicture) {
        this.thirdPicture = thirdPicture;
    }

    protected CompanyPicturesModel() { }

    public CompanyPicturesModel(CompaniesModel FK_picturesCompany, boolean isCurrent, String logo,
                                String backgroundPicture, String firstPicture, String secondPicture,
                                String thirdPicture) {
        this.FK_picturesCompany = FK_picturesCompany;
        this.isCurrent = isCurrent;
        this.logo = logo;
        this.backgroundPicture = backgroundPicture;
        this.firstPicture = firstPicture;
        this.secondPicture = secondPicture;
        this.thirdPicture = thirdPicture;
    }
}

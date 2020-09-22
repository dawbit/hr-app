package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.app.models.database.CompaniesModel;

import javax.persistence.*;

@Entity
@Table(name = "company_pictures")
public class CompanyPicturesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FKpicturesCompany"))
    @JsonBackReference(value = "picture-company")
    @JsonIgnore
    private CompaniesModel FKpicturesCompany;

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

    public CompaniesModel getFKpicturesCompany() {
        return FKpicturesCompany;
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

    public void setFKpicturesCompany(CompaniesModel FKpicturesCompany) {
        this.FKpicturesCompany = FKpicturesCompany;
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

    public CompanyPicturesModel(CompaniesModel FKpicturesCompany, boolean isCurrent, String logo,
                                String backgroundPicture, String firstPicture, String secondPicture,
                                String thirdPicture) {
        this.FKpicturesCompany = FKpicturesCompany;
        this.isCurrent = isCurrent;
        this.logo = logo;
        this.backgroundPicture = backgroundPicture;
        this.firstPicture = firstPicture;
        this.secondPicture = secondPicture;
        this.thirdPicture = thirdPicture;
    }
}

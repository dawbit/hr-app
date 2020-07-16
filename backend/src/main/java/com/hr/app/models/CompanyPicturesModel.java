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

}

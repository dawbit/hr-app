package com.hr.app.models;

import org.hibernate.annotations.Type;
import javax.persistence.*;

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

    @Lob
    @Column(name = "image")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] image;

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

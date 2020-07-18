package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicturesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_profilePictureUser"))
    @JsonBackReference(value = "profile_picture-user")
    @JsonIgnore
    private UsersModel FK_profilePictureUser;

    @Column(name = "is_current")
    private boolean isCurrent;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Column(name = "file_name")
    private String fileName;


    // =========================================
    // RELATIONSHIPS
    // =========================================


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public UsersModel getFK_profilePictureUser() {
        return FK_profilePictureUser;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFK_profilePictureUser(UsersModel FK_profilePictureUser) {
        this.FK_profilePictureUser = FK_profilePictureUser;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    protected ProfilePicturesModel() { }

    public ProfilePicturesModel(UsersModel FK_profilePictureUser, boolean isCurrent, String fileName) {
        this.FK_profilePictureUser = FK_profilePictureUser;
        this.isCurrent = isCurrent;
        this.fileName = fileName;
    }
}

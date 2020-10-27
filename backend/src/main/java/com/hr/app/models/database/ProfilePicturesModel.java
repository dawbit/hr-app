package com.hr.app.models.database;

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
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FKprofilePictureUser"))
    @JsonBackReference(value = "profilepictures-user")
    @JsonIgnore
    private UsersModel FKprofilePictureUser;

    @Column(name = "is_current")
    private boolean isCurrent;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Column(name = "file_name", nullable = false)
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

    public UsersModel getFKprofilePictureUser() {
        return FKprofilePictureUser;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFKprofilePictureUser(UsersModel FKprofilePictureUser) {
        this.FKprofilePictureUser = FKprofilePictureUser;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    protected ProfilePicturesModel() { }

    public ProfilePicturesModel(UsersModel FKprofilePictureUser, boolean isCurrent, String fileName) {
        this.FKprofilePictureUser = FKprofilePictureUser;
        this.isCurrent = isCurrent;
        this.fileName = fileName;
    }
}

package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cvs")
public class CvsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_cvUser"))
    @JsonBackReference(value = "cv-user")
    @JsonIgnore
    private UsersModel FK_cvUser;

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

    public UsersModel getFK_cvUser() {
        return FK_cvUser;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFK_cvUser(UsersModel FK_cvUser) {
        this.FK_cvUser = FK_cvUser;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    protected CvsModel() { }

    public CvsModel(UsersModel FK_cvUser, boolean isCurrent, String fileName) {
        this.FK_cvUser = FK_cvUser;
        this.isCurrent = isCurrent;
        this.fileName = fileName;
    }
}

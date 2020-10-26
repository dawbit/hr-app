package com.hr.app.models.database;

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
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FKcvUser"))
    @JsonBackReference(value = "cv-user")
    @JsonIgnore
    private UsersModel FKcvUser;

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


    public UsersModel getFKcvUser() {
        return FKcvUser;
    }

    public long getId() {
        return id;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFKcvUser(UsersModel FKcvUser) {
        this.FKcvUser = FKcvUser;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    protected CvsModel() { }

    public CvsModel(UsersModel FKcvUser, boolean isCurrent, String fileName) {
        this.FKcvUser = FKcvUser;
        this.isCurrent = isCurrent;
        this.fileName = fileName;
    }
}

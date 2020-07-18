package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "ceos")
public class CeosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ceoUser"))
    @JsonBackReference(value = "ceo-user")
    @JsonIgnore
    private UsersModel FK_ceoUser;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_ceoCompany"))
    @JsonBackReference(value = "ceo-company")
    @JsonIgnore
    private CompaniesModel FK_ceoCompany;

    // =========================================
    // RELATIONSHIPS
    // =========================================


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public long getId() {
        return id;
    }

    public UsersModel getFK_ceoUser() {
        return FK_ceoUser;
    }

    public CompaniesModel getFK_ceoCompany() {
        return FK_ceoCompany;
    }

    public void setFK_ceoUser(UsersModel FK_ceoUser) {
        this.FK_ceoUser = FK_ceoUser;
    }

    public void setFK_ceoCompany(CompaniesModel FK_ceoCompany) {
        this.FK_ceoCompany = FK_ceoCompany;
    }

    protected CeosModel() { }

    public CeosModel(UsersModel FK_ceoUser, CompaniesModel FK_ceoCompany) {
        this.FK_ceoUser = FK_ceoUser;
        this.FK_ceoCompany = FK_ceoCompany;
    }
}

package com.hr.app.models.database;

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
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FKceoUser"))
    @JsonBackReference(value = "ceo-user")
    @JsonIgnore
    private UsersModel FKceoUser;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FKceoCompany"))
    @JsonBackReference(value = "ceo-company")
    @JsonIgnore
    private CompaniesModel FKceoCompany;

    // =========================================
    // RELATIONSHIPS
    // =========================================


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================



    public long getId() {
        return id;
    }

    public UsersModel getFKceoUser() {
        return FKceoUser;
    }

    public CompaniesModel getFKceoCompany() {
        return FKceoCompany;
    }

    public void setFKceoUser(UsersModel FKceoUser) {
        this.FKceoUser = FKceoUser;
    }

    public void setFKceoCompany(CompaniesModel FKceoCompany) {
        this.FKceoCompany = FKceoCompany;
    }

    protected CeosModel() { }

    public CeosModel(UsersModel FKceoUser, CompaniesModel FKceoCompany) {
        this.FKceoUser = FKceoUser;
        this.FKceoCompany = FKceoCompany;
    }
}

package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "hr_users")
public class HrUsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "hrUser-user")
    @JsonIgnore
    private UsersModel FK_hrUserUser;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference(value = "hrUser-company")
    @JsonIgnore
    private CompaniesModel FK_hrUserCompany;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference(value = "hrUser-department")
    @JsonIgnore
    private DepartmentsModel FK_hrUserDepartment;

    // =========================================
    // RELATIONSHIPS
    // =========================================


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public UsersModel getFK_hrUserUser() {
        return FK_hrUserUser;
    }

    public CompaniesModel getFK_hrUserCompany() {
        return FK_hrUserCompany;
    }

    public DepartmentsModel getFK_hrUserDepartment() {
        return FK_hrUserDepartment;
    }

    public void setFK_hrUserUser(UsersModel FK_hrUserUser) {
        this.FK_hrUserUser = FK_hrUserUser;
    }

    public void setFK_hrUserCompany(CompaniesModel FK_hrUserCompany) {
        this.FK_hrUserCompany = FK_hrUserCompany;
    }

    public void setFK_hrUserDepartment(DepartmentsModel FK_hrUserDepartment) {
        this.FK_hrUserDepartment = FK_hrUserDepartment;
    }

    protected HrUsersModel() { }

    public HrUsersModel(UsersModel FK_hrUserUser, CompaniesModel FK_hrUserCompany, DepartmentsModel FK_hrUserDepartment) {
        this.FK_hrUserUser = FK_hrUserUser;
        this.FK_hrUserCompany = FK_hrUserCompany;
        this.FK_hrUserDepartment = FK_hrUserDepartment;
    }
}

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
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FKhrUserUser"))
    @JsonBackReference(value = "hrUser-user")
    @JsonIgnore
    private UsersModel FKhrUserUser;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FKhrUserCompany"))
    @JsonBackReference(value = "hrUser-company")
    @JsonIgnore
    private CompaniesModel FKhrUserCompany;

    @ManyToOne
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "FKhrUserDepartment"))
    @JsonBackReference(value = "hrUser-department")
    @JsonIgnore
    private DepartmentsModel FKhrUserDepartment;

    // =========================================
    // RELATIONSHIPS
    // =========================================


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public UsersModel getFKhrUserUser() {
        return FKhrUserUser;
    }

    public CompaniesModel getFKhrUserCompany() {
        return FKhrUserCompany;
    }

    public DepartmentsModel getFKhrUserDepartment() {
        return FKhrUserDepartment;
    }

    public void setFKhrUserUser(UsersModel FKhrUserUser) {
        this.FKhrUserUser = FKhrUserUser;
    }

    public void setFKhrUserCompany(CompaniesModel FKhrUserCompany) {
        this.FKhrUserCompany = FKhrUserCompany;
    }

    public void setFKhrUserDepartment(DepartmentsModel FKhrUserDepartment) {
        this.FKhrUserDepartment = FKhrUserDepartment;
    }

    protected HrUsersModel() { }

    public HrUsersModel(UsersModel FKhrUserUser, CompaniesModel FKhrUserCompany, DepartmentsModel FKhrUserDepartment) {
        this.FKhrUserUser = FKhrUserUser;
        this.FKhrUserCompany = FKhrUserCompany;
        this.FKhrUserDepartment = FKhrUserDepartment;
    }
}

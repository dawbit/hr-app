package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class DepartmentsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_departmentCompany"))
    @JsonBackReference(value = "departments-company")
    @JsonIgnore
    private CompaniesModel FK_departmentCompany;

    @Column(name = "name", nullable = false)
    private String name;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FK_hrUserDepartment")
    @JsonBackReference(value = "hrUser-department")
    private List<HrUsersModel> hrUsers;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public long getId() {
        return id;
    }

    public CompaniesModel getFK_departmentCompany() {
        return FK_departmentCompany;
    }

    public String getName() {
        return name;
    }

    public List<HrUsersModel> getHrUsers() {
        return hrUsers;
    }

    public void setFK_departmentCompany(CompaniesModel FK_departmentCompany) {
        this.FK_departmentCompany = FK_departmentCompany;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHrUsers(List<HrUsersModel> hrUsers) {
        this.hrUsers = hrUsers;
    }

    protected DepartmentsModel() {}

    public DepartmentsModel(CompaniesModel FK_departmentCompany, String name, List<HrUsersModel> hrUsers) {
        this.FK_departmentCompany = FK_departmentCompany;
        this.name = name;
        this.hrUsers = hrUsers;
    }
}

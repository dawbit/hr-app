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
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FKdepartmentCompany"))
    @JsonBackReference(value = "departments-company")
    @JsonIgnore
    private CompaniesModel FKdepartmentCompany;

    @Column(name = "name", nullable = false)
    private String name;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKhrUserDepartment")
    @JsonBackReference(value = "hrUser-department")
    private List<HrUsersModel> hrUsers;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public long getId() {
        return id;
    }

    public CompaniesModel getFKdepartmentCompany() {
        return FKdepartmentCompany;
    }

    public String getName() {
        return name;
    }

    public List<HrUsersModel> getHrUsers() {
        return hrUsers;
    }

    public void setFKdepartmentCompany(CompaniesModel FKdepartmentCompany) {
        this.FKdepartmentCompany = FKdepartmentCompany;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHrUsers(List<HrUsersModel> hrUsers) {
        this.hrUsers = hrUsers;
    }

    protected DepartmentsModel() {}

    public DepartmentsModel(CompaniesModel FKdepartmentCompany, String name, List<HrUsersModel> hrUsers) {
        this.FKdepartmentCompany = FKdepartmentCompany;
        this.name = name;
        this.hrUsers = hrUsers;
    }
}

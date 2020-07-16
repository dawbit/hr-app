package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "departments")
public class DepartmentsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference(value = "departments-company")
    @JsonIgnore
    private CompaniesModel FK_departmentCompany;

    @Column(name = "name")
    private String name;
}

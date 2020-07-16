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
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "ceo-user")
    @JsonIgnore
    private UsersModel FK_ceoUser;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference(value = "ceo-company")
    @JsonIgnore
    private CompaniesModel FK_ceoCompany;
}

package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account_types")
public class AccountTypesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "role_id", nullable = false)
    private long roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKuserAccountTypes")
    @JsonBackReference(value = "user-role")
    @JsonIgnore
    private List<UsersModel> FKuserAccountTypes;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setFKuserAccountTypes(List<UsersModel> FKuserAccountTypes) {
        this.FKuserAccountTypes = FKuserAccountTypes;
    }

    protected AccountTypesModel() { }

    public AccountTypesModel(long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}

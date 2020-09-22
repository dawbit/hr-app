package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    private List<UsersModel> FKuserAccountTypes;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public void setId(long id) {
        this.id = id;
    }

    public List<UsersModel> getFKuserAccountTypes() {
        return FKuserAccountTypes;
    }

    public void setFKuserAccountTypes(List<UsersModel> FKuserAccountTypes) {
        this.FKuserAccountTypes = FKuserAccountTypes;
    }

    public long getId() {
        return id;
    }

    public long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public List<UsersModel> getUserAccountTypes() {
        return FKuserAccountTypes;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    protected AccountTypesModel() { }

    public AccountTypesModel(long roleId, String roleName, List<UsersModel> FKuserAccountTypes) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.FKuserAccountTypes = FKuserAccountTypes;
    }
}

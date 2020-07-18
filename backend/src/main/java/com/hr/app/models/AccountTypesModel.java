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

    @Column(name = "role_id")
    private long roleId;

    @Column(name = "role_name")
    private String roleName;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FK_userAccountTypes")
    @JsonBackReference(value = "user-role")
    private List<UsersModel> userAccountTypes;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public List<UsersModel> getUserAccountTypes() {
        return userAccountTypes;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    protected AccountTypesModel() { }

    public AccountTypesModel(long roleId, String roleName, List<UsersModel> userAccountTypes) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.userAccountTypes = userAccountTypes;
    }
}

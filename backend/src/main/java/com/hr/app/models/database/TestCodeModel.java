package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "test_code")
public class TestCodeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "test_id", foreignKey = @ForeignKey(name = "FKtestCodetest"))
    @JsonBackReference(value = "test-id")
    @JsonIgnore
    private TestsModel FKtestCodetest;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FKtestCodeuser"))
    @JsonBackReference(value = "user-id")
    @JsonIgnore
    private UsersModel FKtestCodeuser;

    @Column(name = "test_code")
    private String code;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TestsModel getFKtest() {
        return FKtestCodetest;
    }

    public void setFKtest(TestsModel FKtestCodetest) {
        this.FKtestCodetest = FKtestCodetest;
    }

    public UsersModel getFKuser() {
        return FKtestCodeuser;
    }

    public void setFKuser(UsersModel FKtestCodeuser) {
        this.FKtestCodeuser = FKtestCodeuser;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    protected TestCodeModel(){};

    public TestCodeModel(long id, TestsModel FKtestCodetest, UsersModel FKtestCodeuser, String code) {
        this.id = id;
        this.FKtestCodetest = FKtestCodetest;
        this.FKtestCodeuser = FKtestCodeuser;
        this.code = code;
    }
}

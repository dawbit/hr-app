package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "test_type")
public class TestTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKtestType")
    @JsonBackReference(value = "test-type")
    private List<TestsModel> testsModels;

    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestsModel> getTestsModels() {
        return testsModels;
    }

    public void setTestsModels(List<TestsModel> testsModels) {
        this.testsModels = testsModels;
    }

    protected TestTypeModel(){};

    public TestTypeModel(long id, String name, List<TestsModel> testsModels) {
        this.id = id;
        this.name = name;
        this.testsModels = testsModels;
    }
}

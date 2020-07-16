package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tests")
public class TestsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_hr_id")
    @JsonBackReference(value = "test-user-hr")
    @JsonIgnore
    private UsersModel FK_testUserHr;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference(value = "test-company")
    @JsonIgnore
    private UsersModel FK_testCompany;

    @Column(name = "is_possible_to_back")
    private boolean isPossibleToBack;

    @Column(name = "test_code")
    private String testCode;

    @Column(name = "test_type")
    private String testType;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "time_for_test")
    private short timeForTest;


    @OneToMany(mappedBy = "FK_questionTest")
    @JsonBackReference(value = "questionTest")
    private List<QuestionsModel> questions;

    @OneToMany(mappedBy = "FK_testIduserAnswer")
    @JsonBackReference(value = "test_id_user_answer")
    private List<UserAnswersModel> userAnswers;
}

package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference(value = "user-role")
    @JsonIgnore
    private AccountTypesModel FK_userAccountTypes;

    @Column(name = "is_active")
    private boolean isActive;


    @OneToMany(mappedBy = "FK_cvUser")
    @JsonBackReference(value = "cv-user")
    private List<CvsModel> cvs;

    @OneToMany(mappedBy = "FK_profilePictureUser")
    @JsonBackReference(value = "profile_pictures-user")
    private List<ProfilePicturesModel> profilePictures;

    @OneToMany(mappedBy = "FK_ceoUser")
    @JsonBackReference(value = "ceo-user")
    private List<CeosModel> ceo;

    @OneToMany(mappedBy = "FK_testUserHr")
    @JsonBackReference(value = "test-user-hr")
    private List<TestsModel> tests;

    @OneToMany(mappedBy = "FK_userIduserAnswer")
    @JsonBackReference(value = "user_id_user_answer")
    private List<UserAnswersModel> userAnswers;
}

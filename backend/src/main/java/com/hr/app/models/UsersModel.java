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
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_userAccountTypes"))
    @JsonBackReference(value = "user-role")
    @JsonIgnore
    private AccountTypesModel FK_userAccountTypes;

    @Column(name = "is_active")
    private boolean isActive;


    // =========================================
    // RELATIONSHIPS
    // =========================================

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

    @OneToMany(mappedBy = "FK_hrUserUser")
    @JsonBackReference(value = "hrUser-user")
    private List<HrUsersModel> hrUsers;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public AccountTypesModel getFK_userAccountTypes() {
        return FK_userAccountTypes;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public List<CvsModel> getCvs() {
        return cvs;
    }

    public List<ProfilePicturesModel> getProfilePictures() {
        return profilePictures;
    }

    public List<CeosModel> getCeo() {
        return ceo;
    }

    public List<TestsModel> getTests() {
        return tests;
    }

    public List<UserAnswersModel> getUserAnswers() {
        return userAnswers;
    }

    public List<HrUsersModel> getHrUsers() {
        return hrUsers;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFK_userAccountTypes(AccountTypesModel FK_userAccountTypes) {
        this.FK_userAccountTypes = FK_userAccountTypes;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void setCvs(List<CvsModel> cvs) {
        this.cvs = cvs;
    }

    public void setProfilePictures(List<ProfilePicturesModel> profilePictures) {
        this.profilePictures = profilePictures;
    }

    public void setCeo(List<CeosModel> ceo) {
        this.ceo = ceo;
    }

    public void setTests(List<TestsModel> tests) {
        this.tests = tests;
    }

    public void setUserAnswers(List<UserAnswersModel> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public void setHrUsers(List<HrUsersModel> hrUsers) {
        this.hrUsers = hrUsers;
    }

    protected UsersModel() { }

    public UsersModel(String firstName, String middleName, String surname, String email, String phoneNumber,
                      String login, String password, AccountTypesModel FK_userAccountTypes, boolean isActive,
                      List<CvsModel> cvs, List<ProfilePicturesModel> profilePictures, List<CeosModel> ceo,
                      List<TestsModel> tests, List<UserAnswersModel> userAnswers, List<HrUsersModel> hrUsers) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
        this.FK_userAccountTypes = FK_userAccountTypes;
        this.isActive = isActive;
        this.cvs = cvs;
        this.profilePictures = profilePictures;
        this.ceo = ceo;
        this.tests = tests;
        this.userAnswers = userAnswers;
        this.hrUsers = hrUsers;
    }
}

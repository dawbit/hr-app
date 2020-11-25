package com.hr.app.models.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.app.models.api_helpers.RegisterCommandDto;
import com.hr.app.models.dto.UserResultDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", unique = true, nullable = false)
    @Email
    @NotBlank //Ponoc samo nullable nie dziala z @Email
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "avatar")
    private String avatarUrl;

    @Column(name = "login", unique = true, nullable = false)
    @Size(min = 8)
    private String login;

    @Column(name = "password")
    @Size(min = 6)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FKuserAccountTypes"))
    @JsonBackReference(value = "user-role")
    private AccountTypesModel FKuserAccountTypes;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mailing_id", foreignKey = @ForeignKey(name = "FKuserMailing"))
    @JsonBackReference(value = "user-mailing")
    private MailingModel FKuserMailing;


    // =========================================
    // RELATIONSHIPS
    // =========================================

    @OneToMany(mappedBy = "FKcvUser")
    @JsonBackReference(value = "cv-user")
    @JsonIgnore
    private List<CvsModel> listOfcvsModels;

    @OneToMany(mappedBy = "FKprofilePictureUser")
    @JsonBackReference(value = "profilepictures-user")
    private List<ProfilePicturesModel> profilePictures;

    @OneToMany(mappedBy = "FKceoUser")
    @JsonBackReference(value = "ceo-user")
    private List<CeosModel> ceo;

    @OneToMany(mappedBy = "FKtestUserHr")
    @JsonBackReference(value = "test-user-hr")
    private List<TestsModel> tests;

    @OneToMany(mappedBy = "FKuserIduserAnswer")
    @JsonBackReference(value = "user_id_user_answer")
    private List<UserAnswersModel> userAnswers;

    @OneToMany(mappedBy = "FKhrUserUser")
    @JsonBackReference(value = "hrUser-user")
    private List<HrUsersModel> hrUsers;

    @OneToMany(mappedBy = "FKtestCodeuser")
    @JsonBackReference(value = "user-id")
    private List<TestParticipantModel> FKtestCodeuser;

    @OneToMany(mappedBy = "FKhrAlertUser")
    @JsonBackReference(value = "hralert-user")
    private List<HrAlertModel> hrAlertModels;


    // =========================================
    // GETTERS, SETTERS, CONSTRUCTORS
    // =========================================


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setListOfcvsModels(List<CvsModel> listOfcvsModels) {
        this.listOfcvsModels = listOfcvsModels;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setFKtestCodeuser(List<TestParticipantModel> FKtestCodeuser) {
        this.FKtestCodeuser = FKtestCodeuser;
    }

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

    public AccountTypesModel getFKuserAccountTypes() {
        return FKuserAccountTypes;
    }

    public Boolean getIsActive() {
        return isActive;
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

    public void setFKuserAccountTypes(AccountTypesModel FKuserAccountTypes) {
        this.FKuserAccountTypes = FKuserAccountTypes;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
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

    public MailingModel getFKuserMailing() {
        return FKuserMailing;
    }

    public void setFKuserMailing(MailingModel FKuserMailing) {
        this.FKuserMailing = FKuserMailing;
    }

    protected UsersModel() { }

    public UsersModel(String firstName, String middleName, String surname, String email, String phoneNumber,
                      String login, String password, AccountTypesModel FKuserAccountTypes, Boolean isActive,
                      List<ProfilePicturesModel> profilePictures, List<CeosModel> ceo, List<TestsModel> tests,
                      List<UserAnswersModel> userAnswers, List<HrUsersModel> hrUsers, MailingModel FKuserMailing,
                      String avatarUrl) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
        this.FKuserAccountTypes = FKuserAccountTypes;
        this.isActive = isActive;
        this.profilePictures = profilePictures;
        this.ceo = ceo;
        this.tests = tests;
        this.userAnswers = userAnswers;
        this.hrUsers = hrUsers;
        this.FKuserMailing = FKuserMailing;
        this.avatarUrl = avatarUrl;
    }

    public UsersModel(UserResultDto userResultDto){
        this.id = userResultDto.getId();
        this.firstName = userResultDto.getFirstName();
        this.middleName = userResultDto.getMiddleName();
        this.surname = userResultDto.getSurname();
        this.email = userResultDto.getEmail();
        this.phoneNumber = userResultDto.getPhoneNumber();
        this.login = userResultDto.getLogin();
        this.isActive = userResultDto.isActive();
    }

    public UsersModel(RegisterCommandDto registerCommandDto) {
        this.password = registerCommandDto.getPassword();
        this.email = registerCommandDto.getEmail();
        this.firstName = registerCommandDto.getFirstName();
        this.middleName = registerCommandDto.getMiddleName();
        this.phoneNumber = registerCommandDto.getPhoneNumber();
        this.surname = registerCommandDto.getSurname();
        this.login = registerCommandDto.getLogin();
        this.isActive = true;
    }
}

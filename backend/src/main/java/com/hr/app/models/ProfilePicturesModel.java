package com.hr.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicturesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "profile_picture-user")
    @JsonIgnore
    private UsersModel FK_profilePictureUser;

    @Column(name = "is_current")
    private boolean isCurrent;

    // TODO: nazwa pliku + upload, lub plik jako byte[]
    @Column(name = "file_name")
    private String fileName;

}

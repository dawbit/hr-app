package com.hr.app.repositories;

import com.hr.app.models.database.ProfilePicturesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfilePicturesRepository extends JpaRepository<ProfilePicturesModel, Long> {

    ProfilePicturesModel findById(long id);

}

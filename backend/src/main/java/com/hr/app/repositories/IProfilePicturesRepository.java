package com.hr.app.repositories;

import com.hr.app.models.ProfilePicturesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProfilePicturesRepository extends JpaRepository<ProfilePicturesModel, Long> {

    ProfilePicturesModel findById(long id);

    List<ProfilePicturesModel> findAllByFk_profilepictureuser(long fk_profilepictureuser);

}

package com.hr.app.repositories;

import com.hr.app.models.database.CompanyPicturesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompanyPictureRepository extends JpaRepository<CompanyPicturesModel, Long> {

    CompanyPicturesModel findById(long id);

}

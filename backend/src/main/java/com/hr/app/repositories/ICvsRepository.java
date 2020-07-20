package com.hr.app.repositories;

import com.hr.app.models.CvsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICvsRepository extends JpaRepository<CvsModel, Long> {

    CvsModel findById (long id);

    List<CvsModel> findAllByFk_cvuser(long fk_cvuser);

}

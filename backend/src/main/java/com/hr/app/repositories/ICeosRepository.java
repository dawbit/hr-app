package com.hr.app.repositories;

import com.hr.app.models.CeosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICeosRepository extends JpaRepository<CeosModel, Long> {

    CeosModel findById(long id);

    CeosModel findByFKceoUserId(long id);

}

package com.hr.app.repositories;

import com.hr.app.models.database.DepartmentsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDepartmentsRepository extends JpaRepository<DepartmentsModel, Long> {

    DepartmentsModel findById(long id);

    List<DepartmentsModel> findAllByName(String name);

}


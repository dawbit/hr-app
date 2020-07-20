package com.hr.app.repositories;

import com.hr.app.models.DepartmentsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDepartmentsRepository extends JpaRepository<DepartmentsModel, Long> {

    DepartmentsModel findById(long id);

    List<DepartmentsModel> findAllByCompany_id(long company_id);

}


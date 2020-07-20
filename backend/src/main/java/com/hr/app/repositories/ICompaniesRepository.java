package com.hr.app.repositories;

import com.hr.app.models.CompaniesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICompaniesRepository extends JpaRepository<CompaniesModel, Long> {

    CompaniesModel findById(long id);
    CompaniesModel findByName(String name);

    List<CompaniesModel> findAllByName(String name);
}

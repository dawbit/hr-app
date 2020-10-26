package com.hr.app.repositories;

import com.hr.app.models.database.CompaniesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICompaniesRepository extends JpaRepository<CompaniesModel, Long> {

    CompaniesModel findById(long id);
    CompaniesModel findByName(String name);

    @Query(value = "SELECT * FROM Companies company WHERE company.about LIKE %?1%" +
            " OR company.location LIKE %?1% OR company.name LIKE %?1%", nativeQuery = true)
    List<CompaniesModel> findCompanyByAnything(String value);

    List<CompaniesModel> findAllByName(String name);
}

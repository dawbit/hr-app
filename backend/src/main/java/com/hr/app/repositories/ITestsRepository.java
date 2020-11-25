package com.hr.app.repositories;

import com.hr.app.models.database.TestsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITestsRepository extends JpaRepository<TestsModel, Long> {

    TestsModel findById(long id);
    TestsModel findByName(String name);

    List<TestsModel> findByFKtestCompanyId(long id);
}

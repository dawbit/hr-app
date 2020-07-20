package com.hr.app.repositories;

import com.hr.app.models.TestsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITestsRepository extends JpaRepository<TestsModel, Long> {

    TestsModel findById(long id);

    List<TestsModel> findAllByFk_testuserhr(long fk_testuserhr);
    List<TestsModel> findAllByfK_testcompany(long fk_testcompany);



}

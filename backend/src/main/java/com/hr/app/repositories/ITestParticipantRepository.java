package com.hr.app.repositories;

import com.hr.app.models.database.TestParticipantModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestParticipantRepository extends JpaRepository<TestParticipantModel, Long> {

    TestParticipantModel findById(long id);
    TestParticipantModel findByCode(String code);
    long countByReadAndFKtestCodeuserId(boolean read, long userId);
}

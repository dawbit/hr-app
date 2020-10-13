package com.hr.app.repositories;

import com.hr.app.models.database.TestParticipantModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITestParticipantRepository extends JpaRepository<TestParticipantModel, Long> {

    TestParticipantModel findByCode(String code);

    TestParticipantModel findByFKtestCodeuserIdAndFKtestCodetestId(long userId, long testId);
}

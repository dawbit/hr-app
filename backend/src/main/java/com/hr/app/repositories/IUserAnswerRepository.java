package com.hr.app.repositories;

import com.hr.app.models.database.UserAnswersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserAnswerRepository extends JpaRepository<UserAnswersModel, Long> {

    UserAnswersModel findById(long id);
}

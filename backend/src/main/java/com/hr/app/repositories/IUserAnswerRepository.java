package com.hr.app.repositories;

import com.hr.app.models.UserAnswersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserAnswerRepository extends JpaRepository<UserAnswersModel, Long> {

    UserAnswersModel findById(long id);
}

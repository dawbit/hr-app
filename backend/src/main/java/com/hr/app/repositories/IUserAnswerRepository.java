package com.hr.app.repositories;

import com.hr.app.models.database.UserAnswersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserAnswerRepository extends JpaRepository<UserAnswersModel, Long> {

    UserAnswersModel findById(long id);

    UserAnswersModel findByFKquestionIduserAnswerIdAndFKuserIduserAnswerId(long questionId, long userId );

    List<UserAnswersModel> findAllByFKanswerIduserAnswerId(long answerId);
}

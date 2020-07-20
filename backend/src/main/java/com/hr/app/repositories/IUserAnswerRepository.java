package com.hr.app.repositories;

import com.hr.app.models.UserAnswersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserAnswerRepository extends JpaRepository<UserAnswersModel, Long> {

    UserAnswersModel findById(long id);

    List<UserAnswersModel> findAllByFk_useriduseranswer (long fk_useriduseranswer);
    List<UserAnswersModel> findAllByFk_questioniduseranswer (long fk_questioniduseranswer);
    List<UserAnswersModel> findAllByFk_answeriduseranswer (long fk_answeriduseranswer);
    List<UserAnswersModel> findAllByFk_testiduseranswer (long fk_testiduseranswer);


}

package com.hr.app.repositories;

import com.hr.app.models.AnswersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAnswersRepository extends JpaRepository<AnswersModel, Long> {

    AnswersModel findById(long id);

    List<AnswersModel> findAllByQuestion_id(long id);

}

package com.hr.app.repositories;

import com.hr.app.models.database.AnswersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAnswersRepository extends JpaRepository<AnswersModel, Long> {

    AnswersModel findById(long id);

}

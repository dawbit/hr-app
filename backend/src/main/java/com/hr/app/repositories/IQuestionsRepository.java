package com.hr.app.repositories;

import com.hr.app.models.database.QuestionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionsRepository extends JpaRepository<QuestionsModel, Long> {

    QuestionsModel findById(long id);

}

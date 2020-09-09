package com.hr.app.repositories;

import com.hr.app.models.QuestionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IQuestionsRepository extends JpaRepository<QuestionsModel, Long> {

    QuestionsModel findById(long id);

}

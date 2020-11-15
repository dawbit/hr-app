package com.hr.app.repositories;

import com.hr.app.models.database.MailingModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMailingRepository extends JpaRepository<MailingModel, Long> {

    MailingModel findById(long id);

}

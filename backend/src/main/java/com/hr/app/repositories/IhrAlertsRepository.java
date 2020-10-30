package com.hr.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hr.app.models.database.HrAlertModel;

public interface IhrAlertsRepository extends JpaRepository<HrAlertModel, Long> {

    HrAlertModel findById(long id);

    long countByFKhrAlertAnnouncementIdAndFKhrAlertUserId(long idAnnouncement, long idUser);
}

package com.hr.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hr.app.models.database.HrAlertModel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IHrAlertsRepository extends JpaRepository<HrAlertModel, Long> {

    HrAlertModel findById(long id);

    long countByFKhrAlertAnnouncementIdAndFKhrAlertUserId(long idAnnouncement, long idUser);

    @Query(value = "SELECT * FROM hr_alert " +
            "LEFT JOIN test_participant " +
            "ON hr_alert.test_participant_id = test_participant.id " +
            "LEFT JOIN announcements " +
            "ON hr_alert.announcement_id = announcements.id " +
            "WHERE announcements.company_id = ?1", nativeQuery = true)
    List<HrAlertModel> findHrAlerts(long companyId);
}

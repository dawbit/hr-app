package com.hr.app.repositories;

import com.hr.app.models.database.AnnouncementsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAnnouncementsRepository extends JpaRepository<AnnouncementsModel, Long> {

    AnnouncementsModel findById(long id);

    @Query(value = "SELECT * FROM Announcements announcement JOIN Companies company " +
            "ON company.id = announcement.company_id", nativeQuery = true)
    List<AnnouncementsModel> findAllAnnouncement();

    @Query(value = "SELECT * FROM Announcements announcement JOIN Companies company " +
            "ON company.id = announcement.company_id " +
            "WHERE LOWER(announcement.title) LIKE LOWER(CONCAT('%', ?1,'%')) " +
            "OR LOWER(announcement.description) LIKE LOWER(CONCAT('%', ?1,'%')) " +
            "OR LOWER(company.name) LIKE LOWER(CONCAT('%', ?1,'%')) " +
            "OR LOWER(company.about) LIKE LOWER(CONCAT('%', ?1,'%')) " +
            "OR LOWER(company.location) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
    List<AnnouncementsModel> findAnnouncementByAnything(String value);
}

package com.hr.app.controllers;

import com.hr.app.models.database.AnnouncementsModel;
import com.hr.app.models.dto.AnnouncementsDto;
import com.hr.app.repositories.IAnnouncementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class AnnouncementsController {

    @Autowired
    private IAnnouncementsRepository announcementsRepository;

    // /announcements/find?q='example'
    @GetMapping("/announcements/find")
    public List<AnnouncementsDto> getAllAnnouncementsByAntything(@RequestParam String q) {
        List<AnnouncementsModel> dbResponse;
        List<AnnouncementsDto> responseList = new ArrayList<AnnouncementsDto>();

        dbResponse = announcementsRepository.findAnnouncementByAnything(q);

        for (AnnouncementsModel item : dbResponse ) {
            AnnouncementsDto preparedItem = new AnnouncementsDto(item.getTitle(),item.getDescription(),
                    item.getFKannouncementCompany().getName(), item.getFKannouncementCompany().getAbout(),
                    item.getFKannouncementCompany().getLocation());
            responseList.add(preparedItem);
        }

        return responseList;
    }

}

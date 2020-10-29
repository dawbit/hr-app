package com.hr.app.controllers;

import com.hr.app.models.database.AnnouncementsModel;
import com.hr.app.models.dto.AnnouncementsDto;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.repositories.IAnnouncementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class AnnouncementsController {

    @Autowired
    private IAnnouncementsRepository announcementsRepository;

    @GetMapping("/announcements/all")
    public Object getAllAnnouncements(HttpServletResponse response) {
        try {
            List<AnnouncementsModel> dbResponse;
            dbResponse = announcementsRepository.findAllAnnouncement();
            return prepareResponse(dbResponse);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new ResponseTransfer("Server Error");
        }
    }

    // /announcements/find?q='example'
    @GetMapping("/announcements/find")
    public Object getAllAnnouncementsByAntything(@RequestParam String q, HttpServletResponse response) {
        try {
            List<AnnouncementsModel> dbResponse;
            dbResponse = announcementsRepository.findAnnouncementByAnything(q);
            return prepareResponse(dbResponse);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new ResponseTransfer("Server Error");
        }
    }

    private List<AnnouncementsDto> prepareResponse(List<AnnouncementsModel> dbResponse) {
        List<AnnouncementsDto> responseList = new ArrayList<AnnouncementsDto>();

        for (AnnouncementsModel item : dbResponse ) {
            AnnouncementsDto preparedItem = new AnnouncementsDto(item.getId(), item.getTitle(), item.getDescription(),
                    item.getFKannouncementCompany().getId(), item.getFKannouncementCompany().getName(),
                    item.getFKannouncementCompany().getAbout(), item.getFKannouncementCompany().getLocation());
            responseList.add(preparedItem);
        }
        return responseList;
    }

}

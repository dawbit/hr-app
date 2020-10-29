package com.hr.app.controllers;

import com.hr.app.models.database.AnnouncementsModel;
import com.hr.app.models.database.CompaniesModel;
import com.hr.app.models.database.HrUsersModel;
import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.AnnouncementsDto;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class AnnouncementsController {

    private final String serviceUrlParam = "/announcements";

    @Autowired
    private IAnnouncementsRepository announcementsRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IHrUsersRepository hrUsersRepository;

    @Autowired
    private ICompaniesRepository companiesRepository;

    @PostMapping(serviceUrlParam + "/add")
    public ResponseTransfer addAnnouncement(@RequestBody AnnouncementsDto announcement, HttpServletResponse response) {

        UsersModel usersModel;
        HrUsersModel hrUsersModel;
        CompaniesModel companyModel;
        AnnouncementsModel preparedAnnouncement;

        try {
            usersModel = getUserModel();
            hrUsersModel = getHrUsersModel(usersModel.getId());
            companyModel = getCompanyById(hrUsersModel.getFKhrUserCompany().getId());

            preparedAnnouncement = new AnnouncementsModel(companyModel, hrUsersModel,
                    announcement.getAnnouncementTitle(), announcement.getAnnouncementDescription());

            announcementsRepository.save(preparedAnnouncement);
            return new ResponseTransfer("Announcement added");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }

    @GetMapping(serviceUrlParam + "/all")
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
    @GetMapping(serviceUrlParam + "find")
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

    private UsersModel getUserModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private HrUsersModel getHrUsersModel(long userId) {
        return hrUsersRepository.findByFKhrUserUserId(userId);
    }

    private CompaniesModel getCompanyById(long companyId) {
        return companiesRepository.findById(companyId);
    }

}

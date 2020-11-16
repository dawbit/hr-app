package com.hr.app.controllers;

import com.hr.app.models.database.*;
import com.hr.app.models.dto.AnnouncementsDto;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @Autowired
    private IHrAlertsRepository hrAlertsRepository;

    @Autowired
    private ICeosRepository ceosRepository;


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
            return new ResponseTransfer("Server Error", e.toString());
        }
    }

    // /announcements/find?q='example'
    @GetMapping(serviceUrlParam + "/find")
    public Object getAllAnnouncementsByAntything(@RequestParam String q, HttpServletResponse response) {
        try {
            List<AnnouncementsModel> dbResponse;
            dbResponse = announcementsRepository.findAnnouncementByAnything(q);
            return prepareResponse(dbResponse);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new ResponseTransfer("Server Error", e.toString());
        }
    }

    @PostMapping(serviceUrlParam + "/apply/{id}")
    @ResponseBody
    public ResponseTransfer announcementApply(@PathVariable long id, HttpServletResponse response) {
        UsersModel usersModel;
        AnnouncementsModel announcementsModel;
        HrAlertModel preparedAlert;

        try {
            if ((hrUsersRepository.findById(id) == null && ceosRepository.findById(id) == null) || isAdmin().get()) {
                usersModel = getUserModel();
                announcementsModel = announcementsRepository.findById(id);
                if (hrAlertsRepository.countByFKhrAlertAnnouncementIdAndFKhrAlertUserId(id, usersModel.getId()) < 1) {
                    preparedAlert = new HrAlertModel(announcementsModel, usersModel);
                    hrAlertsRepository.save(preparedAlert);
                    return new ResponseTransfer("Application has been submitted");
                } else {
                    response.setStatus(HttpServletResponse.SC_CONFLICT); //409
                    return new ResponseTransfer("You have already taken part in the recruitment process from your account!");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
                return new ResponseTransfer("HR users and CEOs are not allowed to apply!");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new ResponseTransfer("Server Error", e.toString());
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

    private AtomicBoolean isAdmin() {
        AtomicBoolean isAdmin = new AtomicBoolean(false);

        Collection<? extends GrantedAuthority> getRole =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (getRole != null) {
            getRole.forEach(role -> {
                if (role.toString().equals("ROLE_ADMIN")) {
                    isAdmin.set(true);
                }
            });
        }

        return isAdmin;
    }

}

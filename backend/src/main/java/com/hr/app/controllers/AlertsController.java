package com.hr.app.controllers;

import com.hr.app.models.database.HrAlertModel;
import com.hr.app.models.database.HrUsersModel;
import com.hr.app.models.database.TestParticipantModel;
import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.repositories.IHrAlertsRepository;
import com.hr.app.repositories.IHrUsersRepository;
import com.hr.app.repositories.ITestParticipantRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@CrossOrigin
@RestController
public class AlertsController {

    private final String serviceUrlParam = "/alerts";

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IHrAlertsRepository hrAlertsRepository;

    @Autowired
    private IHrUsersRepository hrUsersRepository;

    @Autowired
    private ITestParticipantRepository testParticipantRepository;


    // endpoint zwracający liczbę nowych powiadomień dla użytkownika
    // status read na true dla użytkownika zostaje ustawiany w momencie, kiedy rozpoczyna quiz, bądź zaznaczy
    // przyciskiem, że przeczytał powiadomienie
    @GetMapping(serviceUrlParam + "/user")
    public Object returnAlerts(HttpServletResponse response) {
        try {
            long userId = getUserModel().getId();
            return testParticipantRepository.countByReadAndFKtestCodeuserId(false, userId);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }

    // endpoint zwracający liczbę nowych powiadomień dla użytkownika HR
    // status read na true dla użytkownika HR zostaje ustawiany w momencie, kiedy przypisze konkretny test
    // użytkownikowi, lub zaznaczy to przyciskiem (niestety, teraz już tego na szybko nie poprawimy, ale wtedy alert
    // straci się dla każdego uzytkownika HR, ponieważ w tabeli z alertami HR jest po prostu status read/true - bez
    // podziału na użytkowników. Ew. możemy to rozwinąć później.
    @GetMapping(serviceUrlParam + "/hr-user")
    public Object returnHrAlertsNumber(HttpServletResponse response) {
        UsersModel usersModel;
        try {
            long userId = getUserModel().getId();
            if (Objects.isNull(getHrUsersModel(userId))) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                return new ResponseTransfer("You cannot perform this operation");
            } else {
                long hrUserCompanyId = getHrUsersModel(userId).getFKhrUserCompany().getId();
                return hrAlertsRepository.countByReadAndFKhrAlertAnnouncementFKannouncementCompanyId(
                        false, hrUserCompanyId);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }

    // ustawianie statusu powiadomienia użytkownika jako przeczytane
    @PostMapping(serviceUrlParam + "/user/{testParticipantId}/setAsRead")
    public Object setAlertAsRead(@PathVariable long testParticipantId, HttpServletResponse response) {
        try {
            long userId = getUserModel().getId();
            TestParticipantModel testParticipantModel = testParticipantRepository.findById(testParticipantId);
            if (testParticipantModel.getFKtestCodeuser().getId() == userId) {
                testParticipantModel.setRead(true);
                testParticipantRepository.save(testParticipantModel);
                return new ResponseTransfer("Status changed");
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                return new ResponseTransfer("You cannot perform this operation");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }

    // ustawianie statusu powiadomienia użytkownika pracującego w dziale HR jako przeczytane
    @PostMapping(serviceUrlParam + "/hr-user/{alertId}/setAsRead")
    public Object setAlertAsReadHR(@PathVariable long alertId, HttpServletResponse response) {
        try {
            long userId = getUserModel().getId();
            long companyId = getHrUsersModel(userId).getFKhrUserCompany().getId();
            HrAlertModel hrAlertModel = hrAlertsRepository.findById(alertId);
            if (hrAlertModel.getFKhrAlertAnnouncement().getFKannouncementCompany().getId() == companyId) {
                hrAlertModel.setRead(true);
                hrAlertsRepository.save(hrAlertModel);
                return new ResponseTransfer("Status changed");
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                return new ResponseTransfer("You cannot perform this operation");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }


    private UsersModel getUserModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private HrUsersModel getHrUsersModel(long userId) {
        return hrUsersRepository.findByFKhrUserUserId(userId);
    }
}

package com.hr.app.controllers;

import com.hr.app.models.api_helpers.AssignQuizDto;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.*;
import com.hr.app.repositories.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class HrPanelController {

    private final String serviceUrlParam = "/hr";

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IHrUsersRepository hrUsersRepository;

    @Autowired
    private IHrAlertsRepository hrAlertsRepository;

    @Autowired
    private ITestParticipantRepository testParticipantRepository;

    @Autowired
    private ITestsRepository testsRepository;

    @Autowired
    private IAnnouncementsRepository announcementsRepository;

    @GetMapping(serviceUrlParam + "/list-of-applications")
    public Object getListOfApplications(HttpServletResponse response) {
        UsersModel usersModel;

        try {
            usersModel = getUserModel();
            long hrUserCompanyId = getHrUsersModel(usersModel.getId()).getFKhrUserCompany().getId();

            return prepareResponse(hrAlertsRepository.findHrAlerts(hrUserCompanyId));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }

    @Transactional
    @PostMapping(serviceUrlParam + "/alert/{alertId}/assign-quiz")
    public Object assignAQuiz(@PathVariable long alertId, @RequestBody AssignQuizDto assignQuizDto,
                              HttpServletResponse response) {
        //TestParticipantModel testParticipantModel = new TestParticipantModel();
        HrAlertModel alert = hrAlertsRepository.findById(alertId);
        String testName = assignQuizDto.getTestName();
        long currentQuestionNumber = 0;
        long startQuizTimeInMilis = 0;
        long testId = assignQuizDto.getTestId();
        String testCode = assignQuizDto.getTestCode();
        long userId = assignQuizDto.getUserId();
        Boolean read = false; //??
        long announcementId = assignQuizDto.getAnnouncementId();

        TestsModel testsModel = testsRepository.findById(testId);
        UsersModel usersModel = usersRepository.findById(userId);
        AnnouncementsModel announcementsModel = announcementsRepository.findById(announcementId);
        TestParticipantModel testParticipantModel = new TestParticipantModel(testsModel, usersModel, testCode,
                currentQuestionNumber, startQuizTimeInMilis, announcementsModel, false);

        //testParticipantRepository
        long savedId = testParticipantRepository.save(testParticipantModel).getId();
        TestParticipantModel testParticipantModelSaved = testParticipantRepository.findById(savedId);
        long saveAID = testParticipantModelSaved.getFKtestAnnouncement().getId();
        long saveUSER = testParticipantModelSaved.getFKtestCodeuser().getId();

        //hrAlertsRepository.updateTestParticipantValue(testParticipantModelSaved, saveAID, saveUSER);

        HrAlertModel hrAlertModel = hrAlertsRepository.findByFKhrAlertUserIdAndFKhrAlertAnnouncementId(saveUSER, saveAID);
        hrAlertModel.setFKhrAlertTestParticipant(testParticipantModelSaved);
        hrAlertsRepository.save(hrAlertModel);
        System.out.println(savedId);

        //hrAlertsRepository.save()

        return null;
    }


    private UsersModel getUserModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private HrUsersModel getHrUsersModel(long userId) {
        return hrUsersRepository.findByFKhrUserUserId(userId);
    }

    private List<HrAlertsDto> prepareResponse(List<HrAlertModel> dbResponse) {
        List<HrAlertsDto> responseList = new ArrayList<HrAlertsDto>();

        for (HrAlertModel item : dbResponse ) {
            SimplyUserDto user = new SimplyUserDto(item.getFKhrAlertUser().getId(), item.getFKhrAlertUser().getLogin());
            SimplyQuizInfoDto quizInfo = new SimplyQuizInfoDto();

            // zakładamy, że przy przypisaniu quizu do użytkownika, od razu przypisujemy też do niego
            // unikalny kod do uruchomienia quizu.
            if (!Objects.isNull(item.getFKhrAlertTestParticipant())) {
                quizInfo = new SimplyQuizInfoDto(
                        item.getFKhrAlertTestParticipant().getFKtestCodetest().getId(),
                        item.getFKhrAlertTestParticipant().getFKtestCodetest().getName(),
                        item.getFKhrAlertTestParticipant().getCode()
                );
            }

            HrAlertsDto preparedItem = new HrAlertsDto(item.getFKhrAlertAnnouncement().getId(), user, quizInfo);
            responseList.add(preparedItem);
        }
        return responseList;
    }

}

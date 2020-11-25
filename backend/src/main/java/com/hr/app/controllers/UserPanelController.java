package com.hr.app.controllers;

import com.hr.app.models.database.HrAlertModel;
import com.hr.app.models.database.MailingModel;
import com.hr.app.models.database.TestParticipantModel;
import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.models.dto.UserPanelListOfAnnoncementsDto;
import com.hr.app.repositories.IHrAlertsRepository;
import com.hr.app.repositories.IMailingRepository;
import com.hr.app.repositories.IQuestionsRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class UserPanelController {

    private final String serviceUrlParam = "/user";

    @Autowired
    IUsersRepository usersRepository;

    @Autowired
    IHrAlertsRepository hrAlertsRepository;

    @Autowired
    IQuestionsRepository questionsRepository;

    @Autowired
    IMailingRepository mailingRepository;


    @GetMapping(serviceUrlParam + "/list-of-applications")
    public Object getListOfApplications(HttpServletResponse response) {
        long userId;
        try {
            userId = getUserModel().getId();
            Object preparedResponse = prepareList(hrAlertsRepository.findByFKhrAlertUserId(userId));
            return preparedResponse;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }

    @GetMapping(serviceUrlParam + "/mailing")
    public Object getListOfMailings(HttpServletResponse response) {
        long mailingId;
        try {
            UsersModel userModel = getUserModel();
            if(Objects.isNull(userModel.getFKuserMailing())) {
                userModel.setFKuserMailing(new MailingModel());
            }
            mailingId = getUserModel().getFKuserMailing().getId();
            return mailingRepository.findById(mailingId);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }

    @PutMapping(serviceUrlParam + "/mailing/edit")
    public Object saveListOfMailings(@RequestBody MailingModel mailingModel, HttpServletResponse response) {
        long mailingId;
        try {
            mailingId = getUserModel().getFKuserMailing().getId();
            if (mailingId == mailingModel.getId()) {
                mailingModel.setMailingNewQuiz(mailingModel.getMailingNewQuiz());
                mailingRepository.save(mailingModel);
                return new ResponseTransfer("E-mail preferences have changed");
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403
                return new ResponseTransfer("You cannot perform this operation");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }


    private UsersModel getUserModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private List<UserPanelListOfAnnoncementsDto> prepareList(List<HrAlertModel> dbResponse){
        List<UserPanelListOfAnnoncementsDto> responseList = new ArrayList<UserPanelListOfAnnoncementsDto>();

        for(HrAlertModel item : dbResponse) {
            UserPanelListOfAnnoncementsDto preparedItem;

            String companyName = item.getFKhrAlertAnnouncement().getFKannouncementCompany().getName();
            String annoncementName = item.getFKhrAlertAnnouncement().getTitle();

            if (!Objects.isNull(item.getFKhrAlertTestParticipant())) {
                long testParticipantId = item.getFKhrAlertTestParticipant().getId();
                boolean isEnded = quizIsEnded(item.getFKhrAlertTestParticipant().getQuestionNumber(),
                        questionsRepository.countByFKquestionTestId(item.getFKhrAlertTestParticipant().getFKtestCodetest().getId()),
                        item.getFKhrAlertTestParticipant());
                preparedItem = new UserPanelListOfAnnoncementsDto(testParticipantId, companyName, annoncementName,
                        item.getFKhrAlertTestParticipant().getCode(), isEnded);
            } else {
                preparedItem = new UserPanelListOfAnnoncementsDto(companyName, annoncementName);
            }
            responseList.add(preparedItem);
        }

        return responseList;
    }

    private boolean quizIsEnded(long questionNumber, long allQuestionNumber, TestParticipantModel testParticipantModel) {
        if(questionNumber==0) {
            return true;
        }
        if(questionNumber > allQuestionNumber) {
            return true;
        }
        return !checkIfUserHasTimeLeftForThisQuiz(testParticipantModel);
    }

    private long getCurrentTimeInMilis() {
        return ZonedDateTime.now().toInstant().toEpochMilli();
    }

    private boolean checkIfUserHasTimeLeftForThisQuiz(TestParticipantModel testParticipantModel) {
        long currentTime = getCurrentTimeInMilis();
        long testStartTime = testParticipantModel.getStartQuizTimeInMilis();
        long timeForTest = testParticipantModel.getFKtestCodetest().getTimeForTestInMilis();

        return  testStartTime + timeForTest > currentTime;
    }
}

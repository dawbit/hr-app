package com.hr.app.controllers;

import com.hr.app.models.database.HrAlertModel;
import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.models.dto.UserPanelListOfAnnoncementsDto;
import com.hr.app.repositories.IHrAlertsRepository;
import com.hr.app.repositories.IQuestionsRepository;
import com.hr.app.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping(serviceUrlParam + "/list-of-applications")
    public Object getListOfApplications(HttpServletResponse response) {
        long userId;
        try {
            userId = getUserModel().getId();
            Object preparedResponse = prepareList(hrAlertsRepository.findByFKhrAlertUserId(userId));
            return preparedResponse;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error", e.toString()); // 500
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
                boolean isEnded = questionsRepository.countByFKquestionTestId(
                        item.getFKhrAlertTestParticipant().getFKtestCodetest().getId()) <
                        item.getFKhrAlertTestParticipant().getQuestionNumber() - 1;
                preparedItem = new UserPanelListOfAnnoncementsDto(testParticipantId, companyName, annoncementName,
                        item.getFKhrAlertTestParticipant().getCode(), isEnded);
            } else {
                preparedItem = new UserPanelListOfAnnoncementsDto(companyName, annoncementName);
            }
            responseList.add(preparedItem);
        }

        return responseList;
    }

}

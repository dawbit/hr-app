package com.hr.app.controllers;

import com.hr.app.models.database.HrAlertModel;
import com.hr.app.models.database.HrUsersModel;
import com.hr.app.models.database.UsersModel;
import com.hr.app.models.dto.*;
import com.hr.app.repositories.IHrAlertsRepository;
import com.hr.app.repositories.IHrUsersRepository;
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
public class HrPanelController {

    private final String serviceUrlParam = "/hr";

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IHrUsersRepository hrUsersRepository;

    @Autowired
    private IHrAlertsRepository hrAlertsRepository;

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

            HrAlertsDto preparedItem = new HrAlertsDto(user, quizInfo);
            responseList.add(preparedItem);
        }
        return responseList;
    }

}

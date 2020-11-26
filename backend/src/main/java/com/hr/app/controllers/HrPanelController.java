package com.hr.app.controllers;

import com.hr.app.mails.CustomMailing;
import com.hr.app.models.api_helpers.AssignQuizDto;
import com.hr.app.models.api_helpers.UserQuestionResultDto;
import com.hr.app.models.api_helpers.UserQuizResultDto;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.HrAlertsDto;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.models.dto.SimplyQuizInfoDto;
import com.hr.app.models.dto.SimplyUserDto;
import com.hr.app.repositories.*;
import com.hr.app.security.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@CrossOrigin
@RestController
public class HrPanelController {

    private final String serviceUrlParam = "/hr";
    private int hashLenght = 12;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IHrUsersRepository hrUsersRepository;

    @Autowired
    private ICvsRepository cvsRepository;

    @Autowired
    private IHrAlertsRepository hrAlertsRepository;

    @Autowired
    private ITestParticipantRepository testParticipantRepository;

    @Autowired
    private IQuestionsRepository questionsRepository;

    @Autowired
    private ITestsRepository testsRepository;

    @Autowired
    private IAnnouncementsRepository announcementsRepository;

    @Autowired
    private CustomMailing sendMail;

    @Autowired
    private ICeosRepository ceosRepository;

    @Autowired
    private IUserAnswerRepository userAnswerRepository;

    @Autowired
    private IAnswersRepository answersRepository;

    @GetMapping(serviceUrlParam + "/user-result/{userId}/{quizId}")
    public Object getuUserResult(HttpServletResponse response, @PathVariable long userId, @PathVariable long quizId) {
        UsersModel currentUser = getUserModel();

        try{
            if(currentUser.getFKuserAccountTypes().getRoleId()==2) {
                CeosModel ceosModel = ceosRepository.findByFKceoUserId(currentUser.getId());
                if(ceosModel == null) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return new ResponseTransfer("FORBIDDEN");
                }
            }
            else if(currentUser.getFKuserAccountTypes().getRoleId()==3) {
                HrUsersModel hrUsersModel = hrUsersRepository.findByFKhrUserUserId(currentUser.getId());
                if(hrUsersModel == null) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return new ResponseTransfer("FORBIDDEN");
                }
            }
            else if(currentUser.getFKuserAccountTypes().getRoleId()==4) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return new ResponseTransfer("FORBIDDEN");
            }
            TestsModel testsModel = testsRepository.findById(quizId);
            if(testsModel==null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return new ResponseTransfer("QUIZ_NOT_FOUND");
            }
            List<QuestionsModel> questionsModelList = questionsRepository.findAllByFKquestionTestId(testsModel.getId());
            ArrayList<UserQuestionResultDto> userQuestionResultDtoArrayList = new ArrayList<>();

            for (QuestionsModel question: questionsModelList) {
                int questionMaxPoints = getMaxQuestionPoints(question);
                UserAnswersModel userAnswersModel = userAnswerRepository.findByFKquestionIduserAnswerIdAndFKuserIduserAnswerId(question.getId(), userId);
                UserQuestionResultDto userQuestionResultDto = new UserQuestionResultDto(question.getText(), questionMaxPoints, userAnswersModel.getFKanswerIduserAnswer().getPoints(), userAnswersModel.getFKanswerIduserAnswer().getText());
                userQuestionResultDtoArrayList.add(userQuestionResultDto);
            }

            return new UserQuizResultDto(testsModel.getId(), userId, testsModel.getName(), getUserLoginById(userId), userQuestionResultDtoArrayList);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }
    }

    private String getUserLoginById(long userId) {
        return usersRepository.findById(userId).getLogin();
    }

    @GetMapping(serviceUrlParam + "/list-of-applications")
    public Object getListOfApplications(HttpServletResponse response) {
        UsersModel usersModel;

        try {
            usersModel = getUserModel();
            long hrUserCompanyId = getHrUsersModel(usersModel.getId()).getFKhrUserCompany().getId();

            return prepareResponse(hrAlertsRepository.findHrAlerts(hrUserCompanyId));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error", e.toString()); // 500
        }
    }

    @Transactional
    @PostMapping(serviceUrlParam + "/alert/{alertId}/assign-quiz")
    public Object assignAQuiz(@PathVariable long alertId, @RequestBody AssignQuizDto assignQuizDto,
                              HttpServletResponse response) {
        try {
            // sprawdzenie, czy dla konkretnego alertu nie został przypisany już wcześniej quiz (test_participant)
            if (!Objects.isNull(hrAlertsRepository.findById(alertId)) &&
                    Objects.isNull(hrAlertsRepository.findById(alertId).getFKhrAlertTestParticipant())) {
                long currentQuestionNumber = 0;
                long startQuizTimeInMilis = 0;
                long testId = assignQuizDto.getTestId();
                String testCodeBasic = assignQuizDto.getTestCode();
                long userId = assignQuizDto.getUserId();
                long announcementId = assignQuizDto.getAnnouncementId();
                // sprawdzanie czy wszystkie wymagane dane są podane;
                if (Stream.of(testId, testCodeBasic, userId, announcementId).anyMatch(Objects::isNull)) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                    return new ResponseTransfer("Request is not complete!");
                }

                TestsModel testsModel = testsRepository.findById(testId);
                UsersModel usersModel = usersRepository.findById(userId);
                // sprawdzanie czy quiz i user istnieje
                if (Objects.isNull(testsModel) || Objects.isNull(usersModel)) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                    return new ResponseTransfer("Data does not exist in the database. Please contact the administrator.");
                }

                String testCode = generateRandomCode(testCodeBasic, testId, announcementId, usersModel.getLogin());

                AnnouncementsModel announcementsModel = announcementsRepository.findById(announcementId);
                TestParticipantModel testParticipantModel = new TestParticipantModel(testsModel, usersModel, testCode,
                        currentQuestionNumber, startQuizTimeInMilis, announcementsModel, false);

                long savedTestParticipantId = testParticipantRepository.save(testParticipantModel).getId();
                TestParticipantModel testParticipantModelSaved = testParticipantRepository.findById(savedTestParticipantId);
                long savedParticipantAnnouncementId = testParticipantModelSaved.getFKtestAnnouncement().getId();
                long savedParticipantUserId = testParticipantModelSaved.getFKtestCodeuser().getId();

                // sprawdzanie, czy wszystkie wymagane dane są podane;
                if (Stream.of(savedTestParticipantId, testParticipantModelSaved.getId(),
                        savedParticipantAnnouncementId, savedParticipantUserId).anyMatch(Objects::isNull)) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                    return new ResponseTransfer("Request is not completed!");
                }

                HrAlertModel hrAlertModel = hrAlertsRepository.findByFKhrAlertUserIdAndFKhrAlertAnnouncementId(
                        savedParticipantUserId, savedParticipantAnnouncementId);
                hrAlertModel.setFKhrAlertTestParticipant(testParticipantModelSaved);
                hrAlertModel.setRead(true);
                hrAlertsRepository.saveAndFlush(hrAlertModel);
                if(Objects.isNull(testParticipantModelSaved.getFKtestCodeuser().getFKuserMailing())) {
                    testParticipantModelSaved.getFKtestCodeuser().setFKuserMailing(new MailingModel());
                    usersRepository.save(testParticipantModelSaved.getFKtestCodeuser());
                }
                // sprawdzanie, czy wszystkie pola wymagane dla mailingu znajdują się w bazie
                if (!Stream.of(testParticipantModelSaved.getFKtestCodeuser(),
                        testParticipantModelSaved.getFKtestCodeuser().getEmail(),
                        testParticipantModelSaved.getFKtestCodeuser(),
                        testParticipantModelSaved.getFKtestCodeuser().getLogin(),
                        testParticipantModelSaved.getFKtestAnnouncement(),
                        testParticipantModelSaved.getFKtestAnnouncement().getTitle()).anyMatch(Objects::isNull)) {
                    String userMail = testParticipantModelSaved.getFKtestCodeuser().getEmail();
                    String userName = testParticipantModelSaved.getFKtestCodeuser().getLogin();
                    String announcementTitle = testParticipantModelSaved.getFKtestAnnouncement().getTitle();

                    sendMail.sendNewQuizCodeMessage(userMail, userName, announcementTitle, testCode);
                }

                return new ResponseTransfer("The user has been assigned a quiz");
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT); // 409
                return new ResponseTransfer("Quiz has already been assigned to this alert");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            return new ResponseTransfer("Internal server error", e.toString());
        }
    }

    private int getMaxQuestionPoints(QuestionsModel questionsModel) {
        List<AnswersModel> answersModelList = answersRepository.findAllByFKanswerQuestionId(questionsModel.getId());
        int maxPoints = 0;
        for (AnswersModel answer: answersModelList) {
            if(answer.getPoints() > maxPoints) {
                maxPoints = answer.getPoints();
            }
        }
        return maxPoints;
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
            CvsModel cvsModel = cvsRepository.findByFKcvUserId(item.getFKhrAlertUser().getId());
            String cvUrl=null;
            if(cvsModel!= null) {
                cvUrl = cvsModel.getFileName();
            }
            SimplyUserDto user = new SimplyUserDto(item.getFKhrAlertUser().getId(), item.getFKhrAlertUser().getLogin(), cvUrl);
            SimplyQuizInfoDto quizInfo = new SimplyQuizInfoDto();
            boolean isEnded =false;
            // zakładamy, że przy przypisaniu quizu do użytkownika, od razu przypisujemy też do niego
            // unikalny kod do uruchomienia quizu.
            if (!Objects.isNull(item.getFKhrAlertTestParticipant())) {
                quizInfo = new SimplyQuizInfoDto(
                        item.getFKhrAlertTestParticipant().getFKtestCodetest().getId(),
                        item.getFKhrAlertTestParticipant().getFKtestCodetest().getName(),
                        item.getFKhrAlertTestParticipant().getCode()
                );
                isEnded = quizIsEnded(item.getFKhrAlertTestParticipant().getQuestionNumber(),
                        questionsRepository.countByFKquestionTestId(item.getFKhrAlertTestParticipant().getFKtestCodetest().getId()),
                        item.getFKhrAlertTestParticipant());
            }

            HrAlertsDto preparedItem = new HrAlertsDto(item.getId(), item.getFKhrAlertAnnouncement().getId(), item.getFKhrAlertAnnouncement().getTitle(), user,
                    quizInfo, isEnded);
            responseList.add(preparedItem);
        }
        return responseList;
    }

    private String generateRandomCode(String basicCode, long testId, long announcementId, String login) {
        String hash = testId + announcementId + login + "";
        RandomString hashedString = new RandomString(hashLenght, new SecureRandom(), hash);
        String code = basicCode + "_" + hashedString.nextString();
        if (Objects.nonNull(testParticipantRepository.findByCode(code)))
            return generateRandomCode(basicCode, testId, announcementId, login);
        else return code;
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

package com.hr.app.controllers;

import com.hr.app.models.api_helpers.AnswerCommandDto;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.ResponseTransfer;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.List;

@CrossOrigin
@RestController
public class AnswerController {

    @Autowired
    private IQuestionsRepository questionsRepository;

    @Autowired
    private IAnswersRepository answersRepository;

    @Autowired
    private ITestsRepository testsRepository;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IHrUsersRepository hrUsersRepository;

    @Autowired
    private ICompaniesRepository companiesRepository;

    @Autowired
    private ITestParticipantRepository testParticipantRepository;

    @Autowired
    private IUserAnswerRepository userAnswerRepository;

    @Transactional
    @PostMapping("question/setanswer")
    public ResponseTransfer setAnswer(@RequestBody AnswerCommandDto answerCommandDto, HttpServletResponse response) {
        UsersModel usersModel;
        QuestionsModel questionsModel;
        TestsModel testsModel;
        AnswersModel answersModel;
        TestParticipantModel testParticipantModel;
        List<QuestionsModel> questionsModelList;

        try {
            usersModel = getUserModel();
            answersModel = getAnswersModel(answerCommandDto.getAnswerId());
            questionsModel = getQuestionModel(answerCommandDto.getQuestionId());
            testsModel= questionsModel.getFKquestionTest();
            testParticipantModel = testParticipantRepository.findByCode(answerCommandDto.getTestCode());
            questionsModelList = getQuestionsModelList(testsModel);
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new ResponseTransfer("Server Error");
        }
        if(answersModel == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400
            return new ResponseTransfer("Bad Request");
        }
        if(testParticipantModel==null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ResponseTransfer("Wrong Test code");
        }

        if(usersModel.getId() != testParticipantModel.getFKtestCodeuser().getId()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ResponseTransfer("You are not allowed to participate in this quiz");
        }

        if(testParticipantModel.getQuestionNumber()==0) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ResponseTransfer("Time has been finished");
        }

        if(!checkIfUserHasTimeLeftForThisQuiz(testParticipantModel)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ResponseTransfer("Time has been finished");
        }


        if(!testsModel.isPossibleToBack()) {
            if(testParticipantModel.getQuestionNumber() -1 != getQuestionNumber(questionsModelList, questionsModel)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return new ResponseTransfer("You cannot answer this question");
            }
        }
        UserAnswersModel userAnswersModel;
        try {
            userAnswersModel = getUserAnswersModel(questionsModel, usersModel);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            System.out.println(e.toString());
            return new ResponseTransfer("Server Error");
        }
        if (userAnswersModel!= null) {
            userAnswersModel.setFKanswerIduserAnswer(answersModel);
        } else {
            userAnswersModel = new UserAnswersModel(usersModel, questionsModel, answersModel, testsModel);
        }

        try{
            userAnswerRepository.save(userAnswersModel);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new ResponseTransfer("Server Error");
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new ResponseTransfer("SUCCESS");
    }

    private UsersModel getUserModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private QuestionsModel getQuestionModel(long questionId) {
        return questionsRepository.findById(questionId);
    }

    private AnswersModel getAnswersModel(long answerId) {
        return answersRepository.findById(answerId);
    }

    private TestsModel getTestsModel(long testId){
        return testsRepository.findById(testId);
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

    private UserAnswersModel getUserAnswersModel(QuestionsModel questionsModel, UsersModel usersModel) {
        return userAnswerRepository.findByFKquestionIduserAnswerIdAndFKuserIduserAnswerId(questionsModel.getId(), usersModel.getId());
    }

    private List<QuestionsModel> getQuestionsModelList(TestsModel testsModel) {
        return questionsRepository.findAllByFKquestionTestId(testsModel.getId());
    }

    private long getQuestionNumber(List<QuestionsModel> questionsModelList, QuestionsModel questionsModel) {
        long i=0;
        for(QuestionsModel questionItem : questionsModelList) {
            i++;
            if(questionItem.getId()==questionsModel.getId()) {
                return i;
            }
        }
        return -1;
    }
}

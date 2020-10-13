package com.hr.app.controllers;

import com.hr.app.models.api_helpers.AnswerCommandDto;
import com.hr.app.models.api_helpers.QuestionJsonModel;
import com.hr.app.models.api_helpers.ResponseTransfer;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.QuestionDto;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
    private ITestParticipantRepository testCodeRepository;

    @Autowired
    private IUserAnswerRepository userAnswerRepository;

    //TODO zabezpieczenia
    @PostMapping("question/setanswer")
    public ResponseTransfer setAnswer(@RequestBody AnswerCommandDto answerCommandDto, HttpServletResponse response) {
        UsersModel usersModel;
        QuestionsModel questionsModel;
        TestsModel testsModel;
        AnswersModel answersModel;

        try {
            usersModel = getUserModel();
            questionsModel = getQuestionModel(answerCommandDto.getQuestionId());
            answersModel = getAnswersModel(answerCommandDto.getAnswerId());
            testsModel= getTestsModel(questionsModel.getFKquestionTest().getId());
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new ResponseTransfer("Server Error");
        }
        if(answersModel == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400
            return new ResponseTransfer("Bad Request");
        }
        UserAnswersModel userAnswersModel = new UserAnswersModel(usersModel, questionsModel, answersModel, testsModel);

        userAnswerRepository.save(userAnswersModel);

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
}

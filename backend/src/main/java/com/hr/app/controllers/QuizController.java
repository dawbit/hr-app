package com.hr.app.controllers;

import com.hr.app.models.*;
import com.hr.app.repositories.*;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class QuizController {

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

    @Transactional
    @PostMapping("quiz/add")
    public ResponseTransfer addQuiz(@RequestBody QuizModel quizModel) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        TestsModel testsModel = quizModel.getTestsModel();
        List<QuestionJsonModel> questionJsonModels = quizModel.getQuestionsModel();

        long companyId = getUserCompanyId();

        if(companyId == -1) {
            return new ResponseTransfer("User cannot add Quiz");
        }
        else {
            testsModel.setFKtestCompany(companiesRepository.findById(companyId));
            testsModel.setFKtestUserHr(usersRepository.findByLogin(name));
            try {
                testsRepository.save(testsModel);
                for (QuestionJsonModel questionObject: questionJsonModels) {
                    saveQuestion(testsModel, questionObject);
                }
            } catch (Exception e) {
                return new ResponseTransfer("Failed to save quiz");
            }
            return new ResponseTransfer("Quiz successfully saved");
        }
    }

    @Transactional
    @PostMapping("question/add")
    public ResponseTransfer addQuestion(@RequestBody QuestionJsonModel questionJsonModel) {

        long companyId = getUserCompanyId();

        if(companyId == -1){
            return new ResponseTransfer("User cannot add Question");
        }
        else {
            TestsModel testsModel = testsRepository.findById(questionJsonModel.getTestId());
            if(testsModel.getId()==questionJsonModel.getTestId()){   //
                try{
                    saveQuestion(testsModel, questionJsonModel);
                    return new ResponseTransfer("Pytanie zostało dodane");
                }
                catch (Exception e)
                {
                    return new ResponseTransfer("Błąd i pytanie nie zostało dodane");
                }
            }
            else {
                return new ResponseTransfer("Quiz nie należy do kompanii");
            }
        }
    }

    private void saveQuestion(TestsModel testsModel, QuestionJsonModel questionJsonModels) {
        QuestionsModel questionsModel = questionJsonModels.getQuestionsModel();
        List<AnswersModel> answersModels =questionJsonModels.getAnswersModel();

        questionsModel.setFKquestionTest(testsModel);
        questionsRepository.save(questionsModel);

        for (AnswersModel answerObject: answersModels) {
            answerObject.setFKanswerQuestion(questionsModel);
            answersRepository.save(answerObject);
        }
    }

    private long getUserCompanyId(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        HrUsersModel hrUsersModel = hrUsersRepository.findByFKhrUserUserId(usersRepository.findByLogin(name).getId());
        if(hrUsersModel==null){
            return -1;
        }
        else {
            return hrUsersModel.getFKhrUserCompany().getId();
        }
    }
}

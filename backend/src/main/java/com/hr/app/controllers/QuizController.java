package com.hr.app.controllers;

import com.hr.app.enums.ResponseEnum;
import com.hr.app.enums.ResponseEnumOperations;
import com.hr.app.models.api_helpers.AddQuestionCommandDto;
import com.hr.app.models.api_helpers.AddQuizCommandDto;
import com.hr.app.models.api_helpers.QuizQuestionCommandDto;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.*;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ITestParticipantRepository testParticipantRepository;

    @Transactional
    @PostMapping("quiz/add")
    public ResponseTransfer addQuiz(@RequestBody AddQuizCommandDto addQuizCommandDto, HttpServletResponse response) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        UsersModel usersModel;
        HrUsersModel hrUsersModel;

        try {
            usersModel = getUserModel();
            hrUsersModel = getHrUsersModel(usersModel.getId());
        } catch (Exception e) {
            return new ResponseTransfer("Internal server error");
        }

        TestsModel testsModel = addQuizCommandDto.getTestsModel();
        List<AddQuestionCommandDto> questionCommandDtoList = addQuizCommandDto.getListOfQuestionCommandDto();
        CompaniesModel companiesModel = hrUsersModel.getFKhrUserCompany();

        testsModel.setFKtestCompany(companiesModel);
        testsModel.setFKtestUserHr(usersModel);

        try {
            testsRepository.save(testsModel);
            for (AddQuestionCommandDto questionObject: questionCommandDtoList) {
                QuestionsModel questionsModel = questionObject.getQuestionsModel();
                List<AnswersModel> answersModels =questionObject.getAnswersModel();

                questionsModel.setFKquestionTest(testsModel);
                questionsRepository.save(questionsModel);

                for (AnswersModel answerObject: answersModels) {
                    answerObject.setFKanswerQuestion(questionsModel);
                    answersRepository.save(answerObject);
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return new ResponseTransfer("Quiz saved successfully");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println(e.toString());
            return new ResponseTransfer("Internal server error");
        }
    }

    //TODO chyba do usunięcia, póki co zostaje ale zakomentowane bo kod nie działa
//    @Transactional
//    @PostMapping("question/add")
//    public ResponseTransfer addQuestion(@RequestBody QuestionJsonModel questionJsonModel) {
//
//        long companyId = getUserCompanyId();
//
//        if(companyId == -1){
//            return new ResponseTransfer("User cannot add Question");
//        }
//        else {
//            TestsModel testsModel = testsRepository.findById(questionJsonModel.getTestId());
//            if(testsModel.getId()==questionJsonModel.getTestId()){
//                try{
//                    saveQuestion(testsModel, questionJsonModel);
//                    return new ResponseTransfer("Pytanie zostało dodane");
//                }
//                catch (Exception e)
//                {
//                    return new ResponseTransfer("Błąd i pytanie nie zostało dodane");
//                }
//            }
//            else {
//                return new ResponseTransfer("Quiz nie należy do kompanii");
//            }
//        }
//    }

    //TODO chyba do usunięcia, póki co zostaje
    // Jeśli chodzi o apkę to tylko z tego korzystamy, nawet jak nie można cofać
    @GetMapping("quiz/backpossible/{quizid}")
    public CompleteQuizResultDto getInitialQuiz(@PathVariable long quizid){
        TestsModel quiz = testsRepository.findById(quizid);
        CompleteQuizResultDto completeQuizResultDto = new CompleteQuizResultDto(quiz);
        ArrayList<AnswerResultDto> answerResultDto;

        List<QuestionsModel> listOfQuestionModel = questionsRepository.findAllByFKquestionTestId(completeQuizResultDto.getId());

        ArrayList<QuestionResultDto> listOfQuestionResultDto = new ArrayList<QuestionResultDto>();

        for (QuestionsModel questionsModel : listOfQuestionModel) {
            QuestionResultDto questionResultDto = new QuestionResultDto(questionsModel);
            listOfQuestionResultDto.add(questionResultDto);
        }

        for (QuestionResultDto questionResultDto : listOfQuestionResultDto) {
            List<AnswersModel> listOfAnswersModel = answersRepository.findAllByFKanswerQuestionId(questionResultDto.getId());
            answerResultDto = new ArrayList<AnswerResultDto>();
            for (AnswersModel answerModel: listOfAnswersModel) {
                AnswerResultDto answerResultDto1 = new AnswerResultDto(answerModel);
                answerResultDto.add(answerResultDto1);
            }
            questionResultDto.setAnswers(answerResultDto);
        }

        completeQuizResultDto.setListOfQuestions(listOfQuestionResultDto);

        return completeQuizResultDto;
    }

    @GetMapping("quiz/getQuizInformations/{quizcode}")
    public Object getQuizInformations(@PathVariable String quizcode, HttpServletResponse response) {

        System.out.println("test");
        TestParticipantModel testParticipantModel;
        UsersModel usersModel;
        TestsModel testsModel;
        List<QuestionsModel> listOfQuestions;
        try {
            testParticipantModel = getTestCodeModelByTestCode(quizcode);
            if(testParticipantModel== null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return new QuizCodeDto(ResponseEnum.TEST_NOT_FOUND);
            }
            usersModel = getUserModel();
            testsModel = testParticipantModel.getFKtestCodetest();
            listOfQuestions = getAllQuestionFromQuizId(testsModel.getId());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println(e.toString());
            return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
        }

        if(!testsModel.isActive()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
            return new QuizCodeDto(ResponseEnum.NO_PERMISSION);
        }

        if(usersModel.getId() != testParticipantModel.getFKtestCodeuser().getId()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new QuizCodeDto(ResponseEnum.NO_PERMISSION);
        }

        if(testParticipantModel.getQuestionNumber()==0) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new QuizCodeDto(ResponseEnum.NO_PERMISSION);
        }

        if(testParticipantModel.getStartQuizTimeInMilis() == 0) {
            try {
                testParticipantModel.setStartQuizTimeInMilis(getCurrentTimeInMilis());
                testParticipantRepository.save(testParticipantModel);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                System.out.println(e.toString());
                return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
            }
        }
        try {
            testParticipantModel.setStartQuizTimeInMilis(getCurrentTimeInMilis());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println(e.toString());
            return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
        }


        response.setStatus(HttpServletResponse.SC_OK); // 200
        return new QuizInformationsResultDto(testsModel.getId(),
                listOfQuestions.size(),
                testsModel.isPossibleToBack(),
                testsModel.getTimeForTestInMilis());
    }

    // TODO change enum respons
    // Zamieniono na @RequestParam - ponieważ @RequestBody w GET jest niezgodne z
    // RFC (https://www.ietf.org/rfc/rfc2616.txt) i Angular nie pozwala na takie działanie
    @Transactional
    @GetMapping("quiz/quizquestion/{quizId}/{testCode}/{questionNumber}")
    public Object getQuizQuestion(@PathVariable String quizId,
                                             @PathVariable String testCode,
                                             @PathVariable String questionNumber,
                                             HttpServletResponse response){
        //inicjalizacja niezbednych zmiennych modeli

        UsersModel usersModel;
        TestsModel testsModel;
        List<QuestionsModel> listOfQuestions;
        TestParticipantModel testParticipantModel;
        QuizQuestionCommandDto quizQuestionCommandDto = new QuizQuestionCommandDto(
                Long.parseLong(quizId), Long.parseLong(questionNumber), testCode
        );

        //wprowadzenie zmiennych
        try {
            usersModel = getUserModel();
            testsModel = getTestModelByTestId(quizQuestionCommandDto.getQuizid());
            listOfQuestions = getAllQuestionFromQuizId(quizQuestionCommandDto.getQuizid());
            testParticipantModel = testParticipantRepository.findByCode(quizQuestionCommandDto.getTestCode());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
        }

        //jesli ktoras jest nullem to nie znaleziono testu
        if(testsModel==null || usersModel==null || listOfQuestions == null || testParticipantRepository == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
            return new QuizCodeDto(ResponseEnum.TEST_NOT_FOUND);
        }

        //nieaktywny test? wywalamy blad
        if(!checkIfTestIsActive(testsModel)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
            return new QuizCodeDto(ResponseEnum.INACTIVE_QUIZ);
        }

        if(quizQuestionCommandDto.getQuestionnumber()==0) {
            testParticipantModel.setQuestionNumber(0);
            try {
                testParticipantRepository.save(testParticipantModel);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
                return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return new QuizCodeDto(ResponseEnum.QUIZ_AREADY_SOLVED);
        }

        if (testParticipantModel.getQuestionNumber()==0) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new QuizCodeDto(ResponseEnum.QUIZ_AREADY_SOLVED);
        }

        if(!(testParticipantModel.getFKtestCodeuser().getId() == usersModel.getId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new QuizCodeDto(ResponseEnum.NO_PERMISSION);
        }

        if(!checkIfUserHasTimeLeftForThisQuiz(testParticipantModel)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new QuizCodeDto(ResponseEnum.QUIZ_AREADY_SOLVED);
        }

        boolean isBackPossible = checkIfQuizIsBackPossible(testsModel);

        if(!checkIfQuestionExistsInQuiz(listOfQuestions, quizQuestionCommandDto.getQuestionnumber())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new QuizCodeDto(ResponseEnum.BAD_REQUEST);
        }

        if(isBackPossible) {

            QuestionsModel questionsModel = getExpectedQuestionModel(listOfQuestions, quizQuestionCommandDto.getQuestionnumber());

            List<AnswersModel> listOfAnswersModel;

            try {
                listOfAnswersModel = getAnswersByQuestionId(questionsModel.getId());
            }
            catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return getQuestionDtoModel(questionsModel, listOfAnswersModel);
        }
        else {

            long questionNumberToReturn = testParticipantModel.getQuestionNumber();

            if(quizQuestionCommandDto.getQuestionnumber()!= questionNumberToReturn) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new QuizCodeDto(ResponseEnum.BAD_REQUEST);
            }

            try {
                testParticipantModel.setQuestionNumber(questionNumberToReturn+1);
                testParticipantRepository.save(testParticipantModel);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
            }

            QuestionsModel questionsModel = getExpectedQuestionModel(listOfQuestions, questionNumberToReturn);

            List<AnswersModel> listOfAnswersModel;

            try {
                listOfAnswersModel = getAnswersByQuestionId(questionsModel.getId());
            }
            catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
            }
            return getQuestionDtoModel(questionsModel, listOfAnswersModel);
        }
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

    private UsersModel getUserModel() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByLogin(name);
    }

    private TestsModel getTestModelByTestId(long testId) {
        return testsRepository.findById(testId);
    }

    private List<AnswersModel> getAnswersByQuestionId(long questionId) {
        return answersRepository.findAllByFKanswerQuestionId(questionId);
    }

    private List<QuestionsModel> getAllQuestionFromQuizId(long quizId) {
        return questionsRepository.findAllByFKquestionTestId(quizId);
    }

    private boolean checkIfQuestionExistsInQuiz(List<QuestionsModel> listofQuestions, long questionNumber) {
        if(listofQuestions.size() >= questionNumber) {
            return true;
        }
        else {
            return false;
        }
    }

    private QuestionsModel getExpectedQuestionModel(List<QuestionsModel> listofQuestions, long questionNumber) {
        return listofQuestions.get((int) questionNumber -1);
    }

    private boolean checkIfQuizIsBackPossible(TestsModel testsModel) {
        return testsModel.isPossibleToBack();
    }

    private boolean checkIfTestIsActive(TestsModel testsModel ) {
        return testsModel.isActive();
    }

    private QuestionResultDto getQuestionDtoModel(QuestionsModel questionsModel, List<AnswersModel> answersModelList){
        return new QuestionResultDto(questionsModel, answersModelList, ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SUCCESS));
    }

    private TestParticipantModel getTestCodeModelByTestCode(String testCode) {
        return testParticipantRepository.findByCode(testCode);
    }

    private HrUsersModel getHrUsersModel(long userId) {
        return hrUsersRepository.findByFKhrUserUserId(userId);
    }
}
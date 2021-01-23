package com.hr.app.controllers;

import com.hr.app.enums.ResponseEnum;
import com.hr.app.models.api_helpers.AddQuestionCommandDto;
import com.hr.app.models.api_helpers.AddQuizCommandDto;
import com.hr.app.models.api_helpers.AnswerModelDto;
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
import java.util.Objects;

@CrossOrigin
@RestController
public class QuizController {

    private final String serviceUrlParam = "/quiz";

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
    private ICeosRepository ceosRepository;

    @Autowired
    private ICompaniesRepository companiesRepository;

    @Autowired
    private ITestParticipantRepository testParticipantRepository;

    @Transactional
    @PostMapping("quiz/add")
    public ResponseTransfer addQuiz(@RequestBody AddQuizCommandDto addQuizCommandDtoType, HttpServletResponse response) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        AddQuizCommandDto addQuizCommandDto = addQuizCommandDtoType;
        UsersModel usersModel;
        HrUsersModel hrUsersModel;
        String newQuizName;

        if(addQuizCommandDto.getListOfQuestionCommandDto().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseTransfer("ZERO_QUESTIONS");
        }

        for (AddQuestionCommandDto questionCommandDto: addQuizCommandDto.getListOfQuestionCommandDto()) {
            if(questionCommandDto.getQuestionsModel()==null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new ResponseTransfer("ONE_OF_QUESTIONS_IS_EMPTY");
            }
            if(questionCommandDto.getAnswersModel().size() <2 ) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new ResponseTransfer("EACH_QUESTION_NEEDS_AT_LEAST_TWO_ANSWERS");
            }
        }

        try {
            usersModel = getUserModel();
            hrUsersModel = getHrUsersModel(usersModel.getId());
            newQuizName = checkAndGenerateQuizName(addQuizCommandDto.getTestsModel().getName(), hrUsersModel.getFKhrUserCompany().getName());
            if(newQuizName==null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return new ResponseTransfer("QUIZ_NAME_EXISTS");
            }
            if(addQuizCommandDto.getTestsModel().getTimeForTestInMilis() <60000) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new ResponseTransfer("TOO_SHORT_TIME");
            }
            addQuizCommandDto.getTestsModel().setName(newQuizName);
        } catch (Exception e) {
            return new ResponseTransfer("Internal server error");
        }

        TestsModel testsModel = new TestsModel(addQuizCommandDto.getTestsModel());
        List<AddQuestionCommandDto> questionCommandDtoList = addQuizCommandDto.getListOfQuestionCommandDto();
        CompaniesModel companiesModel = hrUsersModel.getFKhrUserCompany();

        testsModel.setFKtestCompany(companiesModel);
        testsModel.setFKtestUserHr(usersModel);
        testsModel.setActive(true);

        try {
            testsRepository.save(testsModel);
            for (AddQuestionCommandDto questionObject: questionCommandDtoList) {

                QuestionsModel questionsModel = new QuestionsModel(questionObject.getQuestionsModel());
                ArrayList<AnswersModel> answersModelsList = new ArrayList<>();
                for(AnswerModelDto answerModelDto: questionObject.getAnswersModel()) {
                    AnswersModel answersModel = new AnswersModel(answerModelDto);
                    answersModelsList.add(answersModel);
                }

                questionsModel.setFKquestionTest(testsModel);
                questionsRepository.save(questionsModel);

                for (AnswersModel answerObject: answersModelsList) {
                    answerObject.setFKanswerQuestion(questionsModel);
                    answersRepository.save(answerObject);
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return new ResponseTransfer("Quiz saved successfully");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseTransfer("Internal server error");
        }
    }

    @GetMapping(serviceUrlParam + "/list")
    public Object quizList() {
        UsersModel usersModel;
        HrUsersModel hrUsersModel = null;
        CeosModel ceosModel = null;

        try {
            usersModel = getUserModel();
            Boolean isHrUser = Objects.nonNull(getHrUsersModel(usersModel.getId()));
            Boolean isCeoUser = Objects.nonNull(getCeosUserModel(usersModel.getId()));

            long companyId = 0L;
            if (isHrUser) {
                hrUsersModel = hrUsersRepository.findByFKhrUserUserId(getUserModel().getId());
                companyId = hrUsersModel.getFKhrUserCompany().getId();
            } else if (isCeoUser) {
                ceosModel = ceosRepository.findByFKceoUserId(getUserModel().getId());
                companyId = ceosModel.getFKceoCompany().getId();
            } else {
                return new ResponseTransfer("You are not a HR or CEO user");
            }

        List<TestsModel> dbResponse = testsRepository.findByFKtestCompanyId(companyId);
        List<QuizInformationsResultExtendsDto> responseList = new ArrayList<>();
        for (TestsModel item : dbResponse) {
            QuizInformationsResultExtendsDto preparedItem = new QuizInformationsResultExtendsDto(item.getId(),
                    item.getName(), item.getFKtestCompany().getId(), item.getFKtestUserHr().getId());
            responseList.add(preparedItem);
        }
        return responseList;

        } catch (Exception e) {
            return new ResponseTransfer("Internal server error", e.toString()); // 500
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
                return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
            }
        }

        if(!checkIfUserHasTimeLeftForThisQuiz(testParticipantModel)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new QuizCodeDto(ResponseEnum.QUIZ_AREADY_SOLVED);
        }

        if(!testsModel.isPossibleToBack() && testParticipantModel.getQuestionNumber() > listOfQuestions.size()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new QuizCodeDto(ResponseEnum.QUIZ_AREADY_SOLVED);
        }

        // ustawienie pola o rozpoczęciu quizu jako przeczytane
        try {
            testParticipantModel.setRead(true);
            testParticipantRepository.save(testParticipantModel);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new QuizCodeDto(ResponseEnum.SERVER_ERROR);
        }
        long timeLeft = getTimeLeftForQuiz(testParticipantModel);
        response.setStatus(HttpServletResponse.SC_OK); // 200
        return new QuizInformationsResultDto(testsModel.getId(),
                listOfQuestions.size(),
                testsModel.isPossibleToBack(),
                timeLeft,
                ResponseEnum.SUCCESS,
                testParticipantModel.getQuestionNumber());
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
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
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

    private long getTimeLeftForQuiz(TestParticipantModel testParticipantModel) {
        long currentTime = getCurrentTimeInMilis();
        long testStartTime = testParticipantModel.getStartQuizTimeInMilis();
        long timeForTest = testParticipantModel.getFKtestCodetest().getTimeForTestInMilis();

        return testStartTime + timeForTest - currentTime;
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
        return new QuestionResultDto(questionsModel, answersModelList, ResponseEnum.SUCCESS);
    }

    private TestParticipantModel getTestCodeModelByTestCode(String testCode) {
        return testParticipantRepository.findByCode(testCode);
    }

    private String checkAndGenerateQuizName(String quizName, String compantyName) {
        StringBuilder newQuizName = new StringBuilder(compantyName + "_" + quizName);
        for(int i=0; i<100; i++) {
            TestsModel testsModel = testsRepository.findByName(newQuizName.toString());
            if(testsModel==null) {
                return newQuizName.toString();
            }
            newQuizName.append("_").append(i);
        }
        return null;
    }

    private HrUsersModel getHrUsersModel(long userId) {
        return hrUsersRepository.findByFKhrUserUserId(userId);
    }

    private CeosModel getCeosUserModel(long userId) {
        return ceosRepository.findByFKceoUserId(userId);
    }
}
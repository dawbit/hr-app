package com.hr.app.controllers;

import com.hr.app.enums.ResponseEnum;
import com.hr.app.enums.ResponseEnumOperations;
import com.hr.app.models.api_helpers.QuizQuestionCommandDto;
import com.hr.app.models.api_helpers.QuestionJsonModel;
import com.hr.app.models.api_helpers.QuizModel;
import com.hr.app.models.api_helpers.ResponseTransfer;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.AnswerDto;
import com.hr.app.models.dto.CompleteQuizDto;
import com.hr.app.models.dto.QuestionDto;
import com.hr.app.models.dto.QuizInformationsDto;
import com.hr.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    private ITestParticipantRepository testCodeRepository;

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
            if(testsModel.getId()==questionJsonModel.getTestId()){
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

    // Jeśli chodzi o apkę to tylko z tego korzystamy, nawet jak nie można cofać
    @GetMapping("quiz/backpossible/{quizid}")
    public CompleteQuizDto getInitialQuiz(@PathVariable long quizid){
        TestsModel quiz = testsRepository.findById(quizid);
        CompleteQuizDto completeQuizDto = new CompleteQuizDto(quiz);
        ArrayList<AnswerDto> answerDto;

        List<QuestionsModel> listOfQuestionModel = questionsRepository.findAllByFKquestionTestId(completeQuizDto.getId());

        ArrayList<QuestionDto> listOfQuestionDto = new ArrayList<QuestionDto>();

        for (QuestionsModel questionsModel : listOfQuestionModel) {
            QuestionDto questionDto = new QuestionDto(questionsModel);
            listOfQuestionDto.add(questionDto);
        }

        for (QuestionDto questionDto : listOfQuestionDto) {
            List<AnswersModel> listOfAnswersModel = answersRepository.findAllByFKanswerQuestionId(questionDto.getId());
            answerDto = new ArrayList<AnswerDto>();
            for (AnswersModel answerModel: listOfAnswersModel) {
                AnswerDto answerDto1 = new AnswerDto(answerModel);
                answerDto.add(answerDto1);
            }
            questionDto.setAnswers(answerDto);
        }

        completeQuizDto.setListOfQuestions(listOfQuestionDto);

        return completeQuizDto;
    }

    @GetMapping("quiz/getQuizInformations/{quizcode}")
    public QuizInformationsDto getQuizInformations(@PathVariable String quizcode, HttpServletResponse response) {

        TestParticipantModel testParticipantModel;
        UsersModel usersModel;
        TestsModel testsModel;
        List<QuestionsModel> listOfQuestions;

        try {
            testParticipantModel = getTestCodeModelByTestCode(quizcode);
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new QuizInformationsDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }

        if(testParticipantModel == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
            return new QuizInformationsDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.BAD_TEST_CODE));
        }

        try {
            testsModel = getTestModelByTestId(testParticipantModel.getFKtest().getId());
            usersModel = getUserModel();
            listOfQuestions = getAllQuestionFromQuizId(testsModel.getId());
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
            return new QuizInformationsDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.BAD_TEST_CODE));
        }

        if(!testsModel.isActive()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
            return new QuizInformationsDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.INACTIVE_QUIZ));
        }

        if(usersModel==null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
            return new QuizInformationsDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.NO_PERMISSION));
        }
        else if(!checkIfUserCanSolveThisQuiz(usersModel, testParticipantModel.getFKuser().getId())){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
            return new QuizInformationsDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.NO_PERMISSION));
        }

        if(checkIfUserAlreadySolvedThisQuiz(testParticipantModel)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
            return new QuizInformationsDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUIZ_AREADY_SOLVED));
        }


        response.setStatus(HttpServletResponse.SC_OK); // 200
        return new QuizInformationsDto(testsModel.getId(),
                listOfQuestions.size(),
                testsModel.isPossibleToBack(),
                ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SUCCESS));
    }

//    @GetMapping("quiz/{quizcode}")
//    public QuestionDto getQuizQuestionWithQuizCode(@RequestParam String quizcode, HttpServletResponse response){
//        UsersModel usersModel;
//        TestsModel testsModel;
//        List<QuestionsModel> listOfQuestions;
//        TestCodeModel testCodeModel;
//
//        try {
//            testCodeModel = getTestCodeModelByTestCode(quizcode);
//        }
//        catch (Exception e) {
//            //TODO
//        }
//        if(testCodeModel == null ) {
//            //TODO
//        }
//
//        try {
//            testsModel = getTestModelByTestId(testCodeModel.getFKtest().getId());
//            usersModel = getUserModel();
//            listOfQuestions = getAllQuestionFromQuizId(testsModel.getId());
//        }
//        catch (Exception e) {
//            //TODO
//        }
//
//        boolean isBackPossible = checkIfQuizIsBackPossible(testsModel);
//
//        if(isBackPossible) {
//
//        }
//    }

    // TODO change enum respons
    // Zamieniono na @RequestParam - ponieważ @RequestBody w GET jest niezgodne z
    // RFC (https://www.ietf.org/rfc/rfc2616.txt) i Angular nie pozwala na takie działanie
    @GetMapping("quiz/quizquestion/{quizId}/{testCode}/{questionNumber}")
    public QuestionDto getQuizQuestion(@PathVariable String quizId,
                                       @PathVariable String testCode,
                                       @PathVariable String questionNumber,
                                       HttpServletResponse response){
        //inicjalizacja niezbednych zmiennych modeli

        UsersModel usersModel;
        TestsModel testsModel;
        List<QuestionsModel> listOfQuestions;
        QuizQuestionCommandDto quizQuestionCommandDto = new QuizQuestionCommandDto(
                Long.parseLong(quizId), Long.parseLong(questionNumber), testCode
        );

        //wprowadzenie zmiennych
        try {
            usersModel = getUserModel();
            testsModel = getTestModelByTestId(quizQuestionCommandDto.getQuizid());
            listOfQuestions = getAllQuestionFromQuizId(quizQuestionCommandDto.getQuizid());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }

        //jesli ktoras jest nullem to nie znaleziono testu
        if(testsModel==null || usersModel==null || listOfQuestions == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.TEST_NOT_FOUND));
        }

        boolean isBackPossible = checkIfQuizIsBackPossible(testsModel);
        boolean isOpenForEveryone = checkIfQuizIsOpenForEveryone(testsModel);

        //nieaktywny test? wywalamy blad
        if(!checkIfTestIsActive(testsModel)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.INACTIVE_QUIZ));
        }

        /////////////////////////////////////////////
        //TEST Z MOZLIWOSCIA POWROTU I DLA WSZYSTKICH
        /////////////////////////////////////////////
        if(isBackPossible && isOpenForEveryone){
            QuestionDto questionDto = getBackPossibleQuizQuestionWithoutCode(quizQuestionCommandDto, testsModel, listOfQuestions);
            response.setStatus(ResponseEnumOperations.getResponseStatusFromModelResponseCode(questionDto.getResponseCode()));
            return questionDto;
        }

        /////////////////////////////////////////////////
        //TEST Z MOZLIWOSCIA POWROTU I NIE DLA WSZYSTKICH
        /////////////////////////////////////////////////
        else if(isBackPossible && !isOpenForEveryone) {
            QuestionDto questionDto = getBackPossibleQuizQuestionWithCode(quizQuestionCommandDto, testsModel, usersModel, listOfQuestions);
            response.setStatus(ResponseEnumOperations.getResponseStatusFromModelResponseCode(questionDto.getResponseCode()));
            return questionDto;
        }

        ////////////////////////////////////////////////
        //TEST BEZ MOZLIWOSCIA POWROTU I  DLA WSZYSTKICH
        ////////////////////////////////////////////////
        else if (!isBackPossible && isOpenForEveryone) {
            QuestionDto questionDto = getBackImpossibleQuizQuestionWithoutCode(quizQuestionCommandDto, testsModel, usersModel, listOfQuestions);
            response.setStatus(ResponseEnumOperations.getResponseStatusFromModelResponseCode(questionDto.getResponseCode()));
            return questionDto;
        }


        ///////////////////////////////////////////////////
        //TEST BEZ MOZLIWOSCI POWROTU I  NIE DLA WSZYSTKICH
        ///////////////////////////////////////////////////
        else {
            QuestionDto questionDto = getBackImpossibleQuizQuestionWithCode(quizQuestionCommandDto, testsModel, usersModel, listOfQuestions);
            response.setStatus(ResponseEnumOperations.getResponseStatusFromModelResponseCode(questionDto.getResponseCode()));
            return questionDto;
        }
    }

    private boolean checkIfUserAlreadySolvedThisQuiz(TestParticipantModel testParticipantModel){
        return testParticipantModel.getQuestionNumber()!=1;
    }

    private boolean checkIfUserCanSolveThisQuiz(UsersModel usersModel, long testCodeModelUserId) {
        return usersModel.getId() == testCodeModelUserId;
    }

    private QuestionDto getBackPossibleQuizQuestionWithoutCode(QuizQuestionCommandDto quizQuestionCommandDto,
                                                               TestsModel testsModel ,
                                                               List<QuestionsModel> listOfQuestions) {
        if(!checkIfQuestionExistsInQuiz(listOfQuestions, quizQuestionCommandDto.getQuestionnumber())) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }

        QuestionsModel questionsModel = getExpectedQuestionModel(listOfQuestions, quizQuestionCommandDto.getQuestionnumber());

        if(questionsModel==null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }
        List<AnswersModel> listOfAnswersModel;

        try {
            listOfAnswersModel = getAnswersByQuestionId(questionsModel.getId());
        }
        catch (Exception e) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }
        if(listOfAnswersModel == null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }
        return getQuestionDtoModel(questionsModel, listOfAnswersModel);
    }

    private QuestionDto getBackPossibleQuizQuestionWithCode(QuizQuestionCommandDto quizQuestionCommandDto,
                                                            TestsModel testsModel,
                                                            UsersModel usersModel,
                                                            List<QuestionsModel> listOfQuestions) {
        if(quizQuestionCommandDto.getTestCode() == null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.BAD_REQUEST));
        }

        TestParticipantModel testParticipantModel;

        try {
            testParticipantModel = getTestCodeModelByTestAndUser(testsModel, usersModel);
        }
        catch (Exception e) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }
        if(testParticipantModel ==null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.AUTHORIZATION_FAILED));
        }

        if(!checkIfTestCodeIsCorrect(testParticipantModel, quizQuestionCommandDto.getTestCode())) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.AUTHORIZATION_FAILED));
        }

        if(!checkIfQuestionExistsInQuiz(listOfQuestions, quizQuestionCommandDto.getQuestionnumber())) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }

        QuestionsModel questionsModel = getExpectedQuestionModel(listOfQuestions, quizQuestionCommandDto.getQuestionnumber());

        if(questionsModel==null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }
        List<AnswersModel> listOfAnswersModel;

        try {
            listOfAnswersModel = getAnswersByQuestionId(questionsModel.getId());
        }
        catch (Exception e) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }
        if(listOfAnswersModel == null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }
        return getQuestionDtoModel(questionsModel, listOfAnswersModel);
    }

    private QuestionDto getBackImpossibleQuizQuestionWithoutCode(QuizQuestionCommandDto quizQuestionCommandDto,
                                                                 TestsModel testsModel,
                                                                 UsersModel usersModel,
                                                                 List<QuestionsModel> listOfQuestions) {

        TestParticipantModel testParticipantModel;

        try {
            testParticipantModel = getTestCodeModelByTestAndUser(testsModel, usersModel);
        }
        catch (Exception e) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }
        if(testParticipantModel == null) {
            try{
                testParticipantModel = saveAndGetNewTestCodeModel(testsModel, usersModel);
            } catch (Exception e) {
                return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
            }
        }
        long questionNumber;
        try {
            questionNumber = getNextQuestionNumber(testParticipantModel);
        }
         catch (Exception e) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
         }

        if(!checkIfQuestionExistsInQuiz(listOfQuestions, questionNumber)) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUIZ_AREADY_SOLVED));
        }

        QuestionsModel questionsModel = getExpectedQuestionModel(listOfQuestions, questionNumber);

        if(questionsModel==null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }

        List<AnswersModel> listOfAnswersModel;

        try {
            listOfAnswersModel = getAnswersByQuestionId(questionsModel.getId());
        }
        catch (Exception e) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }
        if(listOfAnswersModel == null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }
        return getQuestionDtoModel(questionsModel, listOfAnswersModel);
    }

    private QuestionDto getBackImpossibleQuizQuestionWithCode(QuizQuestionCommandDto quizQuestionCommandDto,
                                                              TestsModel testsModel,
                                                              UsersModel usersModel,
                                                              List<QuestionsModel> listOfQuestions) {

        if(quizQuestionCommandDto.getTestCode() == null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.BAD_REQUEST));
        }

        TestParticipantModel testParticipantModel;

        try {
            testParticipantModel = getTestCodeModelByTestAndUser(testsModel, usersModel);
        }
        catch (Exception e) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }
        if(testParticipantModel ==null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.AUTHORIZATION_FAILED));
        }
        if(!checkIfTestCodeIsCorrect(testParticipantModel, quizQuestionCommandDto.getTestCode())) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.AUTHORIZATION_FAILED));
        }

        long questionNumber;
        try {
            questionNumber = getNextQuestionNumber(testParticipantModel);
        }
        catch (Exception e) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }

        if(!checkIfQuestionExistsInQuiz(listOfQuestions, questionNumber)) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUIZ_AREADY_SOLVED));
        }

        QuestionsModel questionsModel = getExpectedQuestionModel(listOfQuestions, questionNumber);

        if(questionsModel==null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }

        List<AnswersModel> listOfAnswersModel;

        try {
            listOfAnswersModel = getAnswersByQuestionId(questionsModel.getId());
        }
        catch (Exception e) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SERVER_ERROR));
        }
        if(listOfAnswersModel == null) {
            return new QuestionDto(ResponseEnumOperations.getResponseStatusInt(ResponseEnum.QUESTION_NOT_FOUND));
        }
        return getQuestionDtoModel(questionsModel, listOfAnswersModel);
    }


    private TestParticipantModel getTestCodeModelByTestAndUser(TestsModel testsModel, UsersModel usersModel) {
        return testCodeRepository.findByFKtestCodeuserIdAndFKtestCodetestId(usersModel.getId(), testsModel.getId());
    }

    private TestParticipantModel saveAndGetNewTestCodeModel(TestsModel  testsModel, UsersModel usersModel) {
        TestParticipantModel testParticipantModel = new TestParticipantModel(testsModel, usersModel);;
        testCodeRepository.save(testParticipantModel);
        return testParticipantModel;
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

    private long getNextQuestionNumber(TestParticipantModel testParticipantModel){
        long questionNumber = testParticipantModel.getQuestionNumber();
        testParticipantModel.setQuestionNumberPlusOne();
        testCodeRepository.save(testParticipantModel);
        return questionNumber;
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

    private boolean checkIfQuizIsBackPossible(TestsModel testsModel) {
        return testsModel.isPossibleToBack();
    }
    private boolean checkIfTestCodeIsCorrect(TestParticipantModel testParticipantModel, String testCode) {
        return testParticipantModel.getCode().equals(testCode);
    }
    private boolean checkIfTestIsActive(TestsModel testsModel ) {
        return testsModel.isActive();
    }
    private boolean checkIfQuizIsOpenForEveryone(TestsModel testsModel){
        return testsModel.isOpenForEveryone();
    }

    private QuestionDto getQuestionDtoModel(QuestionsModel questionsModel, List<AnswersModel> answersModelList){
        return new QuestionDto(questionsModel, answersModelList, ResponseEnumOperations.getResponseStatusInt(ResponseEnum.SUCCESS));
    }

    private TestParticipantModel getTestCodeModelByTestCode(String testCode) {
        return testCodeRepository.findByCode(testCode);
    }
}
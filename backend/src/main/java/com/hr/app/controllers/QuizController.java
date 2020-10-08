package com.hr.app.controllers;

import com.hr.app.models.api_helpers.QuizQuestionCommandDto;
import com.hr.app.models.api_helpers.QuestionJsonModel;
import com.hr.app.models.api_helpers.QuizModel;
import com.hr.app.models.api_helpers.ResponseTransfer;
import com.hr.app.models.database.*;
import com.hr.app.models.dto.AnswerDto;
import com.hr.app.models.dto.CompleteQuizDto;
import com.hr.app.models.dto.QuestionDto;
import com.hr.app.repositories.*;
import org.aspectj.weaver.ast.Test;
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
    private ITestCodeRepository testCodeRepository;

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

    //TODO Poki co nie wiem jak xD
//    @GetMapping("quiz/{testcode}")
//    public TestsModel getQuiz(@PathVariable String testcode){
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        try {
//            UsersModel user = usersRepository.findByLogin(name);
//            TestCodeModel testCode = testCodeRepository.findByCode(testcode);
//            if(testCode.getFKuser().getId()==user.getId()){
//                return testCode.getFKtest();
//            }
//        } catch (Exception e) {
//            return null;
//        }
//
//    }

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

    //TODO make it look better and response codes
    @GetMapping("quiz/quizquestion")
    public QuestionDto getQuizQuestion(@RequestBody QuizQuestionCommandDto quizQuestionCommandDto, HttpServletResponse response){
        //inicjalizacja niezbednych zmiennych modeli
        UsersModel usersModel = getUsersModel();
        TestsModel testsModel = getTest(quizQuestionCommandDto.getQuizid());

        if(testsModel ==null || usersModel == null) {
            response.setStatus(403);
            return null;
        }

        boolean isBackPossible = checkIfQuizIsBackPossible(testsModel);
        boolean isOpenForEveryone = checkIfQuizIsOpenForEveryone(testsModel);

        //jesli wyslany jest kod, to sprawdzenie czy istnieje w bazie. jesli nie ma w bazie to juz na starcie wywalamy brak dostepu

        if(checkIfTestIsActive(testsModel)) {

            if(isBackPossible && isOpenForEveryone){
                return getBackPossibleQuizQuestionWithoutCode(quizQuestionCommandDto, testsModel);
            }
            else if(isBackPossible && !isOpenForEveryone) {
                TestCodeModel testCodeModel;
                if(quizQuestionCommandDto.getTestCode()!= null) {
                    testCodeModel = getTestCodeModel(usersModel.getId(), quizQuestionCommandDto.getQuizid());
                    if(testCodeModel==null) {
                        response.setStatus(403);
                        return null;
                    }
                    else {
                        return getBackPossibleQuizQuestionWithCode(quizQuestionCommandDto, testsModel, testCodeModel);
                    }
                }
                return null;
            }
            else if (!isBackPossible && isOpenForEveryone) {
                TestCodeModel testCodeModel;
                if(checkIfTestCodeForUserAlreadyExists(testsModel,usersModel)){
                    testCodeModel = getTestCodeModel(usersModel.getId(), testsModel.getId());
                }
                else {
                    testCodeModel= saveNewTestCodeToDataBase(testsModel, usersModel);
                }
                if(testCodeModel==null) {
                    return null;
                }
                return getBackImpossibleQuizQuestionWithoutCode(quizQuestionCommandDto, testCodeModel);
            }
            else {
                TestCodeModel testCodeModel;
                if(quizQuestionCommandDto.getTestCode()!= null) {
                    testCodeModel = getTestCodeModel(usersModel.getId(), quizQuestionCommandDto.getQuizid());
                    if(testCodeModel==null) {
                        response.setStatus(403);
                        return null;
                    }
                    else {
                        return getBackImpossibleQuizQuestionWithCode(quizQuestionCommandDto, testsModel, testCodeModel);
                    }
                }
                return null;
            }

//            if(checkIfQuizIsBackPossible(testsModel)){
//
//                if(checkIfQuizIsOpenForEveryone(testsModel)){
//                    return getBackPossibleQuizQuestionWithoutCode(quizQuestionCommandDto, testsModel);
//                }
//                else {
//                    TestCodeModel testCodeModel;
//                    if(quizQuestionCommandDto.getTestCode()!= null) {
//                        testCodeModel = getTestCodeModel(usersModel.getId(), quizQuestionCommandDto.getQuizid());
//                        if(testCodeModel==null) {
//                            response.setStatus(403);
//                            return null;
//                        }
//                        else {
//                            return getBackPossibleQuizQuestionWithCode(quizQuestionCommandDto, testsModel, testCodeModel);
//                        }
//                    }
//                    return null;
//                }
//
//            }
//            else {
//                if(checkIfQuizIsOpenForEveryone(testsModel)){
//                    TestCodeModel testCodeModel;
//                    if(checkIfTestCodeForUserAlreadyExists(testsModel,usersModel)){
//                        testCodeModel = getTestCodeModel(usersModel.getId(), testsModel.getId());
//                    }
//                    else {
//                        testCodeModel= saveNewTestCodeToDataBase(testsModel, usersModel);
//                    }
//                    if(testCodeModel==null) {
//                        return null;
//                    }
//                    return getBackImpossibleQuizQuestionWithoutCode(quizQuestionCommandDto, testCodeModel);
//                }
//                else {
//                    TestCodeModel testCodeModel;
//                    if(quizQuestionCommandDto.getTestCode()!= null) {
//                        testCodeModel = getTestCodeModel(usersModel.getId(), quizQuestionCommandDto.getQuizid());
//                        if(testCodeModel==null) {
//                            response.setStatus(403);
//                            return null;
//                        }
//                        else {
//                            return getBackImpossibleQuizQuestionWithCode(quizQuestionCommandDto, testsModel, testCodeModel);
//                        }
//                    }
//                    return null;
//                }
//            }
        }

        //test nieaktywny czyli brak dostepu. wyrzucamy 403
        else {
            response.setStatus(403);
            return null;
        }
    }

    private boolean checkIfTestCodeForUserAlreadyExists(TestsModel testsModel, UsersModel usersModel) {
        try {
            TestCodeModel testCodeModel = testCodeRepository.findByFKtestCodeuserIdAndFKtestCodetestId(usersModel.getId(), testsModel.getId());
            return testCodeModel != null;
        }
        catch (Exception e) {
            return false;
        }
    }

    private TestCodeModel saveNewTestCodeToDataBase(TestsModel  testsModel, UsersModel usersModel){
        TestCodeModel testCodeModel = new TestCodeModel(testsModel, usersModel);
        try
        {
            testCodeRepository.save(testCodeModel);
            return testCodeModel;
        }
        catch (Exception e) {
            return null;
        }
    }

    private UsersModel getUsersModel(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return usersRepository.findByLogin(name);
        }
        catch (Exception e) {
            return null;
        }
    }
    private TestsModel getTest(long testId) {
        try {
            return testsRepository.findById(testId);
        }
        catch (Exception e ) {
            return null;
        }

    }
    private List<AnswersModel> getAnswersForQuestion(long questionId){
        try {
            return  answersRepository.findAllByFKanswerQuestionId(questionId);
        }
        catch (Exception e) {
            return null;
        }
    }
    private List<QuestionsModel> getAllQuizQuestions(long quizId) {
        try {
            return questionsRepository.findAllByFKquestionTestId(quizId);
        }
        catch (Exception e) {
            return null;
        }
    }
    private QuestionsModel getExpectedQuestion(long quizId,long questionNumber) {
        List<QuestionsModel> listofQuestions = getAllQuizQuestions(quizId);
        if(listofQuestions==null) {
            return null;
        }
        else if (listofQuestions.size() < questionNumber) {
            return null;
        }
        else {
            return getAllQuizQuestions(quizId).get((int) questionNumber-1);
        }
    }
    private TestCodeModel getTestCodeModel (long userId, long testId){
        try{
            return testCodeRepository.findByFKtestCodeuserIdAndFKtestCodetestId(userId, testId);
        }
        catch (Exception e){
            return null;
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

    private boolean checkIfQuizIsBackPossible(TestsModel testsModel) {
        return testsModel.isPossibleToBack();
    }

    private boolean checkIfTestCodeIsCorrect(TestCodeModel testCodeModel, String testCode) {
        return testCodeModel.getCode().equals(testCode);
    }

    private boolean checkIfTestIsActive(TestsModel testsModel ) {
        return testsModel.isActive();
    }

    //TODO
    private boolean checkIfQuizIsOpenForEveryone(TestsModel testsModel){
        return testsModel.isOpenForEveryone();
    }

    private Long getNextQuestionNumber(TestCodeModel testCodeModel){
        long questionNumber = testCodeModel.getQuestionNumber();
        testCodeModel.setQuestionNumberPlusOne();
        try {
            testCodeRepository.save(testCodeModel);
            return questionNumber;
        }
        catch (Exception e) {
            return null;
        }
    }

    private QuestionDto getBackPossibleQuizQuestionWithoutCode(QuizQuestionCommandDto quizQuestionCommandDto, TestsModel testsModel) {
        QuestionsModel questionsModel = getExpectedQuestion(testsModel.getId(), quizQuestionCommandDto.getQuestionnumber());
        if(questionsModel==null) {
            return null;
        }
        else {
            return getQuestionDtoModel(questionsModel);
        }
    }

    private QuestionDto getBackPossibleQuizQuestionWithCode(QuizQuestionCommandDto quizQuestionCommandDto, TestsModel testsModel, TestCodeModel testCodeModel) {
        if(checkIfTestCodeIsCorrect(testCodeModel, quizQuestionCommandDto.getTestCode())){
            QuestionsModel questionsModel = getExpectedQuestion(testsModel.getId(), quizQuestionCommandDto.getQuestionnumber());
            if(questionsModel==null) {
                return null;
            }
            else {
                return getQuestionDtoModel(questionsModel);
            }
        }
        else {
            return null;
        }
    }

    private QuestionDto getBackImpossibleQuizQuestionWithoutCode(QuizQuestionCommandDto quizQuestionCommandDto, TestCodeModel testCodeModel) {
        Long nextQuestionNumber = getNextQuestionNumber(testCodeModel);
        if(nextQuestionNumber== null) {
            return null;
        }
        QuestionsModel questionsModel = getExpectedQuestion(quizQuestionCommandDto.getQuizid(), nextQuestionNumber);
        if(questionsModel==null) {
            return null;
        }
        else {
            return getQuestionDtoModel(questionsModel);
        }
    }

    private QuestionDto getBackImpossibleQuizQuestionWithCode(QuizQuestionCommandDto quizQuestionCommandDto, TestsModel testsModel, TestCodeModel testCodeModel) {
        if(checkIfTestCodeIsCorrect(testCodeModel, quizQuestionCommandDto.getTestCode())){
            Long nextQuestionNumber = getNextQuestionNumber(testCodeModel);
            if(nextQuestionNumber== null) {
                return null;
            }
            QuestionsModel questionsModel = getExpectedQuestion(quizQuestionCommandDto.getQuizid(), nextQuestionNumber);
            if(questionsModel==null) {
                return null;
            }
            else {
                return getQuestionDtoModel(questionsModel);
            }
        }
        else {
            return null;
        }
    }

    private QuestionDto getQuestionDtoModel(QuestionsModel questionsModel){
        List<AnswersModel> answersModelList = getAnswersForQuestion(questionsModel.getId());
        if(answersModelList == null) {
            return null;
        }
        else {
            return new QuestionDto(questionsModel, answersModelList);
        }

    }
}
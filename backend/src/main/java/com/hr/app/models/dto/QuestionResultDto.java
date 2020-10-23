package com.hr.app.models.dto;

import com.hr.app.models.database.AnswersModel;
import com.hr.app.models.database.QuestionsModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionResultDto {

    private long id;
    private String text;
    private byte[] image;

    private List<AnswerResultDto> answers;

    private int responseCode;

    public QuestionResultDto(QuestionsModel questionsModel) {
        this.id = questionsModel.getId();
        this.text = questionsModel.getText();
        this.image=questionsModel.getImage();
    }

    public QuestionResultDto(QuestionsModel questionsModel, List<AnswersModel> listOfAnswersModel , int responseCode){
        this.id = questionsModel.getId();
        this.text = questionsModel.getText();
        this.image=questionsModel.getImage();

        ArrayList<AnswerResultDto> listOfAnswersDto = new ArrayList<>();
        for (AnswersModel answer: listOfAnswersModel) {
            listOfAnswersDto.add(new AnswerResultDto(answer));
        }
        this.answers = listOfAnswersDto;
        this.responseCode = responseCode;
    }

    public QuestionResultDto(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public byte[] getImage() {
        return image;
    }

    public List<AnswerResultDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerResultDto> answers) {
        this.answers = answers;
    }
}

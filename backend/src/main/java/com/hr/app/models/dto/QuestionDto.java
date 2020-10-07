package com.hr.app.models.dto;

import com.hr.app.models.database.AnswersModel;
import com.hr.app.models.database.QuestionsModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionDto {

    private long id;
    private String text;
    private byte[] image;

    private List<AnswerDto> answers;

    public QuestionDto(QuestionsModel questionsModel) {
        this.id = questionsModel.getId();
        this.text = questionsModel.getText();
        this.image=questionsModel.getImage();
    }

    public QuestionDto(QuestionsModel questionsModel, List<AnswersModel> listOfAnswersModel){
        this.id = questionsModel.getId();
        this.text = questionsModel.getText();
        this.image=questionsModel.getImage();

        ArrayList<AnswerDto> listOfAnswersDto = new ArrayList<>();
        for (AnswersModel answer: listOfAnswersModel) {
            listOfAnswersDto.add(new AnswerDto(answer));
        }
        this.answers = listOfAnswersDto;
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

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }
}

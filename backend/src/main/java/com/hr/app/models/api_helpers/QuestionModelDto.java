package com.hr.app.models.api_helpers;

public class QuestionModelDto {

    private String text;

    private byte[] image;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public QuestionModelDto(String text, byte[] image) {
        this.text = text;
        this.image = image;
    }
}

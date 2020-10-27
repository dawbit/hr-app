package com.hr.app.models.dto;

public class ResponseTransfer {
    private String message;
    private String exception;

    public String getMessage() {
        return message;
    }

    public String getException() {
        return exception;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public ResponseTransfer(String message) {
        this.message = message;
    }

    public ResponseTransfer(String message, String exception) {
        this.message = message;
        this.exception = exception;
    }

}

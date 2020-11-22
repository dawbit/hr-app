package com.hr.app.ftp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class MyFileNotFoundException extends RuntimeException {
    MyFileNotFoundException(String message) {
        super(message);
    }

    MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
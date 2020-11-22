package com.hr.app.ftp;

class FileStorageException extends RuntimeException {
    FileStorageException(String message) {
        super(message);
    }

    FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
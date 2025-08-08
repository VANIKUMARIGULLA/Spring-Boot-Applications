package com.example.examination.service.exception;


public class ExaminationNotFoundException extends RuntimeException {
    public ExaminationNotFoundException(String message) {
        super(message);
    }
}


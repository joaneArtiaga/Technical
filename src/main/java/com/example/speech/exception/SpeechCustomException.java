package com.example.speech.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class SpeechCustomException extends RuntimeException{

    private int code;

    public SpeechCustomException(String message, int code) {
        super(message);
        this.code = code;
    }
}

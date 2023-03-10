package com.bebetter.task.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BeBetterRuntimeException extends RuntimeException{

    private BeBetterErrorResponse beBetterErrorResponse;

    public BeBetterRuntimeException(String message, ErrorCode errorCode){
        super(message);
        this.beBetterErrorResponse = new BeBetterErrorResponse(errorCode, errorCode.getDescription(),
                LocalDateTime.now());
    }
}

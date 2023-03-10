package com.bebetter.task.exception;

import lombok.Getter;

@Getter
public class BeBetterBadRequestException extends BeBetterRuntimeException{

    public BeBetterBadRequestException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }
}

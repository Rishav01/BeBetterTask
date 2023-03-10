package com.bebetter.task.exception;

import lombok.Getter;

@Getter
public class BeBetterNotFoundException extends BeBetterRuntimeException{

    public BeBetterNotFoundException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }
}

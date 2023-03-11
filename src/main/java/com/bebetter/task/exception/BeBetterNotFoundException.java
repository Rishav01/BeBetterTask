package com.bebetter.task.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class BeBetterNotFoundException extends BeBetterRuntimeException{

    public BeBetterNotFoundException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }
}

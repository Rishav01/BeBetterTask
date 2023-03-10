package com.bebetter.task.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class BeBetterErrorResponse {
    private ErrorCode errorCode;
    private String exceptionMessage;
    private LocalDateTime timeStamp;
}

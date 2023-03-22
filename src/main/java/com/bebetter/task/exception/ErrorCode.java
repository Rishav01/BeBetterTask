package com.bebetter.task.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("BB102", "User not found"),
    TASK_NOT_FOUND("BB101", "Task not found"),
    INCORRECT_PASSWORD("BB103", "Incorrect Password Provided"),
    TASK_CREATION_LIMIT_EXHAUSTED("BB104", "Task Creation limit has been exhausted for a day"),
    VALIDATION_FAILED_RB("BB105", "Validation Failed for Request Body Attribute"),
    VALIDATION_FAILED_RP("BB105", "Validation Failed for Request Param or Path Variable"),
    INTERNAL_SERVER_ERROR("BB106", "Internal Server Error");

    private final String description;
    private final String code;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

}

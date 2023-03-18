package com.bebetter.task.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BeBetterBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BeBetterErrorResponse> handleBadRequestException(BeBetterBadRequestException exception){
        return new ResponseEntity(exception.getBeBetterErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BeBetterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BeBetterErrorResponse> handleNotFoundException(BeBetterNotFoundException exception){
        return new ResponseEntity(exception.getBeBetterErrorResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BeBetterErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException
                                                                                       methodArgumentNotValidException){
        List<FieldError> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        return new ResponseEntity<>(BeBetterErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_FAILED_RB)
                .exceptionMessage(getAllErrorFormatted(errors))
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BeBetterErrorResponse> handleConstraintViolationException(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> voilations = exception.getConstraintViolations();
        String message = getConcatenatedErrorMessage(voilations);
        return new ResponseEntity<>(BeBetterErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_FAILED_RP)
                .exceptionMessage(message)
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BeBetterErrorResponse> handleInternalServerException(RuntimeException exception){
        log.error("INTERNAL_SERVER_ERROR occurred");
        exception.printStackTrace();
        return new ResponseEntity(BeBetterErrorResponse.builder()
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .exceptionMessage(ErrorCode.INTERNAL_SERVER_ERROR.getDescription())
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getAllErrorFormatted(List<FieldError> error){
        return error.stream().map(x -> x.getField().concat(": ").concat(x.getDefaultMessage())).collect(Collectors.toList())
                .stream().reduce((y1, y2) -> y1.concat(" and ").concat(y2)).get();
    }

    private String getConcatenatedErrorMessage(Set<ConstraintViolation<?>> voilations){
        return "[".concat(voilations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(" and "))).concat("]");
    }
}

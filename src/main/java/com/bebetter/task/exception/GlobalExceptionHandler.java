package com.bebetter.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BeBetterBadRequestException.class)
    public ResponseEntity<BeBetterErrorResponse> handleBadRequestException(BeBetterBadRequestException exception){
        return new ResponseEntity(exception.getBeBetterErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BeBetterNotFoundException.class)
    public ResponseEntity<BeBetterErrorResponse> handleNotFoundException(BeBetterNotFoundException exception){
        return new ResponseEntity(exception.getBeBetterErrorResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BeBetterErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException
                                                                                       methodArgumentNotValidException){
        List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(BeBetterErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_FAILED_RB)
                .exceptionMessage(errors.size()>1 ? getAllErrorFormatted(errors) : errors.get(0))
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
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
    public ResponseEntity<BeBetterErrorResponse> handleInternalServerException(RuntimeException exception){
        return new ResponseEntity(BeBetterErrorResponse.builder()
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .exceptionMessage(ErrorCode.INTERNAL_SERVER_ERROR.getDescription())
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getAllErrorFormatted(List<String> error){
        return "[".concat(error.stream().reduce((x1, x2) -> x1.concat(" and ").concat(x2)).get()).concat("]");
    }

    private String getConcatenatedErrorMessage(Set<ConstraintViolation<?>> voilations){
        return "[".concat(voilations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(" and "))).concat("]");
    }
}

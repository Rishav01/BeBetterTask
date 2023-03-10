package com.bebetter.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
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
        List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(BeBetterErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_FAILED)
                .exceptionMessage(errors.size()>1 ? getAllErrorFormatted(errors) : errors.get(0))
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleInternalServerException(RuntimeException exception){
        return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getAllErrorFormatted(List<String> error){
        return "[".concat(error.stream().reduce((x1, x2) -> x1.concat(" and ").concat(x2)).get()).concat("]");
    }
}

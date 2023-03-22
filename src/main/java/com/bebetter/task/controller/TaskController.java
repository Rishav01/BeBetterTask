package com.bebetter.task.controller;

import com.bebetter.task.dto.CreateTaskDto;
import com.bebetter.task.entity.Task;
import com.bebetter.task.enums.Status;
import com.bebetter.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/task")
@Slf4j
@Validated
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/set-tasks")
    public ResponseEntity<List<Task>> setTasks(@Valid @RequestBody CreateTaskDto setDiaryDto){
        log.info("Request received for setting task for user {}", setDiaryDto.getEmailId());
        return new ResponseEntity<>(taskService.setTasks(setDiaryDto), HttpStatus.CREATED);
    }

    @GetMapping("/get-tasks")
    public ResponseEntity<List<Task>> getDiaryTask(@RequestParam @Email String emailId,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tillDate,
                                                   @RequestParam(required = false) Status status){
        log.info("Request received for getting the diary for user {} and for the duration {} to {}",
                emailId, fromDate, tillDate);
        return new ResponseEntity<>(taskService.getTasks(emailId, fromDate, tillDate, status), HttpStatus.OK);
    }
}


//TODO: To check if camelcasing is alright for urls
//TODO: is it alright to send string in rest api response or Json is better?
//TODO: Need to enable the authentication before sending request to service
//TODO: Need to check if there should be an access modifier
//TODO: How to print the request DTO in log. Also, is it alright to do?
//TODO: Check if the logging properly covers a flow or not
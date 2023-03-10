package com.bebetter.task.controller;

import com.bebetter.task.dto.TaskCompleteRequestDto;
import com.bebetter.task.dto.TaskDto;
import com.bebetter.task.entity.Task;
import com.bebetter.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskDto taskDto){
        log.info("Request for creating a Task for EmailId: ", taskDto.getUserEmailId());
        String responseMessage = taskService.createTask(taskDto);
        return responseMessage.contains("Task Created with Id") ?
            new ResponseEntity(responseMessage, HttpStatus.CREATED) : new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-all")
    public List<Task> getAllTask(@RequestParam(required=false) String userEmailId){
        log.info("Request for getting all the tasks received");
        return taskService.getAllTask(userEmailId);
    }

    @PostMapping("/complete")
    public String completeTask(@RequestBody TaskCompleteRequestDto taskCompleteRequestDto){
        log.info("Request received for task completion with id {}", taskCompleteRequestDto.getTaskId());
        return taskService.completeTask(taskCompleteRequestDto);
    }

}
//TODO: To check if camelcasing is alright for urls
//TODO: is it alright to send string in rest api response or Json is better?
//TODO: Need to enable the authentication before sending request to service
//TODO: Need to check if there should be an access modifier
//TODO: How to print the request DTO in log. Also, is it alright to do?
//TODO: Check if the logging properly covers a flow or not
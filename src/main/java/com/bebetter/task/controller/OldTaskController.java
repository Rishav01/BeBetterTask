package com.bebetter.task.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@Slf4j
public class OldTaskController {
/*

    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskDto taskDto){
        log.info("Request for creating a Task for EmailId: ", taskDto.getUserEmailId());
        String responseMessage = taskService.createTask(taskDto);
        return responseMessage.contains("Task Created with Id") ?
            new ResponseEntity(responseMessage, HttpStatus.CREATED) : new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get")
    public List<Task> getAllTask(@RequestParam(required=false) String userEmailId){
        log.info("Request for getting all the tasks received");
        return taskService.getAllTask(userEmailId);
    }

    @PostMapping("/complete")
    public String completeTask(@RequestBody TaskCompleteRequestDto taskCompleteRequestDto){
        log.info("Request received for task completion with id {}", taskCompleteRequestDto.getTaskId());
        return taskService.completeTask(taskCompleteRequestDto);
    }
*/

}
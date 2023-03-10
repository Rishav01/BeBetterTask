package com.bebetter.task.service;

import com.bebetter.task.dto.TaskCompleteRequestDto;
import com.bebetter.task.dto.TaskDto;
import com.bebetter.task.dto.user.UserPlanResponseDto;
import com.bebetter.task.entity.Task;
import com.bebetter.task.repository.TaskRepository;
import com.bebetter.task.service.user.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PlanService planService;

    public String createTask(TaskDto taskDto){
        log.info("Starting to create Task");
        UserPlanResponseDto userPlanResponseDto = planService.getUserPlanByEmailId(taskDto.getUserEmailId());
        List<Task> tasks = taskRepository.findByUserEmailIdAndForDate(taskDto.getUserEmailId(), taskDto.getForDate());
        if(userPlanResponseDto.getMaxTaskAllowed() == tasks.size()){
            return "Task creation limit has exhausted";
        }
        Task taskEntity = convertDtoToEntity(taskDto);
        taskRepository.save(taskEntity);
        log.info("Task created and sending the response back");
        return "Task Created with Id: {"+taskEntity.getTaskId()+"}";
    }

    public List<Task> getAllTask(String userEmailId){
        log.info("Starting to get all the tasks");
        return userEmailId==null ? taskRepository.findAll() : taskRepository.findByUserEmailId(userEmailId);

    }

    public String completeTask(TaskCompleteRequestDto taskCompleteRequestDto){
        log.info("starting to complete the task with id {}", taskCompleteRequestDto.getTaskId());
        List<Task> task = taskRepository.findByTaskId(taskCompleteRequestDto.getTaskId());
        Task currentTask = task.get(0);
        currentTask.setIsTaskCompleted(true);
        currentTask.setTaskCompletedTime(LocalDateTime.now());
        currentTask.setTaskEvaluationPoints(taskCompleteRequestDto.getTaskEvaluationPoints());
        taskRepository.save(task.get(0));
        log.info("Done with completing the task with id {}", taskCompleteRequestDto.getTaskId());
        return "task with id "+ taskCompleteRequestDto.getTaskId() +" is marked completed";
    }

    private Task convertDtoToEntity(TaskDto taskDto){
        return Task.builder()
                    .taskName(taskDto.getTaskName())
                    .taskAssignedPoints(taskDto.getTaskAssignedPoints())
                    .forDate(taskDto.getForDate())
                    .taskAddedTime(LocalDateTime.now())
                    .isTaskCompleted(false)
                    .userEmailId(taskDto.getUserEmailId())
                    .build();

    }
}

//TODO is it alright to have dto and entity object same?
//TODO: Find the right way to update the Entity. Is update object by setter and then saving the same object correct?
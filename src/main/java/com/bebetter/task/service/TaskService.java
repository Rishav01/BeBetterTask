package com.bebetter.task.service;

import com.bebetter.task.dto.CreateTaskDto;
import com.bebetter.task.dto.TaskDto;
import com.bebetter.task.dto.user.UserPlanResponseDto;
import com.bebetter.task.entity.Task;
import com.bebetter.task.enums.Status;
import com.bebetter.task.exception.BeBetterBadRequestException;
import com.bebetter.task.exception.BeBetterNotFoundException;
import com.bebetter.task.exception.ErrorCode;
import com.bebetter.task.repository.TaskRepository;
import com.bebetter.task.service.user.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PlanService planService;

    public List<Task> setTasks(CreateTaskDto createTaskDto){
        log.info("Request received for creating task");
        UserPlanResponseDto userPlanResponseDto = planService.getUserPlanByEmailId(createTaskDto.getEmailId());
        List<Task> existingTask = taskRepository.findByEmailIdAndForDate(createTaskDto.getEmailId(), createTaskDto.getForDate());
        if(userPlanResponseDto.getMaxTaskAllowed() < (existingTask.size()+createTaskDto.getTaskDtoList().size())){
            throw new BeBetterBadRequestException(ErrorCode.TASK_CREATION_LIMIT_EXHAUSTED.getDescription(), ErrorCode.TASK_CREATION_LIMIT_EXHAUSTED);
        }
        List<Task> taskList = convertTaskDtoToEntity(createTaskDto);
        taskRepository.saveAll(taskList);
        return taskList;
    }

    public List<Task> getTasks(String emailId, LocalDate fromDate,
                               LocalDate tillDate, Status status){
        log.info("Starting to get the Tasks for a date");
        List<Task> taskResponseDto = status==null ? taskRepository.getTasksByUserAndDuration(emailId, fromDate, tillDate)
                : taskRepository.getTasksByUserDurationAndStatus(emailId, fromDate, tillDate, status);
        if(taskResponseDto.size()==0){
            log.error("Exception has occurred for user {}", emailId);
            throw new BeBetterNotFoundException(ErrorCode.TASK_NOT_FOUND.getDescription(), ErrorCode.TASK_NOT_FOUND);
        }
        return taskResponseDto;
    }

    private List<Task> convertTaskDtoToEntity(CreateTaskDto createTaskDto){
        log.info("converting TaskDto to entity");

        return createTaskDto.getTaskDtoList().stream().map(taskDto -> {
                return convertDtoToEntity(taskDto, createTaskDto);
            }).collect(Collectors.toList());
    }

    private Task convertDtoToEntity(TaskDto taskDto, CreateTaskDto createTaskDto){
        return Task.builder()
                .id(10)
                .name(taskDto.getName())
                .assignedPoints(taskDto.getAssignedPoints())
                .forDate(createTaskDto.getForDate())
                .createdTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .status(Status.OPEN)
                .evaluatedPoints(0)
                .emailId(createTaskDto.getEmailId())
                .build();
    }
}


//TODO is it alright to have dto and entity object same?
//TODO: Find the right way to update the Entity. Is update object by setter and then saving the same object correct?
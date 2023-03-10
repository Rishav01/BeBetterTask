package com.bebetter.task.service;

import com.bebetter.task.dto.SetTaskDto;
import com.bebetter.task.dto.TaskDto;
import com.bebetter.task.dto.user.UserPlanResponseDto;
import com.bebetter.task.entity.Task;
import com.bebetter.task.exception.BeBetterBadRequestException;
import com.bebetter.task.exception.ErrorCode;
import com.bebetter.task.repository.TaskRepository;
import com.bebetter.task.service.user.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DiaryService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PlanService planService;

    public List<Task> setTask(SetTaskDto setTaskDto){
        log.info("Request received for setting task");
        UserPlanResponseDto userPlanResponseDto = planService.getUserPlanByEmailId(setTaskDto.getEmailId());
        List<Task> existingTask = taskRepository.findByUserEmailIdAndForDate(setTaskDto.getEmailId(), setTaskDto.getForDate());
        if(userPlanResponseDto.getMaxTaskAllowed() < (existingTask.size()+setTaskDto.getTaskDtoList().size())){
            throw new BeBetterBadRequestException(ErrorCode.TASK_CREATION_LIMIT_EXHAUSTED.getDescription(), ErrorCode.TASK_CREATION_LIMIT_EXHAUSTED);
        }
        List<Task> taskList = convertTaskDtoToEntity(setTaskDto.getTaskDtoList());
        taskRepository.saveAll(taskList);
        return taskList;
    }

    private List<Task> convertTaskDtoToEntity(List<TaskDto> taskDtoList){
        log.info("converting TaskDto to entity");
        return taskDtoList.stream().map(taskDto -> {
                return convertDtoToEntity(taskDto);
            }).collect(Collectors.toList());
    }

    private Task convertDtoToEntity(TaskDto taskDto){
        return Task.builder()
                .taskName(taskDto.getTaskName())
                .taskAssignedPoints(taskDto.getTaskAssignedPoints())
                .taskAddedTime(LocalDateTime.now())
                .isTaskCompleted(false)
                .build();
    }
}

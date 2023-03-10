package com.bebetter.task.service;

import com.bebetter.task.dto.EfficiencyRequestDto;
import com.bebetter.task.dto.TaskByDateResponseDto;
import com.bebetter.task.entity.Task;
import com.bebetter.task.exception.BeBetterNotFoundException;
import com.bebetter.task.exception.ErrorCode;
import com.bebetter.task.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ToDoService {

    @Autowired
    TaskRepository taskRepository;

    public List<TaskByDateResponseDto> getTaskByDate(String userEmailId, LocalDate date){
        log.info("Starting to get the Tasks for a date");
        List<Task> taskResponseDto = taskRepository.findByUserEmailIdAndForDate(userEmailId, date);
        if(taskResponseDto.size()==0){
            log.error("Exception has occurred for user {} and date {}", userEmailId, date);
            throw new BeBetterNotFoundException(ErrorCode.TASK_NOT_FOUND.getDescription(), ErrorCode.TASK_NOT_FOUND);
        }
        return convertIntoResponseTaskDto(taskRepository.findByUserEmailIdAndForDate(userEmailId, date));
    }

    public Double getUserEfficiency(EfficiencyRequestDto efficiencyRequestDto){
        log.info("Starting to compute the user efficiency from date {} to date {}",
                efficiencyRequestDto.getFromDate(), efficiencyRequestDto.getToDate());
        List<Task> tasks = taskRepository.getTasksByUserAndDuration(efficiencyRequestDto.getUserEmailId(),
                efficiencyRequestDto.getFromDate(), efficiencyRequestDto.getToDate());
        return calculateEfficiency(tasks);
    }

    private List<TaskByDateResponseDto> convertIntoResponseTaskDto(List<Task> tasks){
        return tasks.stream().map(task -> TaskByDateResponseDto.builder()
                            .taskName(task.getTaskName())
                            .taskPoint(task.getTaskAssignedPoints())
                            .isCompleted(task.getIsTaskCompleted()).build()
                    ).collect(Collectors.toList());
    }

    private Double calculateEfficiency(List<Task> tasks){
        /*Integer a = Integer.valueOf(0);
        int totalPoints = 0;
        int totalEvaluatedPoints = 0;
        tasks.stream().forEach(input -> {
            a = a + input.getTaskAssignedPoints();
           totalEvaluatedPoints = totalEvaluatedPoints + input.getTaskEvaluationPoints()
        });*/
        return new Double(100.00);
    }

}

package com.bebetter.task.controller;

import com.bebetter.task.dto.EfficiencyRequestDto;
import com.bebetter.task.dto.TaskByDateResponseDto;
import com.bebetter.task.service.ToDoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/todo")
@Slf4j
public class ToDoController {

    @Autowired
    ToDoService toDoService;

    @GetMapping("/task-by-date")
    public List<TaskByDateResponseDto> getTaskByDate(@RequestParam String userEmailId,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
      log.info("Request received for getting all task for user {} for date {}", userEmailId, date);
      return toDoService.getTaskByDate(userEmailId, date);
    }

    @PostMapping("/get-efficiency")
    public Double presentDayEfficiency(@RequestBody EfficiencyRequestDto efficiencyRequestDto){
        log.info("Request received for getting efficiency for {}", efficiencyRequestDto.getUserEmailId());
        return toDoService.getUserEfficiency(efficiencyRequestDto);
    }
}

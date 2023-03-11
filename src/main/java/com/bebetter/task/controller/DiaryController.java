package com.bebetter.task.controller;

import com.bebetter.task.dto.SetTaskDto;
import com.bebetter.task.entity.Task;
import com.bebetter.task.service.DiaryService;
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
@RequestMapping("/diary")
@Slf4j
@Validated
public class DiaryController {

    @Autowired
    DiaryService diaryService;

    @PostMapping("/set-diary")
    public ResponseEntity<List<Task>> setDiaryTask(@Valid @RequestBody SetTaskDto setDiaryDto){
        log.info("Request received for setting task received for user {}", setDiaryDto.getEmailId());
        return new ResponseEntity<>(diaryService.setTask(setDiaryDto), HttpStatus.CREATED);
    }

    @PostMapping("/get-diary")
    public String getDiaryTask(@RequestParam @Email String emailId,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                               @FutureOrPresent LocalDate forDate){
        log.info("Request received for getting the diary for user {} and for date",
                emailId, forDate);
        return "";
    }
}

package com.bebetter.task.dto;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateTaskDto {
    @Email
    private String emailId;
    @FutureOrPresent()
    private LocalDate forDate;
    @Valid
    private List<TaskDto> taskDtoList;
}

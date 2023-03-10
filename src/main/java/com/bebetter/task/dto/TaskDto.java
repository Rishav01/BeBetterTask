package com.bebetter.task.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class TaskDto {
    @Email
    private String userEmailId;
    @NotNull
    private String taskName;
    @NotNull
    private Integer taskAssignedPoints;
    @FutureOrPresent
    private LocalDate forDate;
}
//TODO: Check the right way of camel casing Dto class
package com.bebetter.task.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskByDateResponseDto {
    private String taskName;
    private Integer taskPoint;
    private Boolean isCompleted;
}

package com.bebetter.task.dto;

import lombok.Getter;

@Getter
public class TaskCompleteRequestDto {
    private Integer taskId;
    private Integer taskEvaluationPoints;
}

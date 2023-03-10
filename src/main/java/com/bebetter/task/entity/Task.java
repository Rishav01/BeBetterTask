package com.bebetter.task.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;
    private String taskName;
    private Integer taskAssignedPoints;
    private LocalDate forDate;
    private LocalDateTime taskAddedTime;
    private LocalDateTime taskCancelledTime;
    private LocalDateTime taskCompletedTime;
    private Boolean isTaskCompleted;
    private Integer taskEvaluationPoints;
    private String userEmailId;
}

//TODO: check if @ToString annotation is alright to add. What is the right way to print object in log
package com.bebetter.task.entity;

import com.bebetter.task.enums.Status;
import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private Integer assignedPoints;
    private LocalDate forDate;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Integer evaluatedPoints;
    private String emailId;
}

//TODO: check if @ToString annotation is alright to add. What is the right way to print object in log
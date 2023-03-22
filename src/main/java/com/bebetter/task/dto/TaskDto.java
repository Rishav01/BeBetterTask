package com.bebetter.task.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class TaskDto {
    @NotNull
    private String name;
    @NotNull
    private Integer assignedPoints;
}
//TODO: Check the right way of camel casing Dto class
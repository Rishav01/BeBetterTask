package com.bebetter.task.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EfficiencyRequestDto {

    private String userEmailId;
    private LocalDate fromDate;
    private LocalDate toDate;
}

package com.bebetter.task.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserPlanResponseDto {
    private String planName;
    private Integer maxTaskAllowed;
}
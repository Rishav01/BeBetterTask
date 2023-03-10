package com.bebetter.task.service.user;

import com.bebetter.task.dto.user.UserPlanResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PlanService {

    @Value("${user.service.base.url}")
    private String url;

    public UserPlanResponseDto getUserPlanByEmailId(String userEmailId){
        log.info("Making a call to user service to get the Plan of the user");
        String completeUrl = url+"plan/user-current-plan?userEmailId={userEmailId}";
        Map<String, String> param = new HashMap<>();
        param.put("userEmailId", userEmailId);

        ResponseEntity<UserPlanResponseDto> userPlan = new RestTemplate()
                .getForEntity(completeUrl, UserPlanResponseDto.class, param);

        log.info("sending response received from user service");
        return userPlan.getBody();
    }

}

//TODO: Check how can we have interface utilized here. Also, the mock class`
//TODO: Check details about the restTemaplte methods
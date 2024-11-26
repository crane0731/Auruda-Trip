package com.sw.AurudaTrip.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TravelPlanEmailDto {
    private String title;
    private String email;
    private String region;
    private String startDate;  // 여행 시작 날짜
    private String endDate;    // 여행 끝 날짜

}
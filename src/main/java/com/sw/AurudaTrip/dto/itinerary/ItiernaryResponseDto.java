package com.sw.AurudaTrip.dto.itinerary;

import com.sw.AurudaTrip.domain.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

//자신의 여행 계획 상세조회를 응답하기 위한 dto
@Builder
@AllArgsConstructor
@Getter
public class ItiernaryResponseDto {

    private Long travlePlanId;
    private String userEmail;
    private String title;
    private Long travelDay;
    private String region;
    private String startDate;
    private String endDate;
    private Theme theme1;
    private Theme theme2;
    private Theme theme3;
    private List<PlaceDto> places;


}

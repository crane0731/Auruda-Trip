package com.sw.AurudaTrip.dto.google;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateItineraryRequestDto {
    private String startDate;
    private String endDate;
    private String travelDay;
    private String city;
    private Integer areaCode;
    private Integer sigunguCode;
//    private String category;
    private String theme1;
    private String theme2;
    private String theme3;

}

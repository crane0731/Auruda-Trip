package com.sw.AurudaTrip.dto.publicdata;

import lombok.*;

//여행지를 저장할 dto

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TouristSpotDto {
    private Long travelPlaceId;
    private Long travelCount;
    private String city;
    private String address;
    private String areaCode;
    private String sigunguCode;
    private String category;
    private String photoUrl;
    private String name;
    private double lat;
    private double lng;
}

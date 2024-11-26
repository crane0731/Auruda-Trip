package com.sw.AurudaTrip.dto.place;

//travelcount가 높은 순서대로 리스트로 뽑아내기 위한 DTO

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceListByTravelCountResponseDto {
    private Long placeId;
    private String placeName;
    private String city;
    private String Category;
    private Long travelCount;

    private double lat;
    private double lng;
    private String photoUrl;

}

package com.sw.AurudaTrip.dto.place;


import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

//장소 상세 조회를 위한 응답 DTO
@Getter
@Builder
public class PlaceResponseDto {

    private Long id;
    private String name;
    private String city;
    private String address;
    private String category;
    private Long travelCount;
    private String description;

    private double lat;
    private double lng;
    private String photoUrl;
}

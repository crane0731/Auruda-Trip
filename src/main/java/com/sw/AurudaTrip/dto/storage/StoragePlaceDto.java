package com.sw.AurudaTrip.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//저장소에 등록할 여행장소 DTO

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StoragePlaceDto {

    private Long id;
    private String name;
    private String city;
    private String address;
    private String category;
    private String description;

    private double lat;
    private double lng;
    private String photo_url;

}
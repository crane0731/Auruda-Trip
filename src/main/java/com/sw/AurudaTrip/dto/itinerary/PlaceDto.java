package com.sw.AurudaTrip.dto.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceDto {
    private Long placeId;
    private String day;
    private String name;
    private String city;
    private String address;
    private String category;
    private String description;

    private double lat;
    private double lng;
    private String photo_url;

}

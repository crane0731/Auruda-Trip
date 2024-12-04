package com.sw.AurudaTrip.dto.gpt;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GptPlaceResponseDto {

    private Long travelPlaceId;
    private Long travelCount;
    private String day;
    private String name;
    private String city;
    private String address;
    private double lat;
    private double lng;
    private String photoUrl;
    private String description;
    private String category;


}

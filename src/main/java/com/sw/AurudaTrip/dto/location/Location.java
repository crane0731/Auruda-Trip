package com.sw.AurudaTrip.dto.location;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//위치를 표현하는 DTO
public class Location {
    private double latitude;
    private double longitude;
    private String name; //장소 이름


}

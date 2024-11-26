package com.sw.AurudaTrip.dto.storage;

import com.sw.AurudaTrip.dto.itinerary.PlaceDto;
import lombok.*;

//저장소에 여행장소를 저장을 위한 요청 DTO

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageRequestDto {

    private PlaceDto place;

}

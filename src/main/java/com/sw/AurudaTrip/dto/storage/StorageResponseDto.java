package com.sw.AurudaTrip.dto.storage;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


//저장소 조회 응답 DTO
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageResponseDto {
    private String userEmail;
    private String userName;
    private String storageName;
    private LocalDateTime createdAt;
    private List<StoragePlaceDto> places;

}

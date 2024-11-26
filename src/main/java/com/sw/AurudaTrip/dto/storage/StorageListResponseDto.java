package com.sw.AurudaTrip.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
//저장소 리스트 응답을 위한 DTO
@Getter
@Setter
@Builder
@AllArgsConstructor
public class StorageListResponseDto {

    private Long storageId;
    private String storageName;
}

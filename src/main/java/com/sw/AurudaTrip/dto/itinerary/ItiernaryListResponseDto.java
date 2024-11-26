package com.sw.AurudaTrip.dto.itinerary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//여행 계획을 리스트로 조회하기 위한 응답 dto
public class ItiernaryListResponseDto {
    private Long id;
    private String title;
}

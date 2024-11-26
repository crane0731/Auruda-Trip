package com.sw.AurudaTrip.dto.place;

import lombok.Getter;

//카테고리 별로 장소라스트를 조회하기 위한 요청 DTO
@Getter
public class CategoryRequestDto {
    private String category;
}

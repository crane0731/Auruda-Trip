package com.sw.AurudaTrip.dto.place;

import lombok.Getter;

//장소의 이름과 주소로 장소데이터가 있는지 확인 하기 위한 dto
@Getter
public class PlaceRequestDto {
    private String name;
    private String address;
}

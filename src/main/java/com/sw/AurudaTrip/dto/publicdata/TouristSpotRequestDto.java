package com.sw.AurudaTrip.dto.publicdata;

import lombok.Getter;

//장소 데이터를 공공데이터 포탈에서 가져오기 위한 요청 dto

@Getter
public class TouristSpotRequestDto {

    private String city;
    private Integer contentTypeId;
    private Integer areaCode;
    private Integer sigunguCode;
    private Integer pageNo;
}

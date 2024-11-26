package com.sw.AurudaTrip.dto.gpt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GptPlaceRequestDto {
    private String placeName;
    private String category;
}

package com.sw.AurudaTrip.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewRequestDto {

    private Long placeId;
    private String content;
    private Double rating;

}

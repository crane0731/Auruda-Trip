package com.sw.AurudaTrip.dto.review;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReviewListResponseDto {

    private Long reviewId;
    private Long userId;
    private String userEmail;
    private String userName;
    private Long placeId;
    private String placeName;
    private String content;
    private Double rating;
    private LocalDateTime createdAt;

}

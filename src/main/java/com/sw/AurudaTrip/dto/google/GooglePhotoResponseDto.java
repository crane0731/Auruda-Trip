package com.sw.AurudaTrip.dto.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GooglePhotoResponseDto {

    private String name;
    private String photoUrl;

}

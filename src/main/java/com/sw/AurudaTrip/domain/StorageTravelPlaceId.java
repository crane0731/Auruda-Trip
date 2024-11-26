package com.sw.AurudaTrip.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class StorageTravelPlaceId {

    @Column(name ="storage_id")
    private Long storageId;
    @Column(name = "travel_place_id")
    private Long travelPlaceId;
}

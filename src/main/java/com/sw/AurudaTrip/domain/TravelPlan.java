package com.sw.AurudaTrip.domain;

import jakarta.persistence.*;
import lombok.*;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "travel_plan", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "name"})
})
public class TravelPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_plan_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name = "region",nullable = false)
    private String region;

    @OneToMany(mappedBy = "travelPlan",cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "travel_plan_travel_places")
    @Builder.Default // 기본값을 유지하도록 설정
    private List<TravelPlanTravelPlace> travelPlanTravelPlaces=new ArrayList<>();

    @Column(name="travel_day", nullable=false)
    private Long travelDay;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date",nullable = false)
    private String endDate;

    @Enumerated(EnumType.STRING)
    @Column(name="theme1")
    private Theme theme1;

    @Enumerated(EnumType.STRING)
    @Column(name="theme2")
    private Theme theme2;

    @Enumerated(EnumType.STRING)
    @Column(name="theme3")
    private Theme theme3;


    public void addTravelPlanTravelPlace(TravelPlanTravelPlace travelPlanTravelPlace) {
        travelPlanTravelPlaces.add(travelPlanTravelPlace);
        travelPlanTravelPlace.setTravelPlan(this);
    }

    public void removeTravelPlanTravelPlace(TravelPlanTravelPlace travelPlanTravelPlace) {
        travelPlanTravelPlaces.remove(travelPlanTravelPlace);
        travelPlanTravelPlace.setTravelPlan(null);
    }

    public void updateTravelPlan(String title) {
        // 필드 업데이트

        this.title = title;

    }


}

package com.sw.AurudaTrip.repository;

import com.sw.AurudaTrip.domain.TravelPlan;
import com.sw.AurudaTrip.domain.TravelPlanTravelPlace;
import com.sw.AurudaTrip.domain.TravelPlanTravelPlaceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelPlanTravelPlaceRepository extends JpaRepository<TravelPlanTravelPlace, TravelPlanTravelPlaceId> {

    List<TravelPlanTravelPlace> findByTravelPlan(TravelPlan travelPlan);
}

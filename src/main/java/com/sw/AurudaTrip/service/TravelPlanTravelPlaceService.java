package com.sw.AurudaTrip.service;

import com.sw.AurudaTrip.domain.TravelPlan;
import com.sw.AurudaTrip.domain.TravelPlanTravelPlace;
import com.sw.AurudaTrip.domain.TravelPlanTravelPlaceId;
import com.sw.AurudaTrip.repository.TravelPlanTravelPlaceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TravelPlanTravelPlaceService {
    private final TravelPlanTravelPlaceRepository travelPlanTravelPlaceRepository;

    @Transactional
    public TravelPlanTravelPlace saveTravelPlanTravelPlace(TravelPlanTravelPlace travelPlanTravelPlace) {
        return travelPlanTravelPlaceRepository.save(travelPlanTravelPlace);
    }

    public TravelPlanTravelPlace findTravelPlanTravelPlaceById(TravelPlanTravelPlaceId id) {
        return travelPlanTravelPlaceRepository.findById(id).orElse(null);
    }

    public List<TravelPlanTravelPlace> findAllTravelPlanTravelPlacesByPlan(TravelPlan travelPlan) {
        return travelPlanTravelPlaceRepository.findByTravelPlan(travelPlan);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteTravelPlanTravelPlaceById(TravelPlanTravelPlaceId id) {
        travelPlanTravelPlaceRepository.deleteById(id);
    }

    @Transactional
    public TravelPlanTravelPlace updateTravelPlanTravelPlace(TravelPlanTravelPlaceId travelPlanTravelPlaceId, String newDay) {
        TravelPlanTravelPlace travelPlanTravelPlace = travelPlanTravelPlaceRepository.findById(travelPlanTravelPlaceId).orElseThrow(() -> new RuntimeException("travel plan place not found"));
        return travelPlanTravelPlace.update(travelPlanTravelPlace.getTravelPlan(),travelPlanTravelPlace.getTravelPlace(),newDay);
    }
}

package com.sw.AurudaTrip.controller;

import com.sw.AurudaTrip.domain.TravelPlace;
import com.sw.AurudaTrip.dto.place.*;
import com.sw.AurudaTrip.service.TravelPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auruda/place")
public class TravelPlaceController {

    private final TravelPlaceService travelPlaceService;

    //travelCount가 높은 순서대로 리스트로 뽑아내는 컨트롤러
    @GetMapping
    public ResponseEntity<List<PlaceListByTravelCountResponseDto>> getPlaceListByTravelCount(){
        List<PlaceListByTravelCountResponseDto> placeListByTravelCount = travelPlaceService.getPlaceListByTravelCount();
        return ResponseEntity.ok(placeListByTravelCount);
    }


    //category별로 travelCount가 높은 순서대로 리스트로 뽑아내는 컨트롤러
    @PostMapping("/category")
    public ResponseEntity<List<PlaceListByTravelCountResponseDto>> getPlaceListByTravelCountByCategory(@RequestBody CategoryRequestDto requestDto){
        List<PlaceListByTravelCountResponseDto> places = travelPlaceService.getPlaceListByTravelCountByCategory(requestDto);
        return ResponseEntity.ok(places);
    }

    //city별로 travelCount가 높은 순서대로 리스트로 뽑아내는 컨트롤러
    @PostMapping("/city")
    public ResponseEntity<List<PlaceListByTravelCountResponseDto>> getPlaceListByTravelCountByCity(@RequestBody CityRequestDto requestDto){
        List<PlaceListByTravelCountResponseDto> places = travelPlaceService.getPlaceListByTravelCountByCity(requestDto);
        return ResponseEntity.ok(places);
    }

    //장소 상세조회 컨트롤러
    @GetMapping("/{place_id}")
    public ResponseEntity<PlaceResponseDto> getPlaceById(@PathVariable("place_id") long placeId){
        PlaceResponseDto place = travelPlaceService.getPlace(placeId);
        return ResponseEntity.ok(place);
    }



}

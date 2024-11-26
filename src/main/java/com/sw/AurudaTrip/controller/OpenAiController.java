package com.sw.AurudaTrip.controller;

import com.sw.AurudaTrip.dto.google.CreateItineraryRequestDto;

import com.sw.AurudaTrip.dto.gpt.GptPlaceResponseDto;
import com.sw.AurudaTrip.dto.publicdata.TouristSpotDto;
import com.sw.AurudaTrip.dto.publicdata.TouristSpotRequestDto;
import com.sw.AurudaTrip.service.GoogleMapApiService;

import com.sw.AurudaTrip.service.PublicDataService;
import com.sw.AurudaTrip.service.gpt.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auruda/travel")
public class OpenAiController {

    private final OpenAiService openAiService;
    private final GoogleMapApiService googleMapApiService;
    private final PublicDataService publicDataService;

    //추천 여행지를 보여주는 컨트롤러
    @PostMapping("places")
    public ResponseEntity<List<TouristSpotDto>> getPlaceData(@RequestBody TouristSpotRequestDto requestDto){
        List<TouristSpotDto> touristSpots = publicDataService.getTouristSpots(requestDto.getCity(),requestDto.getContentTypeId(), requestDto.getAreaCode(), requestDto.getSigunguCode(),requestDto.getPageNo());
        return ResponseEntity.ok(touristSpots);
    }

    //여행 코스를 추천해주는 컨트롤러
    @PostMapping("itinerary")
    public ResponseEntity<List<GptPlaceResponseDto>> getItinerary(@RequestBody CreateItineraryRequestDto requestDto) throws Exception {
        List<TouristSpotDto> places=new ArrayList<>();
        List<TouristSpotDto> touristSpots1 = publicDataService.getTouristSpots(requestDto.getCity(), 38, requestDto.getAreaCode(), requestDto.getSigunguCode(),1);
        places.addAll(touristSpots1);

        List<TouristSpotDto> touristSpots2 = publicDataService.getTouristSpots(requestDto.getCity(),39, requestDto.getAreaCode(), requestDto.getSigunguCode(),1);
        places.addAll(touristSpots2);

        List<TouristSpotDto> touristSpots3 = publicDataService.getTouristSpots(requestDto.getCity(),32, requestDto.getAreaCode(), requestDto.getSigunguCode(),1);
        places.addAll(touristSpots3);

        List<TouristSpotDto> touristSpots4 = publicDataService.getTouristSpots(requestDto.getCity(),15, requestDto.getAreaCode(), requestDto.getSigunguCode(),1);
        places.addAll(touristSpots4);

        List<TouristSpotDto> touristSpots5 = publicDataService.getTouristSpots(requestDto.getCity(),14, requestDto.getAreaCode(), requestDto.getSigunguCode(),1);
        places.addAll(touristSpots5);

        List<TouristSpotDto> touristSpots6 = publicDataService.getTouristSpots(requestDto.getCity(),12, requestDto.getAreaCode(), requestDto.getSigunguCode(),1);
        places.addAll(touristSpots6);

        List<TouristSpotDto> touristSpots7 = publicDataService.getTouristSpots(requestDto.getCity(),28, requestDto.getAreaCode(), requestDto.getSigunguCode(),1);
        places.addAll(touristSpots7);

        List<TouristSpotDto> touristSpots8 = publicDataService.getTouristSpots(requestDto.getCity(),1, requestDto.getAreaCode(), requestDto.getSigunguCode(),1);
        places.addAll(touristSpots8);

        List<GptPlaceResponseDto> travelItinerary = openAiService.getTravelItinerary(requestDto, places);
        return ResponseEntity.ok().body(travelItinerary);

    }


//    //추천 여행지를 보여주는 컨트롤러
//    @PostMapping("places")
//    public ResponseEntity<List<PlaceInfoDto>> places(@RequestBody GoogleItineraryRequestDto requestDto) {
//        List<PlaceInfoDto> places = googleMapApiService.getPlaces(requestDto,requestDto.getCategory());
//        return ResponseEntity.ok().body(places);
//    }

//    //여행 코스를 추천해주는 컨트롤러
//    @PostMapping("/itinerary")
//    public ResponseEntity<List<GptPlaceResponseDto>> getPlaces(@RequestBody GoogleItineraryRequestDto requestDto) throws Exception {
//        List<PlaceInfoDto> places=new ArrayList<>();
//
//        List<PlaceInfoDto> places1 = googleMapApiService.getPlaces(requestDto,"숙소");
//        places.addAll(places1);
//
//        List<PlaceInfoDto> places2 = googleMapApiService.getPlaces(requestDto,"관광지");
//        places.addAll(places2);
//
//        List<PlaceInfoDto> places3 = googleMapApiService.getPlaces(requestDto,"음식점");
//        places.addAll(places3);
//
//        List<PlaceInfoDto> places4 = googleMapApiService.getPlaces(requestDto,"카페");
//        places.addAll(places4);
//
//
//        List<GptPlaceResponseDto> travelItinerary = openAiService.getTravelItinerary(requestDto, places);
//        return ResponseEntity.ok().body(travelItinerary);
//    }

}
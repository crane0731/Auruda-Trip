package com.sw.AurudaTrip.controller.kakaomap;


import com.sw.AurudaTrip.service.kakaomap.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kakao/places")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<String> getPlaces(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam String category) {
        String response = placeService.getPlaces(latitude, longitude, category);
        return ResponseEntity.ok(response);
    }
}
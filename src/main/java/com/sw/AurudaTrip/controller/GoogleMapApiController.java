package com.sw.AurudaTrip.controller;

import com.sw.AurudaTrip.dto.google.GooglePhotoRequestDto;
import com.sw.AurudaTrip.dto.google.GooglePhotoResponseDto;
import com.sw.AurudaTrip.service.GoogleMapApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auruda/photo")
public class GoogleMapApiController {

    private final GoogleMapApiService googleMapApiService;


@PostMapping("")
public ResponseEntity<List<GooglePhotoResponseDto>> getPhoto(@RequestBody GooglePhotoRequestDto googlePhotoRequestDto) {


    List<GooglePhotoResponseDto> photoResponseDtos = new ArrayList<>();

    List<String> placeName = googlePhotoRequestDto.getPlaceName();
    for (String s : placeName) {
        GooglePhotoResponseDto googlePhotoReference = googleMapApiService.getGooglePhotoReference(s);
        photoResponseDtos.add(googlePhotoReference);
    }

    return ResponseEntity.ok(photoResponseDtos);


}

}

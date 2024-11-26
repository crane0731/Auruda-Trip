package com.sw.AurudaTrip.controller;

import com.sw.AurudaTrip.dto.itinerary.PlaceDto;
import com.sw.AurudaTrip.dto.storage.EmptyStorageRequestDto;
import com.sw.AurudaTrip.dto.storage.StorageListResponseDto;
import com.sw.AurudaTrip.dto.storage.StorageResponseDto;
import com.sw.AurudaTrip.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auruda/storage")
public class StorageController {

    private final StorageService storageService;

    //빈 저장소 등록
    @PostMapping("/empty")
    public ResponseEntity<Object> createEmptyStorage(@RequestHeader("User-Id")Long userId, @RequestBody EmptyStorageRequestDto request) {
        try {
            storageService.createEmptyStorage(userId, request);
            return ResponseEntity.ok().body("빈 저장소 등록에 성공했습니다.");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    //저장소에 여행장소 추가
    @PostMapping("/place/{storage_id}")
    public ResponseEntity<Object> addPlaceFromStorage(@PathVariable("storage_id") Long storageId, @RequestBody PlaceDto placeDto) {
            storageService.addPlace(storageId, placeDto);
            return ResponseEntity.ok().body("저장소에 여행장소 추가를 성공했습니다.");

    }

    //저장소에 여행장소 제거
    @DeleteMapping("/place/{storage_id}/{place_id}")
    public ResponseEntity<Object> removePlaceFromStorage(@PathVariable("storage_id") Long storageId,@PathVariable("place_id")Long placeId) {

            storageService.removePlace(storageId, placeId);
            return ResponseEntity.ok().body("저장소에 여행장소 삭제를 성공했습니다.");

    }


    //저장소 조회
    @GetMapping("/{storage_id}")
    public ResponseEntity<Object> getStorage(@PathVariable("storage_id") Long storageId) {

            StorageResponseDto storage = storageService.getStorage(storageId);
            return ResponseEntity.ok().body(storage);

    }

    //저장소 리스트 조회
    @GetMapping("")
    public ResponseEntity<Object> getStorages(@RequestHeader("User-Id")Long userId) {

            List<StorageListResponseDto> storages = storageService.getAllStoragesByUser(userId);
            return ResponseEntity.ok().body(storages);

    }

    //저장소 삭제
    @DeleteMapping("/{storage_id}")
    public ResponseEntity<Object> deleteStorage(@PathVariable("storage_id") Long storageId) {
            storageService.deleteStorage(storageId);
            return ResponseEntity.ok().body("저장소 삭제에 성공했습니다.");
    }








}

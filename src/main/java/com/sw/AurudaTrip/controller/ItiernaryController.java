package com.sw.AurudaTrip.controller;

import com.sw.AurudaTrip.dto.itinerary.ItiernaryListResponseDto;
import com.sw.AurudaTrip.dto.itinerary.ItiernaryRequestDto;
import com.sw.AurudaTrip.dto.itinerary.ItiernaryResponseDto;
import com.sw.AurudaTrip.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auruda/travel")
public class ItiernaryController {


    private final TravelPlanService travelPlanService;
    private final ItiernaryService itiernaryService;

    // 여행 등록
    @PostMapping("")
    public ResponseEntity<Object> saveItiernary(@RequestHeader("User-Id") Long userId, @RequestBody ItiernaryRequestDto requestDto, BindingResult bindingResult) {
        // 오류 메시지를 담을 Map
        Map<String, String> errorMessages = new HashMap<>();

        // 1. 유효성 검사에서 오류가 발생한 경우 모든 메시지를 Map에 추가
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessages.put(error.getField(), error.getDefaultMessage())
            );
        }

        // 5. 오류 메시지가 존재하면 이를 반환
        if (!errorMessages.isEmpty()) {
            return ResponseEntity.badRequest().body(errorMessages);
        }


            itiernaryService.createItiernary(userId,requestDto);
            return ResponseEntity.ok("여행 계획 등록에 성공하였습니다.");

    }

    //자신의 여행 계획 리스트로 조회
    @GetMapping("/plans")
    public ResponseEntity<List<ItiernaryListResponseDto>> getAllItineraryList(@RequestHeader("User-Id") Long userId) {
            return ResponseEntity.ok().body(travelPlanService.getAllTravelPlans(userId));

    }

    //테마별로 여행 계획 리스트 조회
    @GetMapping("/plans/theme")
    public ResponseEntity<List<ItiernaryListResponseDto>> getItineraryListByTheme(@RequestParam("theme")String theme){
        List<ItiernaryListResponseDto> plans = travelPlanService.getPlansByTheme(theme);
        return ResponseEntity.ok().body(plans);
    }

    //여행 계획 상세 조회
    @GetMapping("/plans/{planId}")
    public ResponseEntity<ItiernaryResponseDto> getItinerary(@PathVariable("planId") Long planId) {

        //ItiernaryService에 여행 계획 상세조회하는 메서드 추가해야함

            ItiernaryResponseDto detailItiernary = itiernaryService.getDetailItiernary(planId);
            return ResponseEntity.ok().body(detailItiernary);

    }

    //여행 계획 삭제
    @DeleteMapping("plans/{plan_id}")
    public ResponseEntity<Object> deleteItinerary(@PathVariable("plan_id") Long planId) {

            travelPlanService.deleteTravelPlanById(planId);
            return ResponseEntity.ok("여행 계획 삭제를 성공하였습니다.");

    }

    //여행 계획 수정
    @PutMapping("plans/{plan_id}")
    public ResponseEntity<Object> updateItinerary(
            @PathVariable("plan_id") Long planId,
            @Valid @RequestBody ItiernaryRequestDto requestDto,  // @Valid 추가
            BindingResult bindingResult) {

        // 오류 메시지를 담을 Map
        Map<String, String> errorMessages = new HashMap<>();

        // 1. 유효성 검사에서 오류가 발생한 경우 모든 메시지를 Map에 추가
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessages.put(error.getField(), error.getDefaultMessage())
            );
        }

        // 2. 오류 메시지가 존재하면 이를 반환
        if (!errorMessages.isEmpty()) {
            return ResponseEntity.badRequest().body(errorMessages);
        }


            itiernaryService.updateItinerary(planId, requestDto);
            return ResponseEntity.ok().body("여행 계획 수정 성공");

    }
}

package com.sw.AurudaTrip.service;

import com.sw.AurudaTrip.domain.*;
import com.sw.AurudaTrip.dto.itinerary.ItiernaryRequestDto;
import com.sw.AurudaTrip.dto.itinerary.ItiernaryResponseDto;
import com.sw.AurudaTrip.dto.itinerary.PlaceDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItiernaryService {

    private final UserService userService;
    private final TravelPlanService travelPlanService;
    private final TravelPlaceService travelPlaceService;
    private final TravelPlanTravelPlaceService travelPlanTravelPlaceService;

    //여행계획 생성
    @Transactional
    public void createItiernary(Long userId,ItiernaryRequestDto requestDto) {
        try {
            //유저 찾기
            User user = userService.findUser(userId);

            //여행 플랜 저장

            TravelPlan travelPlan = TravelPlan.builder()
                    .user(user)
                    .title(requestDto.getTitle())
                    .region(requestDto.getRegion())
                    .travelDay(requestDto.getTravelDay())
                    .startDate(requestDto.getStartDate())
                    .endDate(requestDto.getEndDate())
                    .theme1(requestDto.getTheme1())
                    .theme2(requestDto.getTheme2())
                    .theme3(requestDto.getTheme3())
                    .build();

            TravelPlan savedTravelPlan = travelPlanService.saveTravelPlan(travelPlan);

            //여행 장소 리스트 저장
            requestDto.getPlaces().forEach(placeDto -> {
                TravelPlace place = travelPlaceService.savePlace(placeDto);

                //복합 키 설정
                TravelPlanTravelPlaceId travelPlanTravelPlaceId = TravelPlanTravelPlaceId.builder()
                        .travelPlaceId(place.getId())
                        .travelPlanId(savedTravelPlan.getId())
                        .build();

                //여팽플랜여행장소 저장
                TravelPlanTravelPlace travelPlanTravelPlace = TravelPlanTravelPlace.builder()
                        .id(travelPlanTravelPlaceId)
                        .day(placeDto.getDay())
                        .travelPlan(savedTravelPlan)
                        .travelPlace(place)
                        .build();

                TravelPlanTravelPlace savedTravelPlanTravelPlace = travelPlanTravelPlaceService.saveTravelPlanTravelPlace(travelPlanTravelPlace);


                Long id = savedTravelPlan.getId();
                TravelPlan travelPlanById = travelPlanService.getTravelPlanById(id);

                travelPlanById.addTravelPlanTravelPlace(savedTravelPlanTravelPlace);

            });
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    //여행계획 수정
    @Transactional
    public void updateItinerary(Long planId, ItiernaryRequestDto requestDto) {
        // 기존 TravelPlan 조회
        TravelPlan travelPlan = travelPlanService.getTravelPlanById(planId);

        List<TravelPlanTravelPlace> newTravelPlanTravelPlaceList = new ArrayList<>();

        // 요청된 places가 빈 리스트가 아닌 경우 처리
        if (requestDto.getPlaces() != null && !requestDto.getPlaces().isEmpty()) {
            for (PlaceDto placeDto : requestDto.getPlaces()) {
                // TravelPlace 저장 또는 조회
                TravelPlace place = travelPlaceService.savePlace(placeDto);

                // TravelPlanTravelPlace 생성
                TravelPlanTravelPlaceId travelPlanTravelPlaceId = new TravelPlanTravelPlaceId(travelPlan.getId(), place.getId());
                TravelPlanTravelPlace newTravelPlanTravelPlace = TravelPlanTravelPlace.builder()
                        .id(travelPlanTravelPlaceId)
                        .day(placeDto.getDay())
                        .travelPlan(travelPlan)
                        .travelPlace(place)
                        .build();

                // 새로운 TravelPlanTravelPlace 리스트에 추가
                newTravelPlanTravelPlaceList.add(newTravelPlanTravelPlace);
            }
        }

        // 기존 TravelPlanTravelPlace 리스트 가져오기
        List<TravelPlanTravelPlace> existingTravelPlanPlaces = new ArrayList<>(travelPlan.getTravelPlanTravelPlaces());

        // 기존 ID와 새로운 ID를 비교
        Set<TravelPlanTravelPlaceId> existingIds = existingTravelPlanPlaces.stream()
                .map(TravelPlanTravelPlace::getId)
                .collect(Collectors.toSet());
        Set<TravelPlanTravelPlaceId> newIds = newTravelPlanTravelPlaceList.stream()
                .map(TravelPlanTravelPlace::getId)
                .collect(Collectors.toSet());

        // 새로운 항목 추가
        for (TravelPlanTravelPlace newPlace : newTravelPlanTravelPlaceList) {
            if (!existingIds.contains(newPlace.getId())) {
                travelPlan.addTravelPlanTravelPlace(newPlace);
            }
        }

        // 기존 항목 중 삭제 대상 제거
        for (TravelPlanTravelPlace existingPlace : existingTravelPlanPlaces) {
            if (!newIds.contains(existingPlace.getId())) {
                travelPlan.removeTravelPlanTravelPlace(existingPlace);
            }
        }

        // TravelPlan의 제목 업데이트
        travelPlan.updateTravelPlan(requestDto.getTitle());
    }

    // 여행 계획 상세조회하는 서비스
    public ItiernaryResponseDto getDetailItiernary(Long planId) {

        try {
            TravelPlan travelPan = travelPlanService.getTravelPlanById(planId);

            List<TravelPlanTravelPlace> travelPlanTravelPlaces = travelPan.getTravelPlanTravelPlaces();

            List<PlaceDto> placeDtoList = new ArrayList<>();

            for (TravelPlanTravelPlace travelPlanTravelPlace : travelPlanTravelPlaces) {
                TravelPlace place = travelPlaceService.getPlaceById(travelPlanTravelPlace.getTravelPlace().getId());

                PlaceDto placeDto = PlaceDto.builder()
                        .day(travelPlanTravelPlace.getDay())
                        .name(place.getName())
                        .city(place.getCity())
                        .address(place.getAddress())
                        .category(place.getCategory().getKoreanName())
                        .description(place.getDescription())
                        .lat(place.getLat())
                        .lng(place.getLng())
                        .photo_url(place.getPhotoUrl())
                        .build();

                placeDtoList.add(placeDto);
            }

            ItiernaryResponseDto itiernaryResponseDto = ItiernaryResponseDto.builder()
                    .travlePlanId(travelPan.getId())
                    .userEmail(travelPan.getUser().getEmail())
                    .title(travelPan.getTitle())
                    .travelDay(travelPan.getTravelDay())
                    .region(travelPan.getRegion())
                    .startDate(travelPan.getStartDate())
                    .endDate(travelPan.getEndDate())
                    .theme1(travelPan.getTheme1())
                    .theme2(travelPan.getTheme2())
                    .theme3(travelPan.getTheme3())
                    .places(placeDtoList)
                    .build();

            return itiernaryResponseDto;
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


}

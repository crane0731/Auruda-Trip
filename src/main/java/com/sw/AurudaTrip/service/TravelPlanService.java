package com.sw.AurudaTrip.service;

import com.sw.AurudaTrip.domain.TravelPlan;
import com.sw.AurudaTrip.dto.itinerary.ItiernaryListResponseDto;
import com.sw.AurudaTrip.repository.TravelPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;

    //여행 계획 저장
    @Transactional
    public TravelPlan saveTravelPlan(TravelPlan travelPlan) {
        try {
            return travelPlanRepository.save(travelPlan);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("동일한 이름의 여행 계획이 이미 존재합니다.");
        }

    }


    //여행계획 조회
    public TravelPlan getTravelPlanById(Long id) {
        return travelPlanRepository.findById(id).orElseThrow(()->new RuntimeException("여행 계획을 찾을 수 없습니다."));
    }

    //여행계획 리스트 조회 서비스
    public List<ItiernaryListResponseDto> getAllTravelPlans(Long userId) {
        List<TravelPlan> travelPlans = travelPlanRepository.findByUserId(userId);

        // TravelPlan 리스트를 ItiernaryListResponseDto 리스트로 변환
        return travelPlans.stream()
                .map(travelPlan -> new ItiernaryListResponseDto(travelPlan.getId(),travelPlan.getTitle()))  // DTO로 변환
                .collect(Collectors.toList());
    }


    //여행 계획 삭제 서비스
    @Transactional
    public void deleteTravelPlanById(Long id) {
        TravelPlan travelPlan = travelPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("여행플랜을 찾을 수 없습니다."));
        travelPlanRepository.deleteById(id);
    }





}

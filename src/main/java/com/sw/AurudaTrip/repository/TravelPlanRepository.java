package com.sw.AurudaTrip.repository;

import com.sw.AurudaTrip.domain.TravelPlan;
import com.sw.AurudaTrip.dto.email.TravelPlanEmailDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {

    // 유저 아이디로 여행 계획 리스트 조회
    List<TravelPlan> findByUserId(Long userId);

    //List<TravelPlan> findByStartDate(String startDate);


    @Query("SELECT new com.sw.AurudaTrip.dto.email.TravelPlanEmailDto(tp.title, u.email, tp.region,tp.startDate, tp.endDate) " +
            "FROM TravelPlan tp JOIN tp.user u WHERE tp.startDate = :startDate")
    List<TravelPlanEmailDto> findEmailDetailsByStartDate(@Param("startDate") String startDate);


}

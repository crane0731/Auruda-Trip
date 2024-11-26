package com.sw.AurudaTrip.repository;

import com.sw.AurudaTrip.domain.Category;
import com.sw.AurudaTrip.domain.TravelPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelPlaceRepository extends JpaRepository<TravelPlace, Long> {

    Optional<TravelPlace> findByNameAndAddress(String name, String address);

    // travelCount를 기준으로 내림차순 정렬하여 최대 20개의 TravelPlace 반환
    List<TravelPlace> findTop20ByOrderByTravelCountDesc();

    //category 별로 travelCount를 기준으로 내림차순 정렬하여 최대 20개의 TravelPlace 반환
    List<TravelPlace> findTop20ByCategoryOrderByTravelCountDesc(Category category);

    List<TravelPlace> findTop20ByCityOrderByTravelCountDesc(String city);

}

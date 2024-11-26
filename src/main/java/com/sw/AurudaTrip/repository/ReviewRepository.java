package com.sw.AurudaTrip.repository;

import com.sw.AurudaTrip.domain.Review;
import com.sw.AurudaTrip.domain.TravelPlace;
import com.sw.AurudaTrip.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByTravelPlace(TravelPlace travelPlace);
    Optional<Review> findByUser(User user);

}

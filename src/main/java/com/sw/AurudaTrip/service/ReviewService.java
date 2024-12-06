package com.sw.AurudaTrip.service;

import com.sw.AurudaTrip.controller.ReviewController;
import com.sw.AurudaTrip.domain.Review;
import com.sw.AurudaTrip.domain.TravelPlace;
import com.sw.AurudaTrip.domain.User;
import com.sw.AurudaTrip.dto.review.AddReviewRequestDto;
import com.sw.AurudaTrip.dto.review.ReviewListResponseDto;
import com.sw.AurudaTrip.dto.review.UpdateReviewRequestDto;
import com.sw.AurudaTrip.repository.ReviewRepository;
import com.sw.AurudaTrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final TravelPlaceService travelPlaceService;



    //리뷰 등록 서비스
    @Transactional
    public void createReview(Long userId,AddReviewRequestDto dto) {

        //회원 찾기
        User user = userService.findUser(userId);

        //여행장소 찾기
        TravelPlace place = travelPlaceService.getPlaceById(dto.getPlaceId());

        //리뷰 객체 생성
        Review review = Review.builder()
                .user(user)
                .travelPlace(place)
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();

        //리뷰 저장
        reviewRepository.save(review);

    }

    @Transactional
    //리뷰 삭제 서비스
    public void deleteReview(Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(()->new RuntimeException("리뷰가 존재하지 않습니다."));
        reviewRepository.deleteById(reviewId);
    }

    //place_id로 리뷰 조회(리스트) 서비스
    public List<ReviewListResponseDto> getReviews(Long placeId) {

        //여행장소 찾기
        TravelPlace place = travelPlaceService.getPlaceById(placeId);


        //응답 객체 생성후 리턴
        List<ReviewListResponseDto> reviewList = reviewRepository.findAllByTravelPlace(place).stream()
                .map(review -> ReviewListResponseDto.builder()
                        .reviewId(review.getId())
                        .userId(review.getUser().getId())
                        .userEmail(review.getUser().getEmail())
                        .userName(review.getUser().getNickname())
                        .placeId(review.getTravelPlace().getId())
                        .placeName(review.getTravelPlace().getName())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .createdAt(review.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        if (reviewList.isEmpty()) {
            throw new RuntimeException("리뷰가 존재하지 않습니다.");
        }

        return reviewList;
    }

    @Transactional
    //리뷰 수정 서비스
    public void updateReview(Long reviewId,UpdateReviewRequestDto dto){
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));
        review.update(dto.getContent(), dto.getRating());
    }



}

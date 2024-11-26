package com.sw.AurudaTrip.controller;

import com.sw.AurudaTrip.dto.review.AddReviewRequestDto;
import com.sw.AurudaTrip.dto.review.ReviewListResponseDto;
import com.sw.AurudaTrip.dto.review.UpdateReviewRequestDto;
import com.sw.AurudaTrip.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auruda/review")
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 등록
    @PostMapping("")
    public ResponseEntity<String> addReview(@RequestHeader("User-Id")Long userId, @RequestBody AddReviewRequestDto requestDto) {

        reviewService.createReview(userId, requestDto);
        return ResponseEntity.ok("리뷰 등록 성공");

    }

    //리뷰 삭제
    @DeleteMapping("/{review_id}")
    public ResponseEntity<String> deleteReview(@PathVariable("review_id") Long review_id) {

            reviewService.deleteReview(review_id);
            return ResponseEntity.ok().body("리뷰 삭제 성공");

    }

    //장소별 리뷰 조회(리스트)
    @GetMapping("/{place_id}")
    public ResponseEntity<Object> getReview(@PathVariable("place_id") Long place_id) {

        List<ReviewListResponseDto> reviews = reviewService.getReviews(place_id);
        return ResponseEntity.ok(reviews);

    }

    //리뷰 수정
    @PutMapping("/{review_id}")
    public ResponseEntity<Object> updateReview(@PathVariable("review_id") Long review_id, @RequestBody UpdateReviewRequestDto requestDto) {

        reviewService.updateReview(review_id, requestDto);
        return ResponseEntity.ok("리뷰 수정 성공");

    }




}

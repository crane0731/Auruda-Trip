package com.sw.AurudaTrip.controller.crawler;

import com.sw.AurudaTrip.service.crawler.CrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auruda")
public class CrawlerController {

    private final CrawlerService crawlerService;

    @GetMapping("/latest-festivals")
    public ResponseEntity<List<Map<String, String>>> getLatestFestivals(
            @RequestParam(required = false) String region,
            @RequestParam(defaultValue = "1") int pageCount) {

        List<Map<String, String>> festivalData = crawlerService.fetchLatestFestivalData(region, pageCount);
        return ResponseEntity.ok(festivalData);
    }

    // 콘서트 정보를 가져오는 엔드포인트
    @GetMapping("/latest-concerts")
    public ResponseEntity<List<Map<String, String>>> getLatestConcerts(
            @RequestParam(defaultValue = "1") int pageCount,
            @RequestParam(defaultValue = "date") String filter, // 필터 파라미터 추가
            @RequestParam(required = false) String region) { // 지역 파라미터 추가

        List<Map<String, String>> concertData = crawlerService.fetchConcertData(pageCount, filter, region); // 지역 파라미터 추가
        return ResponseEntity.ok(concertData);
    }
}
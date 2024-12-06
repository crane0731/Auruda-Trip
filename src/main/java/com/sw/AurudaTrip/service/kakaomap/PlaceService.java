package com.sw.AurudaTrip.service.kakaomap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlaceService {

    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);

    @Value("${kakao.api.rest-api-key}")
    private String kakaoApiKey;

    public String getPlaces(double latitude, double longitude, String category) {
        // kakaoApiKey 값 확인용 로그 출력
        logger.info("Kakao API Key: " + kakaoApiKey);

        String apiUrl = "https://dapi.kakao.com/v2/local/search/category.json";
        String queryParams = String.format("?category_group_code=%s&x=%f&y=%f&radius=5000",
                category, longitude, latitude);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl + queryParams,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}
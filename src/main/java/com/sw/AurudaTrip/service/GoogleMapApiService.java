package com.sw.AurudaTrip.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sw.AurudaTrip.dto.google.GooglePhotoRequestDto;
import com.sw.AurudaTrip.dto.google.GooglePhotoResponseDto;


import com.sw.AurudaTrip.service.redis.GooglePhotoResponseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//구글맵 api를 사용하는 서비스

@Service
public class GoogleMapApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final GooglePhotoResponseService googlePhotoResponseService;

    @Value("${google.api.key}")
    private String apiKey;

    public GoogleMapApiService(RestTemplate restTemplate, ObjectMapper objectMapper, GooglePhotoResponseService googlePhotoResponseService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

        this.googlePhotoResponseService = googlePhotoResponseService;
    }

//    //Google Maps API 를 사용해 인기 장소를 불러오는 서비스
//    public List<PlaceInfoDto> getPlaces(GoogleItineraryRequestDto dto, String category) {
//
//        //Redis 키는 지역 이름으로 지정
//        String rediskey="PlaceInfo:"+dto.getRegion()+" "+category;
//
//        // Redis에서 데이터가 존재하는지 확인
//        if (placeDataInfoService.exists(rediskey)) {
//            System.out.println("있다ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ");
//            // Redis에서 데이터 가져오기
//            return placeDataInfoService.getData(rediskey);
//        }
//
//
//        String query = dto.getRegion()+category;
//
//        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json";
//        String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("query", query)
//                .queryParam("key", apiKey)
//                .queryParam("language", "ko")  // 한국어 응답 추가
//                .build()
//                .toUriString();
//
//        // API 요청 보내기
//        String response = restTemplate.getForObject(requestUrl, String.class);
//
//
//        System.out.println("response = " + response);
//
//        // 첫 번째 응답의 장소 정보 추출
//        List<PlaceInfoDto> placesInfo = extractPlaceInfo(response,dto.getRegion());
//
//        placeDataInfoService.saveData(rediskey, placesInfo);
//
//        return placesInfo;
//
//    }
//
//    // JSON 데이터에서 필요한 필드를 DTO로 추출
//    private List<PlaceInfoDto> extractPlaceInfo(String jsonResponse,String region) {
//        List<PlaceInfoDto> placeInfoList = new ArrayList<>();
//        try {
//            JsonNode root = objectMapper.readTree(jsonResponse);
//            for (JsonNode place : root.path("results")) {
//
//                String address = place.path("formatted_address").asText();
//
//                // region 이 address에 포함되지 않으면 continue
//                if (!address.contains(region)) {
//                    continue;
//                }
//
//                String name = place.path("name").asText();
//                double lat = place.path("geometry").path("location").path("lat").asDouble();
//                double lng = place.path("geometry").path("location").path("lng").asDouble();
//                double rating = place.path("rating").asDouble();
//                int userRatingsTotal = place.path("user_ratings_total").asInt();
//                String type = place.path("types").get(0).asText();
//
//
//
//
//                // photos 필드 확인 후 photo_reference 추출
//                String googlePhotoUrl = null;
//                JsonNode photosNode = place.path("photos");
//                if (photosNode != null && photosNode.isArray() && photosNode.size() > 0) {
//                    String photoReference = photosNode.get(0).path("photo_reference").asText();
//                    googlePhotoUrl = getGooglePhoto(photoReference);
//                }
//
//                PlaceInfoDto placeInfoDto =PlaceInfoDto.builder()
//                        .name(name)
//                        .address(address)
//                        .lat(lat)
//                        .lng(lng)
//                        .rating(rating)
//                        .userRatingsTotal(userRatingsTotal)
//                        .type(type)
//                        .photoUrl(googlePhotoUrl)
//                        .build();
//
//                placeInfoList.add(placeInfoDto);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return placeInfoList;
//    }

    // Google Maps API에서 photo_reference를 가져오는 메서드

    public GooglePhotoResponseDto getGooglePhotoReference(String placeName) {

        //Redis 키는 지역 이름으로 지정
        String rediskey = String.format("PlacePhoto:%s",
                placeName != null ? placeName.trim() : "unknown");
        // Redis에서 데이터가 존재하는지 확인
        if (googlePhotoResponseService.exists(rediskey)) {
            System.out.println("있다ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ");
            // Redis에서 데이터 가져오기
            return googlePhotoResponseService.getData(rediskey);
        }

        try {
            String url = "https://maps.googleapis.com/maps/api/place/textsearch/json";
            String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("query", placeName)
                    .queryParam("key", apiKey)
                    .build()
                    .toUriString();

            // API 요청 보내기
            String response = restTemplate.getForObject(requestUrl, String.class);

            JsonNode resultNode = objectMapper.readTree(response)
                    .path("results")
                    .get(0)
                    .path("photos")
                    .get(0)
                    .path("photo_reference");

            String photoReference = resultNode.asText();
            String googlePhotoUrl = getGooglePhoto(photoReference);

            GooglePhotoResponseDto googlePhotoResponseDto = GooglePhotoResponseDto.builder()
                    .name(placeName)
                    .photoUrl(googlePhotoUrl)
                    .build();

            googlePhotoResponseService.saveData(rediskey,googlePhotoResponseDto);
            return googlePhotoResponseDto;

        } catch (Exception e) {
            // 예외 발생 시 "이미지 없음" 메시지로 대체
            GooglePhotoResponseDto errorMessage = GooglePhotoResponseDto.builder()
                    .name("이미지 없음")
                    .build();

            return errorMessage;
        }
    }

    // Google Maps API로부터 photo_reference를 사용해 사진 URL을 가져오는 메서드
    public String getGooglePhoto(String photoReference) {
        String url = "https://maps.googleapis.com/maps/api/place/photo";
        String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("key", apiKey)
                .queryParam("photo_reference", photoReference)
                .queryParam("maxwidth", "200")
                .queryParam("maxheight", "150")
                .build()
                .toUriString();
        return requestUrl;
    }


}

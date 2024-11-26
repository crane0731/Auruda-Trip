package com.sw.AurudaTrip.service;

import com.sw.AurudaTrip.domain.Category;
import com.sw.AurudaTrip.domain.TravelPlace;
import com.sw.AurudaTrip.dto.itinerary.PlaceDto;
import com.sw.AurudaTrip.dto.place.CategoryRequestDto;
import com.sw.AurudaTrip.dto.place.CityRequestDto;
import com.sw.AurudaTrip.dto.place.PlaceListByTravelCountResponseDto;
import com.sw.AurudaTrip.dto.place.PlaceResponseDto;
import com.sw.AurudaTrip.repository.TravelPlaceRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TravelPlaceService {

    private final TravelPlaceRepository travelPlaceRepository;

    @Transactional
    public TravelPlace savePlace(PlaceDto placeDto){

        TravelPlace place = TravelPlace.builder()
                .name(placeDto.getName())
                .description(placeDto.getDescription())
                .city(placeDto.getCity())
                .address(placeDto.getAddress())
                .travelCount(0L)
                .category(Category.fromKoreanName(placeDto.getCategory()))
                .lat(placeDto.getLat())
                .lng(placeDto.getLng())
                .photoUrl(placeDto.getPhoto_url())
                .build();



        //이름과 주소로 여행지가 존재하는지 확인
        Optional<TravelPlace> existingPlace = travelPlaceRepository.findByNameAndAddress(placeDto.getName(), placeDto.getAddress());

        //여행지가 없을 경우에만 저장
        if(existingPlace.isEmpty()){
            travelPlaceRepository.save(place);
            return place;
        }
        else{
            existingPlace.get().addTravelCount();
            return existingPlace.get();
        }

    }

    public TravelPlace getPlaceById(Long id){
        return travelPlaceRepository.findById(id).orElseThrow(()->new RuntimeException("여행 장소가 없습니다."));
    }


    //travelcount가 높은 순서대로 리스트 조회하는 서비스
    public List<PlaceListByTravelCountResponseDto> getPlaceListByTravelCount(){
        List<TravelPlace> places = travelPlaceRepository.findTop20ByOrderByTravelCountDesc();

        return places.stream().map(place->PlaceListByTravelCountResponseDto.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .city(place.getCity())
                .Category(place.getCategory().getKoreanName())
                .travelCount(place.getTravelCount())
                .lat(place.getLat())
                .lng(place.getLng())
                .photoUrl(place.getPhotoUrl())
                .build()).toList();

    }

    //Category별로 travelcount가 높은 순서대로 리스트 조회하는 서비스
    public List<PlaceListByTravelCountResponseDto> getPlaceListByTravelCountByCategory(CategoryRequestDto dto){

        List<TravelPlace> places = travelPlaceRepository.findTop20ByCategoryOrderByTravelCountDesc(Category.fromKoreanName(dto.getCategory()));



        return places.stream().map(place->PlaceListByTravelCountResponseDto.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .city(place.getCity())
                .Category(place.getCategory().getKoreanName())
                .travelCount(place.getTravelCount())
                .lat(place.getLat())
                .lng(place.getLng())
                .photoUrl(place.getPhotoUrl())
                .build()).toList();
    }


    //지역별로 travelcount가 높은 순서대로 리스트 조회하는 서비스
    public List<PlaceListByTravelCountResponseDto> getPlaceListByTravelCountByCity(CityRequestDto dto){

        List<TravelPlace> places = travelPlaceRepository.findTop20ByCityOrderByTravelCountDesc(dto.getCity());


        return places.stream().map(place->PlaceListByTravelCountResponseDto.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .city(place.getCity())
                .Category(place.getCategory().getKoreanName())
                .travelCount(place.getTravelCount())
                .lat(place.getLat())
                .lng(place.getLng())
                .photoUrl(place.getPhotoUrl())
                .build()).toList();
    }

    //장소 상세 조회 서비스
    public PlaceResponseDto getPlace(Long placeId){
        TravelPlace place = travelPlaceRepository.findById(placeId).orElseThrow(() -> new RuntimeException("여행 장소가 없습니다."));

        return PlaceResponseDto.builder()
                .id(place.getId())
                .name(place.getName())
                .city(place.getCity())
                .address(place.getAddress())
                .travelCount(place.getTravelCount())
                .category(place.getCategory().getKoreanName())
                .description(place.getDescription())
                .lat(place.getLat())
                .lng(place.getLng())
                .photoUrl(place.getPhotoUrl())
                .build();
    }

    //이름과 주소로 장소데이터 반환
    public TravelPlace getTravelPlaceByNameAndAddress(String name, String address){
        return travelPlaceRepository.findByNameAndAddress(name, address).orElse(null);
    }

}

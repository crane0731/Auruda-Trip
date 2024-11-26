package com.sw.AurudaTrip.service;

import com.sw.AurudaTrip.domain.*;
import com.sw.AurudaTrip.dto.itinerary.PlaceDto;
import com.sw.AurudaTrip.dto.storage.EmptyStorageRequestDto;
import com.sw.AurudaTrip.dto.storage.StorageListResponseDto;
import com.sw.AurudaTrip.dto.storage.StoragePlaceDto;
import com.sw.AurudaTrip.dto.storage.StorageResponseDto;
import com.sw.AurudaTrip.repository.StorageRespository;
import com.sw.AurudaTrip.repository.StorageTravelPlaceRepository;
import com.sw.AurudaTrip.repository.TravelPlaceRepository;
import com.sw.AurudaTrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StorageService {

    private final StorageRespository storageRespository;
    private final UserRepository userRepository;
    private final StorageTravelPlaceRepository storageTravelPlaceRepository;
    private final TravelPlaceRepository travelPlaceRepository;
    private final TravelPlaceService travelPlaceService;

    //빈 저장소 등록 서비스
    public void createEmptyStorage(Long userId, EmptyStorageRequestDto emptyStorageRequestDto) {

        //유저 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("회원을 찾지 못했습니다."));

        Storage storage = Storage.builder()
                .storageName(emptyStorageRequestDto.getStorageName())
                .user(user)
                .build();

        storageRespository.save(storage);

    }


    //저장소에 여행장소 추가 서비스
    public void addPlace(Long storageId, PlaceDto placeDto) {


        //저장소 조회하기
        Storage storage = storageRespository.findById(storageId).orElseThrow(() -> new RuntimeException("저장소가 존재하지 않습니다."));


            //추가하기
            TravelPlace place = travelPlaceService.savePlace(placeDto);

            //StorageTravelPlaceId 생성하기
            StorageTravelPlaceId storageTravelPlaceId = StorageTravelPlaceId.builder()
                    .storageId(storage.getId())
                    .travelPlaceId(place.getId())
                    .build();

            //StorageTravelPlace 생성하기
            StorageTravelPlace storageTravelPlace = StorageTravelPlace.builder()
                    .id(storageTravelPlaceId)
                    .storage(storage)
                    .travelPlace(place)
                    .build();

        StorageTravelPlace saveStorageTravelPlace = storageTravelPlaceRepository.save(storageTravelPlace);

        //storage의 StorageTravelPlaces에 추가하기
            storage.addStorageTravelPlace(saveStorageTravelPlace);


    }

    @Transactional
    //저장소에 여행장소 삭제하는 서비스
    public void removePlace(Long storageId, Long travelPlaceId) {

        //저장소 조회하기
        Storage storage = storageRespository.findById(storageId).orElseThrow(() -> new RuntimeException("저장소가 존재하지 않습니다."));

        //여행장소 조회하기
        TravelPlace place = travelPlaceRepository.findById(travelPlaceId).orElseThrow(() -> new RuntimeException("여행장소가 존재하지 않습니다."));

        //저장소-여행장소 아이디 생성하기
        StorageTravelPlaceId storageTravelPlaceId = StorageTravelPlaceId.builder()
                    .travelPlaceId(place.getId())
                    .storageId(storage.getId())
                    .build();

        //저장소-여행장소 조회하기
        StorageTravelPlace storageTravelPlace = storageTravelPlaceRepository.findById(storageTravelPlaceId);


        //storage의 StorageTravelPlaces에서 제거하기
        storage.removeStorageTravelPlace(storageTravelPlace);

    }

    //저장소 리스트 조회
    public List<StorageListResponseDto> getAllStoragesByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("회원을 찾지 못했습니다."));
        List<Storage> storages = storageRespository.findAllByUser(user);

        return storages.stream()
                .map(storage -> new StorageListResponseDto(storage.getId(),storage.getStorageName()))
                .collect(Collectors.toList());
    }

    //저장소 조회 서비스
    public StorageResponseDto getStorage(Long storageId) {

        //저장소 조회하기
        Storage storage = storageRespository.findById(storageId).orElseThrow(() -> new RuntimeException("저장소가 존재하지 않습니다."));

        //저장소-여행장소 조회하기
        List<StorageTravelPlace> storageTravelPlaces = storage.getStorageTravelPlaces();

        List<StoragePlaceDto> storagePlaceDtoList = new ArrayList<>();

        for (StorageTravelPlace storageTravelPlace : storageTravelPlaces) {
            Long travelPlaceId = storageTravelPlace.getTravelPlace().getId();

            TravelPlace travelPlace = travelPlaceRepository.findById(travelPlaceId).orElseThrow(() -> new RuntimeException("여행장소가 존재하지 않습니다."));


            //여행 장소Dto 생성
            StoragePlaceDto placeDto = StoragePlaceDto.builder()
                    .id(travelPlace.getId())
                    .name(travelPlace.getName())
                    .city(travelPlace.getCity())
                    .address(travelPlace.getAddress())
                    .category(travelPlace.getCategory().getKoreanName())
                    .description(travelPlace.getDescription())
                    .lat(travelPlace.getLat())
                    .lng(travelPlace.getLng())
                    .photo_url(travelPlace.getPhotoUrl())
                    .build();

            storagePlaceDtoList.add(placeDto);

        }
            StorageResponseDto storageResponseDto = StorageResponseDto.builder()
                    .userEmail(storage.getUser().getEmail())
                    .userName(storage.getUser().getNickname())
                    .storageName(storage.getStorageName())
                    .createdAt(storage.getCreatedAt())
                    .places(storagePlaceDtoList)
                    .build();

        return storageResponseDto;

        }

    //저장소 삭제 서비스
    public void deleteStorage(Long storageId) {
        Storage storage = storageRespository.findById(storageId).orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        storageRespository.deleteById(storageId);
    }


}

package com.sw.AurudaTrip.service.redis;

import com.sw.AurudaTrip.dto.google.GooglePhotoResponseDto;
import com.sw.AurudaTrip.dto.gpt.GptPlaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GooglePhotoResponseService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveData(String key, GooglePhotoResponseDto dto) {

        // Redis에 리스트로 저장
        redisTemplate.opsForValue().set(key, dto);
    }

    public GooglePhotoResponseDto getData(String key) {
        return (GooglePhotoResponseDto) redisTemplate.opsForValue().get(key);
    }


    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

}

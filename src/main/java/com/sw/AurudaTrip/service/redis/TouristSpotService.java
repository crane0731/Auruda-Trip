package com.sw.AurudaTrip.service.redis;

import com.sw.AurudaTrip.dto.publicdata.TouristSpotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TouristSpotService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveData(String key, List<TouristSpotDto> dtos) {

        // Redis에 리스트로 저장
        redisTemplate.opsForValue().set(key, dtos);
    }

    public List<TouristSpotDto> getData(String key) {
        return (List<TouristSpotDto>) redisTemplate.opsForValue().get(key);
    }


    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}

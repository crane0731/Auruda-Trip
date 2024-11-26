package com.sw.AurudaTrip.controller;

import com.sw.AurudaTrip.dto.location.Location;
import com.sw.AurudaTrip.service.distance.DistanceMatrixService;
import com.sw.AurudaTrip.service.distance.NearestNeighborService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RouteController {
    private final DistanceMatrixService distanceMatrixService;
    private final NearestNeighborService nearestNeighborService;

    @GetMapping("/shortest-path")
    public List<String> getShortestPath() {
        // 장소 데이터 준비
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(37.5705, 126.9835, "1"));
        locations.add(new Location(37.5700, 126.9780, "강남"));
        locations.add(new Location(37.5600, 126.9750, "2"));

        // 거리 행렬 생성
        double[][] distanceMatrix = distanceMatrixService.createDistanceMatrix(locations);

        // 최단 경로 계산
        List<Integer> path = nearestNeighborService.findShortestPath(distanceMatrix);

        // 경로 반환 (장소 이름으로 변환)
        List<String> route = new ArrayList<>();
        for (int index : path) {
            route.add(locations.get(index).getName());
        }

        return route;
    }
}
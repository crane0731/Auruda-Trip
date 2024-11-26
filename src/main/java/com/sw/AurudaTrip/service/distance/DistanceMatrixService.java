package com.sw.AurudaTrip.service.distance;

import com.sw.AurudaTrip.dto.location.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

//거리 행렬을 생성하는 클래스
@RequiredArgsConstructor
@Component
public class DistanceMatrixService {

    private final DistanceCalculator distanceCalculator;

    public double[][] createDistanceMatrix(List<Location> locations){
        int n = locations.size();
        double[][] distanceMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i!=j){
                    Location loc1 = locations.get(i);
                    Location loc2 = locations.get(j);
                    distanceMatrix[i][j]=distanceCalculator.calculateDistance(loc1.getLatitude(),loc1.getLongitude(),loc2.getLatitude(),loc2.getLongitude());
                }
            }
        }
        return distanceMatrix;
    }

}

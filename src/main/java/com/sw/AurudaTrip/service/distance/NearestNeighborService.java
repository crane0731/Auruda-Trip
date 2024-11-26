package com.sw.AurudaTrip.service.distance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NearestNeighborService {


    public List<Integer> findShortestPath(double[][] distanceMatrix) {
        int n = distanceMatrix.length;
        boolean[] visited = new boolean[n];
        List<Integer> path = new ArrayList<>();

        int current =0; //출발지
        visited[current] = true;
        path.add(current);
        while (path.size() < n) {
            double minDistance = Double.MAX_VALUE;
            int nextCity=-1;

            for (int i = 0; i < n; i++) {
                if(!visited[i] && distanceMatrix[current][i] < minDistance) {
                    minDistance = distanceMatrix[current][i];
                    nextCity = i;
                }
            }
            path.add(nextCity);
            visited[nextCity] = true;
            current = nextCity;
        }
        return path;
    }

}

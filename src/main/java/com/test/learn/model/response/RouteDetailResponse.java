package com.test.learn.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDetailResponse {
    private String sourceStation;
    private String destinationStation;
    private int noOfStations;
    private String routePath;
    private String routeDescription;
    private String routeTime;
}

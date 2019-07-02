package com.test.learn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RouteData {
    private List<Station> routeStationList;
    private int routeCost;
}

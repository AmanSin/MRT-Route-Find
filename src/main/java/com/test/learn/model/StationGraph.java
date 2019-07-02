package com.test.learn.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
/**
 * Model class to store the entire station data into Adjacency list representation where vertex is the stationName.
 */
public class StationGraph {
    private int noOfStation;
    private Map<String, List<Station>> stationNameToNodeAdjList;

    public StationGraph(Set<String> uniqueStationSet) {
        this.noOfStation = uniqueStationSet.size();
        stationNameToNodeAdjList = new HashMap<>();
        for (String station : uniqueStationSet) {
            stationNameToNodeAdjList.put(station, new ArrayList<>());
        }
    }

    public void addStationNode(String srcName, Station destStation) {
        stationNameToNodeAdjList.get(srcName).add(destStation);
    }
}


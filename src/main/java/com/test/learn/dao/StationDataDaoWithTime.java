package com.test.learn.dao;

import com.test.learn.model.RouteData;
import com.test.learn.model.Station;
import com.test.learn.service.StationDataManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
/**
 * Given source and destination station, compute the path between them based on the travelling time between stations.
 */
public class StationDataDaoWithTime {
    private final StationDataManager stationDataManager;

    public StationDataDaoWithTime(StationDataManager stationDataManager) {
        this.stationDataManager = stationDataManager;
    }

    public List<RouteData> getPathBetweenTwoNodesWithTime(String srcStation, String destStation, LocalDateTime currentTime) {
        Map<String, Boolean> isVisited = new HashMap<>();
        for (String stationName : stationDataManager.getStationNameSet()) {
            isVisited.put(stationName, false);
        }

        //TODO Case for which the source station is Junction, and we need to choose the right line to start, this can
        // be easily done bu iterating over all the stations in stationNameToStationDataMap in beginning.

        List<Station> pathList = new ArrayList<>();
        Station src = stationDataManager.getStationNameToStationDataMap().get(srcStation).get(0);
        Station dest = stationDataManager.getStationNameToStationDataMap().get(destStation).get(0);

        pathList.add(src);

        List<RouteData> routeDetailList = new ArrayList<>();

        AtomicInteger maxTime = new AtomicInteger(Integer.MAX_VALUE);
        findAllPathsUtilWithTime(src, dest, isVisited, pathList, 0, maxTime, currentTime, routeDetailList);

        return routeDetailList;
    }


    private void findAllPathsUtilWithTime(Station srcStation, Station destStation, Map<String, Boolean> isVisited,
                                          List<Station> localPathList, int currentTotalTime, AtomicInteger maxTime,
                                          LocalDateTime currentTime, List<RouteData> routeDataList) {

        // Returning if traversing path taking longer time than path find earlier
        if (currentTotalTime >= maxTime.get()) {
            return;
        }
        // Mark the current node
        isVisited.put(srcStation.getStationName(), true);

        if (srcStation.getStationName().equals(destStation.getStationName())) {
            RouteData routeData = new RouteData(new ArrayList<>(localPathList), currentTotalTime);
            routeDataList.add(routeData);

            log.debug("Paths is : {}", localPathList);
            log.debug("currentTotalTime is :  {}", currentTotalTime);

            // if match found then no need to traverse more till depth
            isVisited.put(srcStation.getStationName(), false);
            if (currentTotalTime < maxTime.get()) {
                maxTime.set(currentTotalTime);
            }
            return;
        }

        // Recur for all the vertices adjacent to current vertex
        for (Station stNode :
                stationDataManager.getInitialStationGraph().getStationNameToNodeAdjList().get(srcStation.getStationName())) {
            if (stationDataManager.isActiveStation(stNode, currentTime)) {
                if (!isVisited.get(stNode.getStationName())) {
                    // store current node in localPathList
                    localPathList.add(stNode);

                    int stationTravelTime = stationDataManager.getTravelTimeBetweenNeighbourStation(srcStation,
                            stNode, currentTime);

                    findAllPathsUtilWithTime(stNode, destStation, isVisited, localPathList,
                            currentTotalTime + stationTravelTime, maxTime, currentTime.plusMinutes(stationTravelTime)
                            , routeDataList);

                    // remove current node in localPathList
                    localPathList.remove(stNode);
                }
            }
        }

        // Mark the current node unvisited
        isVisited.put(srcStation.getStationName(), false);
    }
}

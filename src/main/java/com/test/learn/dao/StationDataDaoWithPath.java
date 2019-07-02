package com.test.learn.dao;

import com.test.learn.model.RouteData;
import com.test.learn.model.Station;
import com.test.learn.service.StationDataManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
/**
 * Given source and destination station, compute the path between them based on the number of intermediate stations.
 */
public class StationDataDaoWithPath {
    private final StationDataManager stationDataManager;

    public StationDataDaoWithPath(StationDataManager stationDataManager) {
        this.stationDataManager = stationDataManager;
    }

    public List<RouteData> getPathBetweenTwoNodes(String srcStation, String destStation) {
        Map<String, Boolean> isVisited = new HashMap<>();
        for (String stationName : stationDataManager.getStationNameSet()) {
            isVisited.put(stationName, false);
        }
        List<Station> pathList = new ArrayList<>();
        Station src = stationDataManager.getStationNameToStationDataMap().get(srcStation).get(0);
        Station dest = stationDataManager.getStationNameToStationDataMap().get(destStation).get(0);

        pathList.add(src);

        List<RouteData> routeDetailList = new ArrayList<>();
        AtomicInteger maxPathLen = new AtomicInteger(stationDataManager.getStationNameSet().size());
        findAllPathsUtil(src, dest, isVisited, pathList, 1, maxPathLen, routeDetailList);
        return routeDetailList;
    }


    private void findAllPathsUtil(Station srcStation, Station destStation, Map<String, Boolean> isVisited,
                                  List<Station> localPathList, int currentPathLength, AtomicInteger maxPathLength,
                                  List<RouteData> routeDataList) {

        // Returning if traversing path taking longer route than path find earlier
        if (currentPathLength >= maxPathLength.get()) {
            return;
        }
        // Mark the current node
        isVisited.put(srcStation.getStationName(), true);

        if (srcStation.getStationName().equals(destStation.getStationName())) {
            RouteData routeData = new RouteData(new ArrayList<>(localPathList), currentPathLength);
            routeDataList.add(routeData);

            log.debug("Paths is : {}", localPathList);
            log.debug("currentPathLength is :  {}", currentPathLength);

            // if match found then no need to traverse more till depth
            isVisited.put(srcStation.getStationName(), false);

            if (localPathList.size() < maxPathLength.get()) {
                maxPathLength.set(localPathList.size());
            }
            return;
        }

        // Recur for all the vertices adjacent to current vertex
        for (Station stNode :
                stationDataManager.getInitialStationGraph().getStationNameToNodeAdjList().get(srcStation.getStationName())) {
            if (!isVisited.get(stNode.getStationName())) {
                // store current node in localPathList
                localPathList.add(stNode);
                findAllPathsUtil(stNode, destStation, isVisited, localPathList, currentPathLength + 1, maxPathLength
                        , routeDataList);

                // remove current node in localPathList
                localPathList.remove(stNode);
            }
        }

        // Mark the current node
        isVisited.put(srcStation.getStationName(), false);
    }
}

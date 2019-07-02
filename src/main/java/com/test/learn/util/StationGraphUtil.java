package com.test.learn.util;

import com.test.learn.model.Station;
import com.test.learn.model.StationGraph;
import com.test.learn.model.TrainLineData;
import com.test.learn.util.enums.TrainLine;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility Class to create the StationGraph
 */
public class StationGraphUtil {
    public StationGraph createStationGraph(Set<String> uniqueStationSet, Map<TrainLine, TrainLineData> trainLineDataMap) {
        StationGraph stationGraph = new StationGraph(uniqueStationSet);

        for (Map.Entry<TrainLine, TrainLineData> entry : trainLineDataMap.entrySet()) {
            TrainLineData trainLineData = entry.getValue();
            List<Station> stationListInOrder = trainLineData.getStationListInOrder();

            for (int i = 0; i < stationListInOrder.size() - 1; i++) {
                Station firstStation = stationListInOrder.get(i);
                Station nextStation = stationListInOrder.get(i + 1);
                stationGraph.addStationNode(firstStation.getStationName(), nextStation);
                stationGraph.addStationNode(nextStation.getStationName(), firstStation);
            }
        }
        return stationGraph;
    }
}

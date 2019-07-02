package com.test.learn.util;

import com.test.learn.model.Station;
import com.test.learn.model.TrainLineData;
import com.test.learn.util.enums.TimePeriod;
import com.test.learn.util.enums.TrainLine;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
/**
 * Utility class for managing data related to TrainLine of MRT
 */
public class TrainLineDataUtil {
    public Map<TrainLine, TrainLineData> getTrainLineDataMap(List<Station> stationList) {
        Map<TrainLine, TrainLineData> trainLineDataMap = new HashMap<>();
        for (TrainLine tl : TrainLine.values()) {
            trainLineDataMap.put(tl, new TrainLineData(tl));
        }
        populateTravelTimeByTimePeriodMap(trainLineDataMap);
        populateStationListInOrder(trainLineDataMap, stationList);

        return trainLineDataMap;
    }

    // TODO Hardcoding this information, we can read this later from config files.
    private void populateTravelTimeByTimePeriodMap(Map<TrainLine, TrainLineData> trainLineDataMap) {

        TrainLineData trainLineData = trainLineDataMap.get(TrainLine.NS);
        trainLineData.setTravelTimeByTimePeriodMap(getTimePeriodIntegerMap(12, 10, 10));

        trainLineData = trainLineDataMap.get(TrainLine.EW);
        trainLineData.setTravelTimeByTimePeriodMap(getTimePeriodIntegerMap(10, 10, 10));

        trainLineData = trainLineDataMap.get(TrainLine.CG);
        trainLineData.setTravelTimeByTimePeriodMap(getTimePeriodIntegerMap(10, -1, 10));

        trainLineData = trainLineDataMap.get(TrainLine.NE);
        trainLineData.setTravelTimeByTimePeriodMap(getTimePeriodIntegerMap(12, 10, 10));

        trainLineData = trainLineDataMap.get(TrainLine.CC);
        trainLineData.setTravelTimeByTimePeriodMap(getTimePeriodIntegerMap(10, 10, 10));

        trainLineData = trainLineDataMap.get(TrainLine.CE);
        trainLineData.setTravelTimeByTimePeriodMap(getTimePeriodIntegerMap(10, -1, 10));

        trainLineData = trainLineDataMap.get(TrainLine.DT);
        trainLineData.setTravelTimeByTimePeriodMap(getTimePeriodIntegerMap(10, -1, 8));

        trainLineData = trainLineDataMap.get(TrainLine.TE);
        trainLineData.setTravelTimeByTimePeriodMap(getTimePeriodIntegerMap(10, 8, 8));
    }

    private Map<TimePeriod, Integer> getTimePeriodIntegerMap(int peak, int night, int nonPeak) {
        Map<TimePeriod, Integer> travelTimeByTimePerioMap = new HashMap<>();
        travelTimeByTimePerioMap.put(TimePeriod.PEAK, peak);
        travelTimeByTimePerioMap.put(TimePeriod.NIGHT, night);
        travelTimeByTimePerioMap.put(TimePeriod.NON_PEAK, nonPeak);
        return travelTimeByTimePerioMap;
    }

    //Populating list of stations for each unique line in the MRT system
    private void populateStationListInOrder(Map<TrainLine, TrainLineData> trainLineDataMap, List<Station> stationList) {
        for (Station station : stationList) {
            TrainLineData currentTrainLineData = trainLineDataMap.get(station.getTrainLine());
            if (currentTrainLineData.getStationListInOrder() == null) {
                currentTrainLineData.setStationListInOrder(new ArrayList<>());
            }
            currentTrainLineData.getStationListInOrder().add(station);
        }

        // Sorting stations for every trainLine by the station code
        for (Map.Entry<TrainLine, TrainLineData> entry : trainLineDataMap.entrySet()) {
            List<Station> stList = entry.getValue().getStationListInOrder();

            List<Station> sortedList = stList.stream()
                    .sorted(Comparator.comparing(Station::getStationNumber))
                    .collect(Collectors.toList());

            entry.getValue().setStationListInOrder(sortedList);
        }
    }

    public Set<String> findAllTrainLines(List<Station> stationList) {
        Set<String> trainLines = new HashSet<>();
        for (Station st : stationList) {
            trainLines.add(st.getStationCode().substring(0, 2));
        }
        log.info("TrainLines: {}", trainLines);
        return trainLines;
    }
}

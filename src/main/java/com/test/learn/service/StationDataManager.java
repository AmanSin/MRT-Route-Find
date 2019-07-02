package com.test.learn.service;

import com.test.learn.model.Station;
import com.test.learn.model.StationGraph;
import com.test.learn.model.TrainLineData;
import com.test.learn.util.Constant;
import com.test.learn.util.StationDataUtil;
import com.test.learn.util.StationGraphUtil;
import com.test.learn.util.TimePeriodUtil;
import com.test.learn.util.TrainLineDataUtil;
import com.test.learn.util.enums.TimePeriod;
import com.test.learn.util.enums.TrainLine;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
@Getter
@Slf4j
/**
 * Class managing the relation between station and TrainLine, providing utility methods to find if the station
 * isActive or travelTimeBetweenNeighbourStation
 */
public class StationDataManager {

    private final List<Station> stationList;
    private final Set<String> stationNameSet;
    private final Map<String, List<Station>> stationNameToStationDataMap;
    private final StationGraph initialStationGraph;
    private final TimePeriodUtil timePeriodUtil;
    private final Map<TrainLine, TrainLineData> trainLineDataMap;

    public StationDataManager(String stationDataFile, StationDataUtil stationDataUtil,
                              TrainLineDataUtil trainLineDataUtil, StationGraphUtil stationGraphUtil,
                              TimePeriodUtil timePeriodUtil) {
        stationList = stationDataUtil.getStationListFromFile(stationDataFile);
        log.debug("Total loaded stations are: {}", stationList.size());
        log.info("Total loaded stations are: {}", stationList.size());

        stationNameSet = stationDataUtil.getUniqueStationName(stationList);
        stationNameToStationDataMap = stationDataUtil.stationNameToStationDataMap(stationList);

        //trainLineDataUtil.findAllTrainLines(stationList);
        trainLineDataMap = trainLineDataUtil.getTrainLineDataMap(stationList);
        log.debug("trainLineDataMap is : {}", trainLineDataMap);

        initialStationGraph = stationGraphUtil.createStationGraph(stationNameSet, trainLineDataMap);
        log.debug("initialStationGraph is: {}", initialStationGraph);

        this.timePeriodUtil = timePeriodUtil;

        //getPathBetweenTwoNodesWithTime("Holland Village", "Bugis", LocalDateTime.parse("2019-01-31T16:00"));
        //getPathBetweenTwoNodesWithTime("Boon Lay", "Little India", LocalDateTime.parse("2019-01-31T06:00"));
    }

    public boolean isActiveStation(Station destStation, LocalDateTime localDateTime) {
        LocalDate todayDay = LocalDate.now();
        TimePeriod currentTimePeriod = timePeriodUtil.getTimePeriod(localDateTime);
        int travelTime =
                trainLineDataMap.get(destStation.getTrainLine()).getTravelTimeByTimePeriodMap().get(currentTimePeriod);
        return (destStation.getOpeningDate().isBefore(todayDay))
                && (travelTime > 0);
    }

    public int getTravelTimeBetweenNeighbourStation(Station src, Station dest, LocalDateTime localDateTime) {
        TimePeriod currentTimePeriod = timePeriodUtil.getTimePeriod(localDateTime);
        int time = trainLineDataMap.get(dest.getTrainLine()).getTravelTimeByTimePeriodMap().get(currentTimePeriod);

        // Adding extra cost for switching lines
        if (!src.getTrainLine().equals(dest.getTrainLine())) {
            time = time + Constant.timePeriodLineChangeCost.get(currentTimePeriod);
        }
        return time;
    }

    public Station getStationFromTrainLine(TrainLine tl, String stationName) {
        TrainLineData trainLineData = trainLineDataMap.get(tl);
        for (Station station : trainLineData.getStationListInOrder()) {
            if (station.getStationName().equals(stationName)) {
                return station;
            }
        }
        return null;
    }

    public boolean isValidStation(String station) {
        return stationNameSet.contains(station);
    }
}

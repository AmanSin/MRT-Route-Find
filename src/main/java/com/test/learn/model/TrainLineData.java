package com.test.learn.model;

import com.test.learn.util.enums.TimePeriod;
import com.test.learn.util.enums.TrainLine;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TrainLineData {
    private TrainLine trainLineName;
    //private int noOfStations;
    private List<Station> stationListInOrder;

    // if the value is -1, it means that the line is not function for that TimePeriod
    private Map<TimePeriod, Integer> travelTimeByTimePeriodMap;

    public TrainLineData(TrainLine trainLineName) {
        this.trainLineName = trainLineName;
    }
}

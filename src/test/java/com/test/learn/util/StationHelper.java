package com.test.learn.util;

import com.test.learn.model.Station;

import java.io.File;
import java.util.List;

public class StationHelper {

    private final String DATA_FILE_PATH  = "src/test/resources/StationMap.csv";

    public List<Station> getSampleStationList() {
        StationDataUtil stationDataUtil = new StationDataUtil();
        return stationDataUtil.getStationListFromFile(new File(DATA_FILE_PATH).getAbsolutePath());
    }
}

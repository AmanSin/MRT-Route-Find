package com.test.learn.util;

import com.test.learn.model.Station;
import com.test.learn.util.enums.TrainLine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

@Slf4j
public class StationDataUtilTest {

    private StationDataUtil stationDataUtil;
    private String filePath;
    private final String DATA_FILE_PATH  = "src/test/resources/StationMap.csv";

    @Before
    public void setup() {
        stationDataUtil = new StationDataUtil();
        File file = new File(DATA_FILE_PATH);
        filePath = file.getAbsolutePath();
    }

    @Test
    public void testGetActiveStationListFromFile_ValidStationCount() {
        List<Station> stationList = stationDataUtil.getStationListFromFile(filePath);
        Assert.assertEquals(166, stationList.size());
    }

    @Test
    public void testGetActiveStationListFromFile_ValidStationData() {
        List<Station> stationList = stationDataUtil.getStationListFromFile(filePath);
        Station ns1Station =
                stationList.stream().filter(station -> "NS1".equals(station.getStationCode())).findAny().orElse(null);
        Assert.assertNotNull(ns1Station);
        Assert.assertEquals("Jurong East", ns1Station.getStationName());
        Assert.assertEquals(TrainLine.NS, ns1Station.getTrainLine());

    }
}

package com.test.learn.service;

import com.test.learn.dao.StationDataDaoWithPath;
import com.test.learn.dao.StationDataDaoWithTime;
import com.test.learn.model.RouteData;
import com.test.learn.model.Station;
import com.test.learn.model.request.RouteInput;
import com.test.learn.model.response.RouteDetailResponse;
import com.test.learn.model.response.RouteList;
import com.test.learn.util.ResponseMapper;
import com.test.learn.util.enums.TrainLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RouteServiceTest {

    private RouteService routeService;
    private StationDataDaoWithTime stationDataDao;
    private StationDataDaoWithPath stationDataDaoWithPath;
    private ResponseMapper responseMapper;

    @Before
    public void setup() {
        stationDataDao = Mockito.mock(StationDataDaoWithTime.class);
        stationDataDaoWithPath = Mockito.mock(StationDataDaoWithPath.class);
        responseMapper = Mockito.mock(ResponseMapper.class);
        routeService = new RouteService(stationDataDao, stationDataDaoWithPath, responseMapper);
    }

    @Test
    public void getRouteListByTime_Valid() {
        //ARRANGE
        RouteData routeData = new RouteData(Arrays.asList(new Station("NS1", "Jurong East", LocalDate.now(), TrainLine.NS, 1)
                , new Station("NS2", "Bukit Batok", LocalDate.now(), TrainLine.NS, 2)), 10);
        List<RouteData> routeDataList = Arrays.asList(routeData);
        Mockito.when(stationDataDao.getPathBetweenTwoNodesWithTime(ArgumentMatchers.any(),
                ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(routeDataList);

        RouteDetailResponse routeDetailResponse = new RouteDetailResponse("Jurong East", "Bukit Batok", 2, "NS1, NS2"
                , "Take NS Line from Jurong East to Bukit Batok", "10");
        RouteList routeList = new RouteList(Arrays.asList(routeDetailResponse));
        Mockito.when(responseMapper.createResponseData(routeDataList, true)).thenReturn(routeList);
        RouteInput routeInput = new RouteInput("Jurong East", "Bukit Batok", "2019-01-31T06:05");

        //ACT
        RouteList result = routeService.getRouteListByTime(routeInput);

        // ASSERT
        verify(stationDataDao, times(1)).getPathBetweenTwoNodesWithTime(ArgumentMatchers.any(),
                ArgumentMatchers.any(), ArgumentMatchers.any());

        verify(responseMapper, times(1)).createResponseData(routeDataList, true);

        Assert.assertEquals("Response source station name is invalid", "Jurong East",
                result.getRouteList().get(0).getSourceStation());

        Assert.assertEquals("Response route time is invalid", "10",
                result.getRouteList().get(0).getRouteTime());
    }
}

package com.test.learn.service;

import com.test.learn.dao.StationDataDaoWithPath;
import com.test.learn.dao.StationDataDaoWithTime;
import com.test.learn.model.RouteData;
import com.test.learn.model.request.RouteInput;
import com.test.learn.model.response.RouteList;
import com.test.learn.util.ResponseMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
/**
 * Service class, which interacts with DAO and Controller to do the processing of user input
 */
public class RouteService {
    private final StationDataDaoWithTime stationDataDaoWithTime;
    private final StationDataDaoWithPath stationDataDaoWithPath;
    private final ResponseMapper responseMapper;


    public RouteService(StationDataDaoWithTime stationDataDao, StationDataDaoWithPath stationDataDaoWithPath, ResponseMapper responseMapper) {
        this.stationDataDaoWithTime = stationDataDao;
        this.stationDataDaoWithPath = stationDataDaoWithPath;
        this.responseMapper = responseMapper;
    }

    public RouteList getRouteListByTime(RouteInput routeInput) {
        List<RouteData> routeDataList = stationDataDaoWithTime.getPathBetweenTwoNodesWithTime(routeInput.getSourceStation(),
                routeInput.getDestinationStation(), LocalDateTime.parse(routeInput.getStartingTime()));
        RouteList routeList = responseMapper.createResponseData(routeDataList, true);
        return routeList;
    }

    public RouteList getRouteList(RouteInput routeInput) {
        List<RouteData> routeDataList = stationDataDaoWithPath.getPathBetweenTwoNodes(routeInput.getSourceStation(),
                routeInput.getDestinationStation());
        RouteList routeList = responseMapper.createResponseData(routeDataList, false);
        return routeList;
    }
}

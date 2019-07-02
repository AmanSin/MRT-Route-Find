package com.test.learn.util;

import com.test.learn.model.RouteData;
import com.test.learn.model.Station;
import com.test.learn.model.response.RouteDetailResponse;
import com.test.learn.model.response.RouteList;
import com.test.learn.service.StationDataManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper to convert the internal model, into response model to be given as output to user.
 */
@Component
public class ResponseMapper {

    private final StationDataManager stationDataManager;

    public ResponseMapper(StationDataManager stationDataManager) {
        this.stationDataManager = stationDataManager;
    }

    public RouteList createResponseData(List<RouteData> routeDataList, boolean isTimeNeeded) {

        List<RouteData> sortedRouteDataList = routeDataList.stream()
                .sorted(Comparator.comparing(RouteData::getRouteCost))
                .collect(Collectors.toList());

        RouteList routeList = new RouteList();
        List<RouteDetailResponse> routeDetailResponseList = new ArrayList<>();

        //Returning only top 5 results
        for (int i = 0; i < sortedRouteDataList.size() && i < 5; i++) {
            RouteData routeData = sortedRouteDataList.get(i);
            RouteDetailResponse routeDetailResponse = new RouteDetailResponse();
            routeDetailResponse.setSourceStation(routeData.getRouteStationList().get(0).getStationName());
            routeDetailResponse.setDestinationStation(routeData.getRouteStationList().
                    get(routeData.getRouteStationList().size() - 1).getStationName());
            routeDetailResponse.setNoOfStations(routeData.getRouteStationList().size());

            String routeTime = isTimeNeeded ? String.valueOf(routeData.getRouteCost()) : "NA";

            routeDetailResponse.setRouteTime(routeTime);
            routeDetailResponse.setRoutePath(createRoutePath(routeData.getRouteStationList()));
            routeDetailResponse.setRouteDescription(createRouteDesc(routeData.getRouteStationList()));
            routeDetailResponseList.add(routeDetailResponse);
        }
        routeList.setRouteList(routeDetailResponseList);
        return routeList;
    }

    private String createRoutePath(List<Station> routeStationList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < routeStationList.size() - 1; i++) {
            Station st = routeStationList.get(i);
            Station next = routeStationList.get(i + 1);

            addStationRoutePath(stringBuilder, st);
            if (st.getTrainLine() != next.getTrainLine()) {
                Station junctionStationOnOtherLine = stationDataManager.getStationFromTrainLine(next.getTrainLine(),
                        st.getStationName());
                addStationRoutePath(stringBuilder, junctionStationOnOtherLine);
            }

        }
        stringBuilder.append("'");
        stringBuilder.append(routeStationList.get(routeStationList.size() - 1).getStationCode());
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    private void addStationRoutePath(StringBuilder stringBuilder, Station st) {
        stringBuilder.append("'");
        stringBuilder.append(st.getStationCode());
        stringBuilder.append("', ");
    }

    private String createRouteDesc(List<Station> routeStationList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < routeStationList.size() - 1; i++) {
            Station curStation = routeStationList.get(i);
            Station nextStation = routeStationList.get(i + 1);
            if (curStation.getTrainLine() != nextStation.getTrainLine()) {
                stringBuilder.append("Change from ");
                stringBuilder.append(curStation.getTrainLine());
                stringBuilder.append(" line to ");
                stringBuilder.append(nextStation.getTrainLine());
                stringBuilder.append(" line");
                stringBuilder.append(" ----> ");
            }
            stringBuilder.append("Take ");
            stringBuilder.append(nextStation.getTrainLine());
            stringBuilder.append(" Line from ");
            stringBuilder.append(curStation.getStationName());
            stringBuilder.append(" to ");
            stringBuilder.append(nextStation.getStationName());
            stringBuilder.append(" ----> ");
        }
        return stringBuilder.toString();
    }
}

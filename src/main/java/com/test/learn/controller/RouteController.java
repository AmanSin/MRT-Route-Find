package com.test.learn.controller;

import com.test.learn.model.request.RouteInput;
import com.test.learn.model.response.RouteList;
import com.test.learn.service.RouteService;
import com.test.learn.util.Constant;
import com.test.learn.validation.RouteValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class RouteController {

    private final RouteValidation routeValidation;
    private final RouteService routeService;

    public RouteController(RouteValidation routeValidation, RouteService routeService) {
        this.routeValidation = routeValidation;
        this.routeService = routeService;
    }

    @PostMapping(Constant.HTTP_GET_ROUTE_BY_TIME_REQUEST_URI)
    public ResponseEntity<RouteList> getRouteByTime(@RequestBody @Valid RouteInput routeInput) {
        log.debug("Received getRoute request with following input: {}", routeInput);

        routeValidation.validateRequestByTime(routeInput);
        RouteList routeList = routeService.getRouteListByTime(routeInput);

        log.debug("Returning getRoute request with following routeList: {}", routeList);
        return new ResponseEntity<>(routeList, HttpStatus.OK);
    }

    @PostMapping(Constant.HTTP_GET_ROUTE_REQUEST_URI)
    public ResponseEntity<RouteList> getRoute(@RequestBody @Valid RouteInput routeInput) {
        log.debug("Received getRoute request with following input: {}", routeInput);

        routeValidation.validateRequestByTime(routeInput);
        RouteList routeList = routeService.getRouteList(routeInput);

        log.debug("Returning getRoute request with following routeList: {}", routeList);
        return new ResponseEntity<>(routeList, HttpStatus.OK);
    }
}

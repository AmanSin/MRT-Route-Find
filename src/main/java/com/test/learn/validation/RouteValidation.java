package com.test.learn.validation;

import com.test.learn.exception.InvalidRequestException;
import com.test.learn.model.request.RouteInput;
import com.test.learn.service.StationDataManager;
import com.test.learn.util.StringUtil;
import com.test.learn.util.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
/**
 * Responsible for validating the user input in the API, and throw error accordingly.
 */
public class RouteValidation {

    private final StationDataManager stationDataManager;
    private final StringUtil stringUtil;

    public RouteValidation(StationDataManager stationDataManager) {
        this.stationDataManager = stationDataManager;
        stringUtil = new StringUtil();
    }

    public void validateRequestByTime(RouteInput routeInput) {
        if (routeInput == null) {
            throw new InvalidRequestException(ErrorCode.ERR_API_INP_001);
        }
        validateSourceStation(routeInput.getSourceStation());
        validateDestinationStation(routeInput.getDestinationStation());
        validateStartingTime(routeInput.getStartingTime());
    }

    private void validateStartingTime(String startingTime) {
        try {
            stringUtil.checkNonEmptyString(startingTime);
            LocalDateTime.parse(startingTime);
        } catch (Exception e) {
            throw new InvalidRequestException(ErrorCode.ERR_API_INP_004, e);
        }
    }

    private void validateSourceStation(String sourceStation) {
        try {
            stringUtil.checkNonEmptyString(sourceStation);
            checkValidStation(sourceStation);
        } catch (Exception e) {
            throw new InvalidRequestException(ErrorCode.ERR_API_INP_002, e);
        }
    }

    private void validateDestinationStation(String destinationStation) {
        try {
            stringUtil.checkNonEmptyString(destinationStation);
            checkValidStation(destinationStation);
        } catch (Exception e) {
            throw new InvalidRequestException(ErrorCode.ERR_API_INP_003, e);
        }
    }

    // Check if the requested station exists in the loaded station list
    private void checkValidStation(String station) {
        if (!stationDataManager.isValidStation(station)) {
            throw new RuntimeException("Given station don't exists");
        }
    }
}

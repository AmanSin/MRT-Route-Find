package com.test.learn.util;

import com.test.learn.util.enums.TimePeriod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static final String INVALID_REQUEST_PARAM = "Invalid request parameters";

    public static final String HTTP_GET_ROUTE_BY_TIME_REQUEST_URI = "/v1/getRouteByTime";
    public static final String HTTP_GET_ROUTE_REQUEST_URI = "/v1/getRoute";

    public static final Map<TimePeriod, Integer> timePeriodLineChangeCost = Collections.unmodifiableMap(new HashMap<TimePeriod, Integer>() {{
        put(TimePeriod.PEAK, 15);
        put(TimePeriod.NON_PEAK, 10);
        put(TimePeriod.NIGHT, 10);
    }});
}

package com.test.learn.util;

import com.test.learn.util.enums.TimePeriod;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class TimePeriodUtilTest {
    private TimePeriodUtil timePeriodUtil;

    @Before
    public void setup() {
        timePeriodUtil = new TimePeriodUtil();
    }

    @Test
    public void testGetTimePeriod_1() {
        TimePeriod timePeriod = timePeriodUtil.getTimePeriod(LocalDateTime.parse("2019-01-31T06:05"));
        Assert.assertEquals(timePeriod, TimePeriod.PEAK);
    }

    @Test
    public void testGetTimePeriod_2() {
        TimePeriod timePeriod = timePeriodUtil.getTimePeriod(LocalDateTime.parse("2019-01-31T18:01"));
        Assert.assertEquals(timePeriod, TimePeriod.PEAK);
    }

    @Test
    public void testGetTimePeriod_3() {
        TimePeriod timePeriod = timePeriodUtil.getTimePeriod(LocalDateTime.parse("2019-01-31T22:05"));
        Assert.assertEquals(timePeriod, TimePeriod.NIGHT);
    }

    @Test
    public void testGetTimePeriod_4() {
        TimePeriod timePeriod = timePeriodUtil.getTimePeriod(LocalDateTime.parse("2019-01-31T10:00"));
        Assert.assertEquals(timePeriod, TimePeriod.NON_PEAK);
    }

}

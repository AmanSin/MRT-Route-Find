package com.test.learn.util;

import com.test.learn.util.enums.TimePeriod;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

//TODO add the TimeZone information in time calculation

/**
 * Utility class to compute TimePeriod, based on the given time.
 */
public class TimePeriodUtil {
    private static final EnumSet<DayOfWeek> WEEKEND_DAYS = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final LocalTime morningPeakTimeStart = LocalTime.parse("06:00:00");
    private static final LocalTime morningPeakTimeEnd = LocalTime.parse("09:00:00");
    private static final LocalTime eveningPeakTimeStart = LocalTime.parse("18:00:00");
    private static final LocalTime eveningPeakTimeEnd = LocalTime.parse("21:00:00");
    private static final LocalTime nightTimeStart = LocalTime.parse("20:00:00");
    private static final LocalTime nightTimeEnd = LocalTime.parse("06:00:00");

    public TimePeriod getTimePeriod(LocalDateTime localDateTime) {
        DayOfWeek currentDay = localDateTime.getDayOfWeek();
        LocalTime currentTime = localDateTime.toLocalTime();

        if (currentTime.isAfter(nightTimeStart) || currentTime.isBefore(nightTimeEnd)) {
            return TimePeriod.NIGHT;
        }

        if (!isWeekend(currentDay)) {
            if (
                    (currentTime.isAfter(morningPeakTimeStart) && currentTime.isBefore(morningPeakTimeEnd))
                            || (currentTime.isAfter(eveningPeakTimeStart) && currentTime.isBefore(eveningPeakTimeEnd))
            ) {
                return TimePeriod.PEAK;
            }
        }
        return TimePeriod.NON_PEAK;
    }

    private boolean isWeekend(DayOfWeek currentDay) {
        return WEEKEND_DAYS.contains(currentDay);
    }


}

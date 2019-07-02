package com.test.learn.util;

import com.test.learn.model.Station;
import com.test.learn.util.enums.TrainLine;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
/**
 * Utility class, providing various helper methods to play with Station model.
 */
public class StationDataUtil {
    private final static String COMMA_DELIMITER = ",";
    private final static List<String> dateFormats = Arrays.asList("dd MMMM yyyy", "d MMMM yyyy");

    public List<Station> getStationListFromFile(String stationListFile) {
        log.info("File path is : {}", stationListFile);
        List<Station> stationList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(stationListFile))) {

            //Skipping header line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {

                String[] values = line.split(COMMA_DELIMITER);
                Station station = new Station();
                station.setStationCode(values[0]);
                station.setStationName(values[1]);
                station.setOpeningDate(parseDate(values[2]));
                station.setTrainLine(TrainLine.valueOf(values[0].substring(0, 2)));
                station.setStationNumber(Integer.parseInt(values[0].substring(2)));
                stationList.add(station);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error in reading station data file", e);
        }
        return stationList;
    }

    public Set<String> getUniqueStationName(List<Station> stationList) {
        Set<String> uniqueStationSet = new HashSet<>();
        for (Station st : stationList) {
            uniqueStationSet.add(st.getStationName());
        }
        return uniqueStationSet;
    }

    public Map<String, List<Station>> stationNameToStationDataMap(List<Station> stationList) {
        Map<String, List<Station>> stationNameToStationDataMap = new HashMap<>();
        for (Station st : stationList) {
            if (stationNameToStationDataMap.get(st.getStationName()) == null) {
                stationNameToStationDataMap.put(st.getStationName(), new ArrayList<>());
            }
            stationNameToStationDataMap.get(st.getStationName()).add(st);
        }
        return stationNameToStationDataMap;
    }

    private LocalDate parseDate(String date) {
        for (String format : dateFormats) {
            try {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            } catch (Exception e) {
                //intentionally empty
            }
        }
        throw new IllegalArgumentException("Invalid input for date, unable to parse: " + date);
    }
}

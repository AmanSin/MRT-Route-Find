package com.test.learn.model;

import com.test.learn.util.enums.TrainLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
/**
 * Model class to hold each input station in the memory.
 */
public class Station {
    private String stationCode;
    private String stationName;
    private LocalDate openingDate;
    private TrainLine trainLine;
    private int stationNumber;
}

package com.test.learn.config;

import com.test.learn.service.StationDataManager;
import com.test.learn.util.StationDataUtil;
import com.test.learn.util.StationGraphUtil;
import com.test.learn.util.TimePeriodUtil;
import com.test.learn.util.TrainLineDataUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StationDataConfiguration {

    @Value("${station.data.file}")
    private String stationListFile;

    @Bean
    public StationDataManager stationDataManager() {
        return new StationDataManager(stationListFile, new StationDataUtil(), new TrainLineDataUtil(),
                new StationGraphUtil(), new TimePeriodUtil());
    }
}

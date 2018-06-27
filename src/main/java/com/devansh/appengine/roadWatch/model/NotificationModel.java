package com.devansh.appengine.roadWatch.model;

import com.devansh.appengine.roadWatch.response.GoogleResponse;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.logging.Logger;

@Data
public class NotificationModel {
    private final static Logger log = Logger.getLogger(NotificationModel.class.getName());
    private TweetModel tweetModel;
    private Integer startHour;
    private Integer stopHour;
    private Integer frequencyInMins;
    private Double tresholdSpeedInKmPerHour;

    public Boolean triggerTweet(GoogleResponse googleResponse) {
        Double speedOfCar = getSpeed(googleResponse);
        if (speedOfCar != null) {
            if (speedOfCar < tresholdSpeedInKmPerHour) return true;
        }
        return false;
    }

    public Double getSpeed(GoogleResponse googleResponse) {
        if (googleResponse == null || googleResponse.getRoutes() == null || googleResponse.getRoutes()
                                                                                          .get(0)
                                                                                          .getLegs() == null) {
            return null;
        }

        GoogleResponse.Legs trafficData = googleResponse.getRoutes()
                                                        .get(0)
                                                        .getLegs()
                                                        .get(0);
        if (trafficData.getDistance() == null || trafficData.getDuration_in_traffic() == null) return null;

        Double speedInMetrePerSec = (trafficData.getDistance()
                                                .getValue() / (trafficData.getDuration_in_traffic()
                                                                          .getValue() * 1.00D));


        Double speedInKmPerHour = speedInMetrePerSec * 3.6;
        log.info("Speed derived:" + speedInKmPerHour);

        DecimalFormat df = new DecimalFormat("0.00");

        return Double.valueOf(df.format(speedInKmPerHour));
    }
}

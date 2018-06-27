package com.devansh.appengine.roadWatch.model;

import lombok.Data;

import java.util.List;

@Data
public class TweetModel {
    private String name;
    private List<String> text;
    private List<String> mentions;
    private List<String> hastags;
    private Double originLat;
    private Double originLng;
    private Double destinationLat;
    private Double destinationLng;
    private List<Long> mediaList;

    public String getTweetText(final Double speed) {
        return (text + " speed is just" + speed + " km/hr!! " + mentions);
    }
}

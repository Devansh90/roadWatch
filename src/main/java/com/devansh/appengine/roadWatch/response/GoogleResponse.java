package com.devansh.appengine.roadWatch.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleResponse {
    private String status;
    private List<Routes> routes;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Routes {
        private List<Legs> legs;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Legs {
        private CommonClass distance;
        private CommonClass duration;
        private CommonClass duration_in_traffic;
        private String end_address;
        private String start_address;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommonClass {
        private String text;
        private Integer value;
    }

//    https://maps.googleapis.com/maps/api/directions/json?origin=12.939398,77.695305&destination=12.935527,77.697192&&departure_time=now&keyAIzaSyB0v6MsgQ_pXTUVkk0gKcGrvH4uVbh9hIM
//    https://maps.googleapis.com/maps/api/directions/json?origin=12.939398,77.695305&destination=12.935527,77.697192&&departure_time=now&key=AIzaSyB0v6MsgQ_pXTUVkk0gKcGrvH4uVbh9hIM
}

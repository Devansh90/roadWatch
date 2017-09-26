package com.devansh.appengine.roadWatch.service;

import com.devansh.appengine.roadWatch.response.GoogleResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Logger;

//import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;

/*
Task : Single point of contact to Google API
 */
public class GoogleMapsClass {

    private final static Logger log = Logger.getLogger(GoogleMapsClass.class.getName());

    /*
    Call Google API, to get data between 2 points
    Parse the response to get relevant data
     */

    private static final String gogApKy = "AIzaSyB0v6MsgQ_pXTUVkk0gKcGrvH4uVbh9hIM";
    private static final String distancePath = "/maps/api/directions/json?";
    private static final String googleHost = "https://maps.googleapis.com";

    public GoogleResponse callDistanceAPI(Double originLat, Double originLng, Double destinationLat, Double destinationLong) {
        //Setup basic path
        String url = googleHost + distancePath;
        //Add Origin
        url = url + "origin=" + originLat + "," + originLng + "&";
        //Add Destination
        url = url + "destination=" + destinationLat + "," + destinationLong + "&&";
        //Add Postfix
        url = url + "departure_time=now&key=" + gogApKy;

        //Call url ( its a get Call )
        CloseableHttpClient httpClient = HttpClients.createDefault();


        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        HttpGet httpGet = new HttpGet(url);
        log.info("Path used:" + url);
        httpGet.setHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = null;
        GoogleResponse paymentResponseString = null;
        try {
            response = httpClient.execute(httpGet);
            String responseString = null;
            try {
                HttpEntity entity = response.getEntity();
                responseString = EntityUtils.toString(entity, "UTF-8");
//                log.info("Got response from Google: " + responseString);
            } catch (Exception e) {
                log.severe("Exception in googleCall" + e.getMessage());
                e.printStackTrace();
            } finally {
                response.close();
            }

            paymentResponseString = mapper.readValue(responseString, GoogleResponse.class);
        } catch (IOException e) {
            log.severe("Exception in google API " + e.getMessage());
            e.printStackTrace();
        }

        //Could not retrieve the data from Google
        if (response == null || response.getStatusLine()
                                        .getStatusCode() != 200 || paymentResponseString == null || "ok".equalsIgnoreCase(paymentResponseString.getStatus())) {
            //TODO
        }

        log.info("Got response from Google:" + paymentResponseString);
        return paymentResponseString;
    }
}

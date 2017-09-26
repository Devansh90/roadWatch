package com.devansh.appengine.roadWatch.service;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.util.logging.Logger;

public class TwitterClass {

    private final static java.util.logging.Logger log = Logger.getLogger(TwitterClass.class.getName());

    private final String twConKy;
    private final String twConScKy;
    private final String twAccTknKy;
    private final String twAccTkScKy;

    Twitter twitter;

    //Boot Up service
    public TwitterClass() {
        //Old values
//        this.twConKy = "4Zh5FyfgRWQBTmy9rLX7xD4MI";
//        this.twConScKy = "tfDfi5uDosvt3lyaTD3NieOElQvQcLOzN0cdPFCHrxDCwA2SSQ";
//        this.twAccTknKy = "566081182-17IntmJHpGoCbnYZ0XZ9OWMhrZmuImMYrcp9MMq2";
//        this.twAccTkScKy = "JJAPyLGGYI5cmanxVTLBeweK2Tlb2BOHNVn23epNBuvXg";

        this.twConKy = "3Sgn7YywH5FIh9l5RGuxuIDi7";
        this.twConScKy = "g6c1e939ce30HAg2A1fzBnBolSk3HOdLX3yL3lCXnyXZmkABFz";
        this.twAccTknKy = "912602126475436033-Mz10QSiqI0RN3XOFDvuGVGWfQ2yHYzt";
        this.twAccTkScKy = "Lgpl0CmDKmEkwMwPXnp0VmuY22wU39qc6hP3ey6DFlsNW";

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(twConKy)
          .setOAuthConsumerSecret(twConScKy)
          .setOAuthAccessToken(twAccTknKy)
          .setOAuthAccessTokenSecret(twAccTkScKy);
        TwitterFactory tf = new TwitterFactory(cb.build());

        twitter = tf.getInstance();
    }

    //Status Update with 3 Medias
    //Sample : 894818870992752640L
    public void statusUpdate(final String newStatus, Long mediaId1, Long mediaId2) {
        try {
            StatusUpdate statusUpdate = new StatusUpdate(newStatus);

            if(mediaId1 != null && mediaId2 != null) {
                //Set media ids
                statusUpdate.setMediaIds(mediaId1, mediaId2);
            }

            log.info("About to tweet:" + statusUpdate.toString());
            try {
                Status status = twitter.updateStatus(statusUpdate);
                log.info("Successfully updated the status to [" + status.getText() + "].");
            } catch (TwitterException e) {
                log.severe("Twitter Exception in status Update: " + e.getMessage());
            }
        } catch (Exception e) {
            log.severe(" Exception in status Update" + e.getMessage());
        }
    }

    //Upload new Image
    private Long uploadImage(final File image1) {
        log.info("Can read: " + image1.canRead());
        try {
            final UploadedMedia response = twitter.uploadMedia(image1);
            log.info("Response is : " + response);
            log.info("ResponseId: " + response.getMediaId());
            return response.getMediaId();
        } catch (TwitterException e) {
            log.severe("Twitter Exception in upload Image" + e.getMessage());
        }
        return null;
    }
}

package com.devansh.appengine.roadWatch.service;

import com.devansh.appengine.roadWatch.model.NotificationModel;
import com.devansh.appengine.roadWatch.model.TweetModel;
import com.devansh.appengine.roadWatch.response.GoogleResponse;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class DummyTest2 {

    private final static Logger log = Logger.getLogger(DummyTest2.class.getName());

    public Boolean getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(final Boolean runFlag) {
        DummyTest2.runFlag = runFlag;
    }

    private static Boolean runFlag = true;
    private static Integer counter = 0;
    GoogleMapsClass googleMapsClass = new GoogleMapsClass();
    TwitterClass twitterClass = new TwitterClass();

    private ArrayList<NotificationModel> notificationModelArrayList;

    public void runCode(final PrintWriter writer) {
        if (runFlag) {
            notificationModelArrayList = generateNotificationModelArray();

            DateTimeZone istTimeZone = DateTimeZone.forID("Asia/Kolkata");
            DateTime currentDateTime = new DateTime().withZone(istTimeZone);

            int hourOfDay = currentDateTime.getHourOfDay();
           //TODO int minute = currentDateTime.getMinuteOfDay();

            int minute = currentDateTime.getSecondOfDay();

            log.info("Time is :" + currentDateTime);
            for (NotificationModel notificationModel : notificationModelArrayList) {
                if (notificationModel.getStartHour() <= hourOfDay &&  hourOfDay < notificationModel.getStopHour()) {
                    if (minute % notificationModel.getFrequencyInMins() == 0) {
                        log.info("Time to execute tweet:" + notificationModel.getTweetModel()
                                                                             .getName());
                        counter++;
                        TweetModel tweetModel = notificationModel.getTweetModel();
                        GoogleResponse googleResponse = googleMapsClass.callDistanceAPI(tweetModel.getOriginLat(), tweetModel.getOriginLng(), tweetModel.getDestinationLat(),
                                                                                        tweetModel.getDestinationLng());
                        if (notificationModel.triggerTweet(googleResponse)) {
                            //Send out tweet as speed is less than threshold speed!
                            log.info("Speed is below:" + notificationModel.getTresholdSpeedInKmPerHour());

                            //Select random 2 images from media set
                            List<Integer> mediasToUse = fetchMedia(tweetModel.getMediaList());

                            twitterClass.statusUpdate(tweetModel.getTweetText(), tweetModel.getMediaList()
                                                                                           .get(mediasToUse.get(0)), tweetModel.getMediaList()
                                                                                                                               .get(mediasToUse.get(1)));
                        }

                    }
                }
            }
        } else {
            log.info("Code disabled as of now!");
        }
    }

    private List<Integer> fetchMedia(final List<Long> mediaList) {
        Random r = new Random();
        int low = 0;
        int high = mediaList.size();
        int result = r.nextInt(high - low) + low;
        int result2 = r.nextInt(high - low) + low;
        while (result2 == result) {
            result2 = r.nextInt(high - low) + low;
        }
        log.info("Values selected are " + result + " and " + result2);
        return Arrays.asList(result, result2);

    }

    private ArrayList<NotificationModel> generateNotificationModelArray() {

        List<Long> mediaList = Arrays.asList(1298379827L, 2222222L);

        TweetModel towardsKrishviTweet = new TweetModel();
        towardsKrishviTweet.setText("Why is everything so slow!");
        towardsKrishviTweet.setMentions("#BlrTraffic");
        //12.940680, 77.696211
        towardsKrishviTweet.setOriginLat(12.940680);
        towardsKrishviTweet.setOriginLng(77.696211);
        //12.935040, 77.697077
        towardsKrishviTweet.setDestinationLat(12.935040);
        towardsKrishviTweet.setDestinationLng(77.697077);
        towardsKrishviTweet.setName("towardsKrishviTweet");
        towardsKrishviTweet.setMediaList(mediaList);

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setStartHour(18);
        notificationModel.setStopHour(19);
        notificationModel.setTweetModel(towardsKrishviTweet);
        notificationModel.setFrequencyInMins(10);
        notificationModel.setTresholdSpeedInKmPerHour(15.00D);

        ArrayList<NotificationModel> arrayList = new ArrayList<>();
        arrayList.add(notificationModel);

        return arrayList;
    }

    public void stopCode(final PrintWriter writer) {
        runFlag = false;
        log.info("Code stopped at time:" + counter);
        writer.println("Code stopped at time:" + counter);
    }

    public void restartCode(final PrintWriter writer) {
        runFlag = true;
        log.info("Code restarted at counter:" + counter);
        writer.println("Code restarted at counter:" + counter);
    }
}

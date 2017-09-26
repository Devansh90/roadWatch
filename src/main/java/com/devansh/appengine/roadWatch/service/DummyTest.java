package com.devansh.appengine.roadWatch.service;

import java.util.logging.Logger;

public class DummyTest {

    private final static Logger log = Logger.getLogger(DummyTest.class.getName());

    public Boolean getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(final Boolean runFlag) {
        DummyTest.runFlag = runFlag;
    }

    private static Boolean runFlag = true;
    private static Integer counter = 0;

    public void runCode() {
        if (runFlag) {
            log.info("Code running for time:" + counter);
            counter++;
        }
    }

    // origin=12.939398,77.695305&destination=12.935527,77.697192
    public void stopCode() {
        runFlag = false;

        //Test Code
        GoogleMapsClass googleMapsClass = new GoogleMapsClass();
        log.info("Called google! : " + googleMapsClass.callDistanceAPI(12.939398, 77.695305, 12.935527, 77.697192));
        log.info("Code stopped at time:" + counter);
    }
}

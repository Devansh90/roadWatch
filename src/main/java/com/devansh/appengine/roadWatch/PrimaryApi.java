package com.devansh.appengine.roadWatch;

import com.devansh.appengine.roadWatch.service.DummyTest2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class PrimaryApi
        extends HttpServlet {

    private final static Logger log = Logger.getLogger(PrimaryApi.class.getName());

    private DummyTest2 dummyTest = new DummyTest2();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        log.info("Inside Start Call!");
        runServer(resp, dummyTest);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        log.info("Inside Stop call!");
        stopServer(resp, dummyTest);
    }

    @Override
    protected void doPut(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        log.info("Inside Restart call!");
        restartServer(resp, dummyTest);
    }

    private void stopServer(final HttpServletResponse resp, final DummyTest2 dummyTest) {
        try {
            dummyTest.stopCode(resp.getWriter());
            log.info("Stop code executed!");
//            throw new InterruptedException();
        } catch (Exception e) {
            log.severe("Error in Stop thread:" + e.getMessage());
        }
    }

    private void restartServer(final HttpServletResponse resp, final DummyTest2 dummyTest) {
        try {
            dummyTest.restartCode(resp.getWriter());
            log.info("Restart code executed!");
        } catch (Exception e) {
            log.severe("Error in Stop thread:" + e.getMessage());
        }
    }

    private void runServer(HttpServletResponse resp, final DummyTest2 dummyTest) throws IOException {
//        dummyTest.runCode(resp.getWriter());
        while (true) {
            try {
                Thread.sleep(1000);
                dummyTest.runCode(resp.getWriter());
            } catch (InterruptedException e) {
                log.severe("Error in Start Thread:" + e.getMessage());
            }
        }
    }
}

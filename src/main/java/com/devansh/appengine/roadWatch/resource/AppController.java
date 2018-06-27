package com.devansh.appengine.roadWatch.resource;

import com.codahale.metrics.annotation.Timed;
import com.devansh.appengine.roadWatch.service.Notifier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/v1")
@Api("/v1")
public class AppController {

    private static Notifier dummyTest;

    public AppController(final Notifier dummyTest) {
        this.dummyTest = dummyTest;
    }

    @GET
    @Timed
    @Path("health_check")
    @ApiOperation("Health Check API")
    public Response test1() {
        log.info("HealthCheck called1");
        return Response.status(Response.Status.OK)
                       .entity("Everything OK")
                       .build();
    }

    @GET
    @Timed
    @Path("/app/execute")
    @ApiOperation("Master App starter")
    public Response appExecute() throws IOException {
        log.info("Master App starter called");
        runServer();
        return Response.status(Response.Status.OK)
                       .entity("Everything OK")
                       .build();
    }

    @GET
    @Timed
    @Path("/app/start")
    @ApiOperation("Master App starter")
    public Response appStarter() {
        log.info("Master App starter called");
        restartServer();
        return Response.status(Response.Status.OK)
                       .entity("Everything OK")
                       .build();
    }

    @GET
    @Timed
    @Path("/app/stop")
    @ApiOperation("Master app stopper")
    public Response appStopper() {
        log.info("Master App stopper called");
        stopServer();
        return Response.status(Response.Status.OK)
                       .entity("Everything OK")
                       .build();
    }

    private static void restartServer() {
        try {
            dummyTest.restartCode();
            log.info("Restart code executed!");
        } catch (Exception e) {
            log.error("Error in restart thread:" + e.getMessage());
        }
    }

    private static void stopServer() {
        try {
            dummyTest.stopCode();
            log.info("Stop code executed!");
        } catch (Exception e) {
            log.error("Error in Stop thread:" + e.getMessage());
        }
    }

    private void runServer() throws IOException {
        //TODO Use below
        //dummyTest.runCode(resp.getWriter());

        //TODO Remove when going prod
        while (true) {
            try {
                Thread.sleep(1000);
                dummyTest.runCode();
            } catch (InterruptedException e) {
                log.error("Error in Bootup Thread:" + e.getMessage());
            }
        }
    }
}

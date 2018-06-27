package com.devansh.appengine.roadWatch;

import com.devansh.appengine.roadWatch.config.ServerConfig;
import com.devansh.appengine.roadWatch.resource.AppController;
import com.devansh.appengine.roadWatch.service.Notifier;
import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

@Slf4j
public class BootupServer
        extends Application<ServerConfig> {

    public static final Notifier dummyTest = new Notifier();

    public static void main(String[] args) throws Exception {
        log.info("Server bootup");
        log.info("In here with args:" + args[0] + " " + args[1]);
        new BootupServer().run(args);
    }

    @Override
    public void run(final ServerConfig configuration, final Environment environment) throws Exception {
        log.info("Environment as per configuration is :" + configuration.getEnvironment());

        //Registry of all mapper and services
        final JerseyEnvironment jersey = environment.jersey();
        jersey.register(MultiPartFeature.class);
        jersey.register(new AppController(dummyTest));
        log.info("Server bootup completed");
//        runServer(dummyTest);
    }

//    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        PrintWriter out = resp.getWriter();
//        log.info("Bootup Server call!");
//        AppController appStarter = new AppController(dummyTest);
//
//        runServer(dummyTest);
//    }


}

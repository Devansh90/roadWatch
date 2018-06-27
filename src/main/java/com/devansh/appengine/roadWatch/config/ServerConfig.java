package com.devansh.appengine.roadWatch.config;

import io.dropwizard.Configuration;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by devanshrusia on 6/5/17.
 */
@Data
public class ServerConfig
        extends Configuration {

    @NotNull
    private String environment;
}

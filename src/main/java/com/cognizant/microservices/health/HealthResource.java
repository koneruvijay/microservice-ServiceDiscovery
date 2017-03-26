package com.cognizant.microservices.health;

import com.google.common.collect.ImmutableMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Map;

@Path("health")
public class HealthResource {

    @GET
    @Produces("application/json")
    public Map<String, Boolean> health() {
        return ImmutableMap.of("healthy", true);
    }
}


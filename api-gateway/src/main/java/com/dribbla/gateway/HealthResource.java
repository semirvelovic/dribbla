package com.dribbla.gateway;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/health")
public class HealthResource {

    @GET
    public String checkHealth() {
        return "ok";
    }
}

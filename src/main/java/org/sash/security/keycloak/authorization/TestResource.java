package org.sash.security.keycloak.authorization;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/test")
public class TestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String manage() {
        System.out.println("Test Resource invoked");
        return "Hello Test";
    }
}

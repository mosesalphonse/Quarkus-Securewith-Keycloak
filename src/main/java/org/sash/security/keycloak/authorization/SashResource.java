package org.sash.security.keycloak.authorization;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/sash")
public class SashResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String manage() {
        System.out.println("Sash Resource invoked");
        return "Hello Sash :";
    }
}

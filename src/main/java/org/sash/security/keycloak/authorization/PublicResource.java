package org.sash.security.keycloak.authorization;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api/public")
public class PublicResource {

    @GET
    public String serve() {
        System.out.println("Public Resource Invoked");
        return "Hello Public";
    }
}

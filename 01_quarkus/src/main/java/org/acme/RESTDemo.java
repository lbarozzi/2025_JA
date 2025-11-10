package org.acme;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api/restdemo")
public class RESTDemo {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String demo() {
        return "This is a REST endpoint in Quarkus";
    }
    
    @GET
    @Path("/{id}/")
    @Produces(MediaType.TEXT_PLAIN)
    public String demoWithPathParam(@PathParam("id") Integer id) {
        return "This is a REST endpoint with a path parameter in Quarkus for id: " + id;
    }
}

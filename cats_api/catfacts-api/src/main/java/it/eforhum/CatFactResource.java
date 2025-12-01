package it.eforhum;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CatFactResource {

    @Inject
    CatFactService catFactService;

    @GET
    @Path("/fact")
    public CatFact getFact() {
        return catFactService.getRandomFact();
    }

    @GET
    @Path("/facts")
    public List<CatFact> getFacts(@QueryParam("limit") @DefaultValue("10") int limit) {
        return catFactService.getRandomFacts(limit);
    }
}

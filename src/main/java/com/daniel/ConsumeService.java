package com.daniel;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

@Path("pokemon")
@RegisterRestClient(configKey = "api-url")
public interface ConsumeService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<RestResponse<PokemonResponse>> getPokemon(@QueryParam("limit") int value, @QueryParam("offset") int offset);


}

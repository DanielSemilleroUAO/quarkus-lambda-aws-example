package com.daniel.controllers;

import com.daniel.services.FruitAsyncService;
import com.daniel.clients.ConsumeService;
import com.daniel.entities.Fruit;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Path("/hello")
public class GreetingResource {

    private final Logger logger = LoggerFactory.getLogger(GreetingResource.class);

    @Inject
    FruitAsyncService service;

    @RestClient
    ConsumeService consumeService;
/*
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> hello() {
        return Uni.createFrom().item("hello jaxrs");

    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> helloPost() {
        HashMap mapResponse = new HashMap();
        mapResponse.put("Response", "hello world jaxv");
        return Uni.createFrom().item(Response.status(Response.Status.CREATED).entity(mapResponse).build());
    }

 */

    @GET
    public Uni<?> getAll() {
        logger.info("Obtener toda la información de la bd");
        //return consumeService.getPokemon(100, 0);
        //return service.findAll();
        return Uni
                .combine()
                .all()
                .unis(
                        service.findAll(),
                        consumeService.getPokemon(100, 0)
                )
                .asTuple()
                .map(tuple -> {
                    Map response = new HashMap();
                    response.put("Data1", tuple.getItem1());
                    response.put("Data2", tuple.getItem2().getEntity());
                    return response;
                });
    }

    @GET
    @Path("{name}")
    public Uni<Fruit> getSingle(String name) {
        logger.info("Obtener solo la información de la bd {}", name);
        return service.get(name);
    }
    /*

    @POST
    public Uni<List<Fruit>> add(Fruit fruit) {

        logger.info("Se añade fruta {}", fruit);

        return service.add(fruit)
                .onItem().ignore().andSwitchTo(this::getAll);
    }*/
}

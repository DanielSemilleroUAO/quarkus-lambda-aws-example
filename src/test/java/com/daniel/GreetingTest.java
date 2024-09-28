package com.daniel;

import com.daniel.clients.ConsumeService;
import com.daniel.models.PokemonResponse;
import com.daniel.services.FruitAsyncService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class GreetingTest {

    @InjectMock
    FruitAsyncService fruitAsyncService;

    @InjectMock
    @RestClient
    ConsumeService consumeService;

    @Test
    public void testJaxrs() {
        Mockito.when(consumeService.getPokemon(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Uni.createFrom().item(RestResponse.ok(PokemonResponse.builder().build())));
        RestAssured.when().get("/hello");
    }

    @Test
    public void getSingleTest() {
        RestAssured.when().get("/hello/test");
    }



    @Test
    public void testServlet() {
        RestAssured.when().get("/servlet/hello").then()
                .contentType("text/plain")
                .body(equalTo("hello servlet"));
    }

    @Test
    public void testVertx() {
        RestAssured.when().get("/vertx/hello").then()
                .contentType("text/plain")
                .body(equalTo("hello vertx"));
    }

    @Test
    public void testFunqy() {
        RestAssured.when().get("/funqyHello").then()
                .contentType("application/json")
                .body(equalTo("\"hello funqy\""));
    }

}

package com.daniel.services;

import com.daniel.entities.Fruit;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.daniel.services.AbstractService.FRUIT_DESC_COL;
import static com.daniel.services.AbstractService.FRUIT_NAME_COL;

@QuarkusTest
public class FruitAsyncServiceTest {


    @Inject
    public FruitAsyncService fruitAsyncService;

    @InjectMock
    DynamoDbAsyncClient dynamoDB;


    @Test
    public void testGetTableName() {
        Assertions.assertEquals("QuarkusFruits", fruitAsyncService.getTableName());
    }

    @Test
    public void scanRequestTest() {
        ScanRequest scanRequest = fruitAsyncService.scanRequest();
        Assertions.assertEquals(Arrays.asList("fruitName", "fruitDescription"), scanRequest.attributesToGet());
    }

    @Test
    public void putRequestTest() {
        PutItemRequest putRequest = fruitAsyncService.putRequest(
                Fruit.builder()
                        .name("manzana")
                        .description("").build());
        Assertions.assertEquals("manzana", putRequest.item().get(FRUIT_NAME_COL).s());
    }

    @Test
    public void getRequestTest() {
        GetItemRequest getItemRequest = fruitAsyncService.getRequest("manzana");
        Assertions.assertEquals("manzana", getItemRequest.key().get(FRUIT_NAME_COL).s());
    }

    @Test
    public void findAllTest() {
        Mockito.when(dynamoDB.scan(fruitAsyncService.scanRequest())).thenReturn(CompletableFuture.completedFuture(ScanResponse.builder().build()));
        List<Fruit> fruitList = fruitAsyncService.findAll().await().atMost(Duration.ofSeconds(5));
        Assertions.assertEquals(List.of(), fruitList);
    }

    @Test
    public void addTest() {
        Mockito.when(dynamoDB.scan(fruitAsyncService.scanRequest())).thenReturn(CompletableFuture.completedFuture(ScanResponse.builder().build()));
        Mockito.when(dynamoDB.putItem((PutItemRequest) Mockito.any())).thenReturn(CompletableFuture.completedFuture(PutItemResponse.builder().build()));
        List<Fruit> fruitList = fruitAsyncService.add(Fruit.builder().build()).await().atMost(Duration.ofSeconds(5));
        Assertions.assertEquals(List.of(), fruitList);
    }

    @Test
    public void getTest() {
        Map<String, AttributeValue> fruitMap = new HashMap();
        fruitMap.put(FRUIT_NAME_COL, AttributeValue.fromS("manzana"));
        fruitMap.put(FRUIT_DESC_COL, AttributeValue.fromS("test"));
        Mockito.when(dynamoDB.getItem((GetItemRequest) Mockito.any())).thenReturn(CompletableFuture.completedFuture(GetItemResponse.builder().item(fruitMap

        ).build()));
        Fruit fruit = fruitAsyncService.get("").await().atMost(Duration.ofSeconds(5));
        Assertions.assertEquals("manzana", fruit.getName());
    }

}

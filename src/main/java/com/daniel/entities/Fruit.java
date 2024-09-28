package com.daniel.entities;

import com.daniel.services.AbstractService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Fruit {
    private String name;
    private String description;

    public static Fruit from(Map<String, AttributeValue> item) {
        Fruit fruit = new Fruit();
        if (item != null && !item.isEmpty()) {
            fruit.setName(item.getOrDefault(AbstractService.FRUIT_NAME_COL, AttributeValue.fromS("")).s());
            fruit.setDescription(item.getOrDefault(AbstractService.FRUIT_DESC_COL, AttributeValue.fromS("")).s());
        }
        return fruit;
    }
}

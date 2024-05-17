package com.daniel;

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
            fruit.setName(item.get(AbstractService.FRUIT_NAME_COL).s());
            fruit.setDescription(item.get(AbstractService.FRUIT_DESC_COL).s());
        }
        return fruit;
    }
}

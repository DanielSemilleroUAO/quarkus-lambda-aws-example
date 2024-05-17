package com.daniel;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ResultPokemon {
    private String name;
    private String url;
}

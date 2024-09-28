package com.daniel.models;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.util.List;

@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class PokemonResponse {
    Integer count;
    String next;
    List<ResultPokemon> results;
}

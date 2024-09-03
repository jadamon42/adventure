package com.github.jadamon42.adventure.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Effect {
    private final UUID id;
    private final String name;

    public Effect(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @JsonCreator
    public Effect(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

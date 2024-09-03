package com.github.jadamon42.adventure.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Item {
    private final UUID id;
    private final String name;

    public Item(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @JsonCreator
    public Item(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
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

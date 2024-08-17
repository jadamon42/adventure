package com.github.jadamon42.adventure.model;

import java.io.Serializable;

public class Effect implements Serializable {
    private final String name;

    public Effect(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

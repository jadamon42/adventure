package com.github.jadamon42.adventure.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerDelta {
    private final String name;
    private final List<Item> items;
    private final List<Effect> effects;

    public PlayerDelta(String name, List<Item> items, List<Effect> effects) {
        this.name = name;
        this.items = items;
        this.effects = effects;
    }

    public PlayerDelta(String name) {
        this.name = name;
        this.items = List.of();
        this.effects = List.of();
    }

    public PlayerDelta(Item item) {
        this.name = null;
        this.items = List.of(item);
        this.effects = List.of();
    }

    public PlayerDelta(Effect effect) {
        this.name = null;
        this.items = List.of();
        this.effects = List.of(effect);
    }

    public PlayerDelta() {
        this.name = null;
        this.items = List.of();
        this.effects = List.of();
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public static PlayerDelta.Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private final List<Item> items;
        private final List<Effect> effects;

        public Builder() {
            name = null;
            items = new ArrayList<>();
            effects = new ArrayList<>();
        }

        public PlayerDelta build() {
            return new PlayerDelta(name, items, effects);
        }

        public void setName(String name) {
            this.name = name;
        }

        public void addItem(Item item) {
            this.items.add(item);
        }

        public void addEffect(Effect effect) {
            this.effects.add(effect);
        }
    }
}

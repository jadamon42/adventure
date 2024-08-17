package com.github.jadamon42.adventure.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDelta implements Serializable {
    private final String name;
    private final Item[] items;
    private final Effect[] effects;

    public PlayerDelta(String name, List<Item> items, List<Effect> effects) {
        this.name = name;
        this.items = items.toArray(new Item[0]);
        this.effects = effects.toArray(new Effect[0]);
    }

    public PlayerDelta(String name) {
        this.name = name;
        this.items = new Item[0];
        this.effects = new Effect[0];
    }

    public PlayerDelta(Item item) {
        this.name = null;
        this.items = new Item[] {item};
        this.effects = new Effect[0];
    }

    public PlayerDelta(Effect effect) {
        this.name = null;
        this.items = new Item[0];
        this.effects = new Effect[] {effect};
    }

    public String getName() {
        return name;
    }

    public Item[] getItems() {
        return items;
    }

    public Effect[] getEffects() {
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

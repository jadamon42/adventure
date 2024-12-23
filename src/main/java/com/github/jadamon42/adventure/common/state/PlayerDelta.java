package com.github.jadamon42.adventure.common.state;

import com.github.jadamon42.adventure.common.model.CustomAttribute;
import com.github.jadamon42.adventure.common.model.Effect;
import com.github.jadamon42.adventure.common.model.Item;

import java.util.ArrayList;
import java.util.List;

public class PlayerDelta {
    private final String name;
    private final List<CustomAttribute> customAttributes;
    private final List<Item> items;
    private final List<Effect> effects;

    public PlayerDelta(String name, List<CustomAttribute> customAttributes, List<Item> items, List<Effect> effects) {
        this.name = name;
        this.customAttributes = customAttributes;
        this.items = items;
        this.effects = effects;
    }

    public PlayerDelta(String name) {
        this.name = name;
        this.customAttributes = List.of();
        this.items = List.of();
        this.effects = List.of();
    }

    public PlayerDelta(String key, String value) {
        this.name = null;
        this.customAttributes = List.of(new CustomAttribute(key, value));
        this.items = List.of();
        this.effects = List.of();
    }

    public PlayerDelta(Item item) {
        this.name = null;
        this.customAttributes = List.of();
        this.items = List.of(item);
        this.effects = List.of();
    }

    public PlayerDelta(Effect effect) {
        this.name = null;
        this.customAttributes = List.of();
        this.items = List.of();
        this.effects = List.of(effect);
    }

    public PlayerDelta() {
        this.name = null;
        this.customAttributes = List.of();
        this.items = List.of();
        this.effects = List.of();
    }

    public String getName() {
        return name;
    }

    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public boolean hasChanges() {
        return name != null || !customAttributes.isEmpty() || !items.isEmpty() || !effects.isEmpty();
    }

    public static PlayerDelta.Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private final List<CustomAttribute> customAttributes;
        private final List<Item> items;
        private final List<Effect> effects;

        public Builder() {
            name = null;
            customAttributes = new ArrayList<>();
            items = new ArrayList<>();
            effects = new ArrayList<>();
        }

        public PlayerDelta build() {
            return new PlayerDelta(name, customAttributes, items, effects);
        }

        public void setName(String name) {
            this.name = name;
        }

        public void addCustomAttribute(CustomAttribute customAttribute) {
            this.customAttributes.add(customAttribute);
        }

        public void addItem(Item item) {
            this.items.add(item);
        }

        public void addEffect(Effect effect) {
            this.effects.add(effect);
        }
    }
}

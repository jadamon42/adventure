package com.github.jadamon42.adventure.common.model;

import com.github.jadamon42.adventure.common.state.PlayerDelta;

public class Player {
    private String name;
    private final CustomAttributes customAttributes;
    private final Inventory inventory;
    private final Effects effects;

    public Player() {
        customAttributes = new CustomAttributes();
        inventory = new Inventory();
        effects = new Effects();
    }

    public Player(Player player) {
        this.name = player.name;
        this.customAttributes = new CustomAttributes(player.getCustomAttributes());
        this.inventory = new Inventory(player.getInventory());
        this.effects = new Effects(player.getEffects());
    }

    public PlayerDelta setName(String name) {
        this.name = name;
        return new PlayerDelta(name);
    }

    public String getName() {
        return name;
    }

    public PlayerDelta addCustomAttribute(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        customAttributes.putOrRemove(new CustomAttribute(key, value));
        return new PlayerDelta(key, value);
    }

    public String getCustomAttribute(String key) {
        return customAttributes.get(key);
    }

    public CustomAttributes getCustomAttributes() {
        return customAttributes;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public PlayerDelta addItem(Item item) {
        inventory.add(item);
        return new PlayerDelta(item);
    }

    public boolean hasItem(Item item) {
        return inventory.contains(item);
    }

    public boolean hasItem(String itemName) {
        return inventory.contains(itemName);
    }

    public Effects getEffects() {
        return effects;
    }

    public PlayerDelta addEffect(Effect effect) {
        effects.add(effect);
        return new PlayerDelta(effect);
    }

    public boolean hasEffect(Effect effect) {
        return effects.contains(effect);
    }

    public boolean hasEffect(String effectName) {
        return effects.contains(effectName);
    }

    public void apply(PlayerDelta playerDelta) {
        if (playerDelta.getName() != null) {
            this.name = playerDelta.getName();
        }
        for (CustomAttribute customAttribute : playerDelta.getCustomAttributes()) {
            customAttributes.putOrRemove(customAttribute);
        }
        for (Item item : playerDelta.getItems()) {
            this.inventory.add(item);
        }
        for (Effect effect : playerDelta.getEffects()) {
            this.effects.add(effect);
        }
    }
}

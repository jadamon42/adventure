package com.github.jadamon42.adventure.common.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory implements Iterable<Item> {
    private final List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public Inventory(Inventory inventory) {
        items = new ArrayList<>(inventory.items);
    }

    public void add(Item item) {
        items.add(item);
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public boolean contains(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}

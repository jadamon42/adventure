package com.github.jadamon42.adventure.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Effects implements Serializable {
    private final List<Effect> effects;

    public Effects() {
        effects = new ArrayList<>();
    }

    public Effects(Effects effects) {
        this.effects = new ArrayList<>(effects.effects);
    }

    public void add(Effect effect) {
        effects.add(effect);
    }

    public void remove(Effect effect) {
        effects.remove(effect);
    }

    public boolean contains(Effect effect) {
        return effects.contains(effect);
    }

    public boolean contains(String effectName) {
        for (Effect effect : effects) {
            if (effect.getName().equals(effectName)) {
                return true;
            }
        }
        return false;
    }
}

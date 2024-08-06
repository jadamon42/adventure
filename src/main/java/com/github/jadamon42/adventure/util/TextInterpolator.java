package com.github.jadamon42.adventure.util;

import com.github.jadamon42.adventure.model.Player;

import java.util.HashMap;
import java.util.function.Function;

public class TextInterpolator {
    private static final HashMap<String, Function<Player, String>> variables;

    static {
        variables = new HashMap<>();
        variables.put("PLAYER_NAME", player -> capitalizeWords(player.getName()));
    }

    public static String interpolate(String text, Player player) {
        for (String key : variables.keySet()) {
            if (text.contains("$" + key)) {
                text = text.replace("$" + key, variables.get(key).apply(player));
            }
        }
        return text;
    }

    private static String capitalizeWords(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }

        string = string.trim().replaceAll("\\s+", " ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : string.split(" ")) {
            capitalized.append(Character.toUpperCase(word.charAt(0)))
                       .append(word.substring(1).toLowerCase())
                       .append(" ");
        }
        return capitalized.toString().trim();
    }
}

package com.github.jadamon42.adventure.common.util;

import com.github.jadamon42.adventure.common.model.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInterpolator {
    public static String interpolate(String text, Player player) {
        Pattern pattern = Pattern.compile("\\$(\\w+)");
        Matcher matcher = pattern.matcher(text);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String variableName = matcher.group(1);
            String replacement = VariableGetter.get(player, variableName);
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);

        return result.toString();
    }

    private static class VariableGetter {
        public static String get(Player player, String variableName) {
            if (variableName.equals("PLAYER_NAME")) {
                return capitalizeWords(player.getName());
            }
            String customAttribute = player.getCustomAttribute(variableName);
            return customAttribute != null ? customAttribute : "";
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
}

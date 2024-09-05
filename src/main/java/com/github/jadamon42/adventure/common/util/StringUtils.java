package com.github.jadamon42.adventure.common.util;

public class StringUtils {
    public static String softWrap(String text, int maxLength) {
        String[] lines = text.split("\n");
        StringBuilder wrappedText = new StringBuilder();

        for (String line : lines) {
            wrappedText.append(wrapLine(line, maxLength)).append("\n");
        }

        return wrappedText.toString().trim();
    }

    private static String wrapLine(String line, int maxLength) {
        if (line.length() <= maxLength) {
            return line;
        }

        int end = maxLength;
        if (line.charAt(end) != ' ') {
            int lastSpace = line.lastIndexOf(' ', end);
            if (lastSpace > 0) {
                end = lastSpace;
            }
        }

        String wrappedLine = line.substring(0, end);
        String remainingText = line.substring(end).trim();

        return wrappedLine + "\n" + wrapLine(remainingText, maxLength);
    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}

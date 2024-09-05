package com.github.jadamon42.adventure.common.util;

import java.util.Objects;

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

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if(str == null || searchStr == null) {
            return false;
        }

        final int length = searchStr.length();
        if (length == 0) {
            return true;
        }

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    public static boolean containsAnyIgnoreCase(String str, String delimitedSearchStrs, String delimiterRegex) {
        if (str == null || delimitedSearchStrs == null) {
            return false;
        }

        String[] searchStrs = delimitedSearchStrs.split(delimiterRegex);
        for (String searchStr : searchStrs) {
            if (containsIgnoreCase(str, searchStr)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAnyIgnoreCase(String str, String delimitedSearchStrs) {
        return containsAnyIgnoreCase(str, delimitedSearchStrs, "[,;\\s]+");
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (Objects.equals(str1, str2)) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }

    public static boolean equalsAnyIgnoreCase(String str, String delimitedSearchStrs, String delimiterRegex) {
        if (str == null || delimitedSearchStrs == null) {
            return false;
        }

        String[] searchStrs = delimitedSearchStrs.split(delimiterRegex);
        for (String searchStr : searchStrs) {
            if (equalsIgnoreCase(str, searchStr)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsAnyIgnoreCase(String str, String delimitedSearchStrs) {
        return equalsAnyIgnoreCase(str, delimitedSearchStrs, "[,;\\s]+");
    }
}

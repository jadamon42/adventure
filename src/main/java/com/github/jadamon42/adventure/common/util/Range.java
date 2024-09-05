package com.github.jadamon42.adventure.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * WARNING
 * `Range` gets serialized and deserialized as part of Serializable Runnables.
 * Changes to this class will break compatibility with existing save files.
 */
public record Range(int start, int end) {

    public boolean contains(int value) {
        return value >= start && value <= end;
    }

    @Override
    public String toString() {
        return "Range{" + "start=" + start + ", end=" + end + '}';
    }

    public static Range parse(String input) {
        input = input.replaceAll("[\\[\\]{}()]", "");

        String[] patterns = {
                "^\\d+$",
                "^(\\d+),(\\d+)$",
                "^(\\d+)-(\\d+)$"
        };

        for (String patternString : patterns) {
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(input);

            if (matcher.matches()) {
                int start = Integer.parseInt(matcher.group(1));
                String endStr = matcher.groupCount() > 1 ? matcher.group(2) : matcher.group(1);
                int end = endStr.isEmpty() ? start : Integer.parseInt(endStr);
                return new Range(start, end);
            }
        }

        throw new IllegalArgumentException("Invalid range format: " + input);
    }
}

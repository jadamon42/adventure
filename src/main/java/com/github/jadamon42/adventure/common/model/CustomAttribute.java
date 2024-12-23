package com.github.jadamon42.adventure.common.model;

import java.util.Map;

public class CustomAttribute implements Map.Entry<String, String> {
    private final String key;
    private String value;

    public CustomAttribute(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String setValue(String value) {
        String oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}

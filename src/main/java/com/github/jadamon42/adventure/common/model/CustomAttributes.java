package com.github.jadamon42.adventure.common.model;

import java.util.HashMap;
import java.util.Map;

public class CustomAttributes extends HashMap<String, String> {
    public CustomAttributes() {
        super();
    }

    public CustomAttributes(Map<String, String> customAttributes) {
        super(customAttributes);
    }

    public void putOrRemove(CustomAttribute customAttribute) {
        if (customAttribute.getValue() == null) {
            remove(customAttribute.getKey());
        } else {
            put(customAttribute.getKey(), customAttribute.getValue());
        }
    }
}

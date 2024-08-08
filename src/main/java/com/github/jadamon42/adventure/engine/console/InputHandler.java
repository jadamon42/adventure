package com.github.jadamon42.adventure.engine.console;

import com.github.jadamon42.adventure.node.LinkedTextChoice;

import java.util.List;

public interface InputHandler {
    String getFreeTextInput();
    int getMultipleChoiceInput(List<LinkedTextChoice> choices);
}


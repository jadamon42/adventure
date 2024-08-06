package com.github.jadamon42.adventure.engine.console;

import com.github.jadamon42.adventure.node.LinkableTextChoice;

import java.util.List;

public interface InputHandler {
    String getFreeTextInput();
    int getMultipleChoiceInput(List<LinkableTextChoice> choices);
}


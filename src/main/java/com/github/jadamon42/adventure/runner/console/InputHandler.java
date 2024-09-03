package com.github.jadamon42.adventure.runner.console;

import com.github.jadamon42.adventure.common.node.LinkedTextChoice;

import java.util.List;

public interface InputHandler {
    String getFreeTextInput();
    int getMultipleChoiceInput(List<LinkedTextChoice> choices);
}


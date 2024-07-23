package com.github.jadamon42.adventure;

import com.github.jadamon42.adventure.model.TextChoice;

import java.util.List;

public interface InputHandler {
    String getFreeTextInput();
    int getMultipleChoiceInput(List<TextChoice> choices);
}


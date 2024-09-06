package com.github.jadamon42.adventure.runner.ui.element;

import javafx.util.Duration;

public class WaitAmount {
    private final Duration duration;

    public WaitAmount(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }
}

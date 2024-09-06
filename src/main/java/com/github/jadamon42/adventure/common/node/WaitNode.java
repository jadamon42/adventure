package com.github.jadamon42.adventure.common.node;

import com.github.jadamon42.adventure.common.state.serialize.SerializableWaitNode;

import java.time.Duration;
import java.util.UUID;

public class WaitNode extends LinkableStoryNode {
    private final Duration duration;

    public WaitNode(Duration duration) {
        this.duration = duration;
    }

    private WaitNode(UUID id, Duration duration) {
        super(id);
        this.duration = duration;
    }

    public static WaitNode fromSerialized(SerializableWaitNode serialized) {
        return new WaitNode(serialized.id(), Duration.ofSeconds(serialized.seconds()));
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public void accept(StoryNodeVisitor visitor) {
        visitor.visit(this);
    }
}

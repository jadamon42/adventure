package com.github.jadamon42.adventure.model;

import java.io.Serializable;
import java.util.*;

public class GameState implements Serializable {
    private final Checkpoint initialCheckpoint;
    private final List<CheckpointDelta> checkpointDeltas;

    public GameState(Checkpoint initialCheckpoint) {
        this.initialCheckpoint = initialCheckpoint;
        this.checkpointDeltas = new ArrayList<>();
    }

    public void addCheckpoint(CheckpointDelta checkpointDelta) {
        this.checkpointDeltas.add(checkpointDelta);
    }

    public Checkpoint getCheckpointForMessageId(UUID messageId) {
        Checkpoint checkpoint = initialCheckpoint;
        for (CheckpointDelta checkpointDelta : checkpointDeltas) {
            if (checkpointDelta.getCurrentMessageId() == messageId) {
                break;
            }
            checkpoint = initialCheckpoint.apply(checkpointDelta);
        }
        return checkpoint;
    }

    public Checkpoint getInitialCheckpoint() {
        return initialCheckpoint;
    }

    public Checkpoint getLatestCheckpoint() {
        return initialCheckpoint.applyAll(checkpointDeltas);
    }

    public void reset() {
        checkpointDeltas.clear();
    }
}

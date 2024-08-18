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
            checkpoint = checkpoint.apply(checkpointDelta);
            if (checkpointDelta.getCurrentMessageId() == messageId) {
                break;
            }
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

    public void resetToCheckpoint(UUID messageId) {
        int index = -1;
        for (int i = 0; i < checkpointDeltas.size(); i++) {
            if (checkpointDeltas.get(i).getCurrentMessageId().equals(messageId)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            checkpointDeltas.subList(index + 1, checkpointDeltas.size()).clear();
        }
    }
}

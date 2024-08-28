package com.github.jadamon42.adventure.engine;

import com.github.jadamon42.adventure.model.GameState;

import java.io.*;

public class GameStateManager {
    public void saveGame(String saveFile, GameState gameState) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            out.writeObject(gameState);
        }
    }

    public void saveGame(File saveFile, GameState gameState) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            out.writeObject(gameState);
        }
    }

    public GameState loadGame(String saveFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile))) {
            return (GameState) in.readObject();
        }
    }

    public GameState loadGame(File saveFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile))) {
            return (GameState) in.readObject();
        }
    }
}


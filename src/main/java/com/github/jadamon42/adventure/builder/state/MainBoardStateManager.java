package com.github.jadamon42.adventure.builder.state;

import java.io.*;

public class MainBoardStateManager {
    public void saveGame(File saveFile, MainBoardState mainBoardState) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            out.writeObject(mainBoardState);
        }
    }

    public MainBoardState loadGame(File saveFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile))) {
            return (MainBoardState) in.readObject();
        }
    }
}

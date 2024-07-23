package com.github.jadamon42.adventure;

import com.github.jadamon42.adventure.model.Player;

public interface GameEngine {
    void start();
    void loadGame(String saveFile);
    void saveGame(String saveFile);
    Player getPlayer();
}


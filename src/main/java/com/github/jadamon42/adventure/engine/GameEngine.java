package com.github.jadamon42.adventure.engine;

import com.github.jadamon42.adventure.model.Player;

public interface GameEngine {
    void startGame();
    void loadGame(String saveFile);
    void saveGame(String saveFile);
    Player getPlayer();
}


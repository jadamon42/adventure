package com.github.jadamon42.adventure.runner;

import com.github.jadamon42.adventure.common.model.Player;

public interface GameEngine {
    void startGame();
    void loadGame(String saveFile);
    void saveGame(String saveFile);
    Player getPlayer();
}


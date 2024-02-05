package com.julesdufraiche.epiwars.commands;

import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.manager.GameState;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StartCommand {
    public StartCommand(GameManager gameManager, Player p, int minPlayersToStart) {
        if (!p.isOp()) {
            p.sendMessage(ChatColor.RED + "You do not have the permission to use this command.");
            return;
        }
        if (gameManager.getPlayersSize() < minPlayersToStart) {
            p.sendMessage(ChatColor.RED + "Not enough players to start the game!");
            return;
        }
        if (gameManager.getGameState() == GameState.STARTING || gameManager.getGameState() == GameState.ACTIVE) {
            p.sendMessage(ChatColor.RED + "The game has already started.");
        }
        gameManager.setGameState(GameState.STARTING);
    }
}

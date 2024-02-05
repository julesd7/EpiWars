package com.julesdufraiche.epiwars.commands;

import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.manager.GameState;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StopCommand {
    public StopCommand(GameManager gameManager, Player p) {
        if (!p.isOp()) {
            p.sendMessage(ChatColor.RED + "You do not have the permission to use this command.");
            return;
        }
        if (gameManager.getGameState() != GameState.ACTIVE && gameManager.getGameState() != GameState.STARTING) {
            p.sendMessage(ChatColor.RED + "There is no game to stop.");
            return;
        }
        gameManager.setGameState(GameState.STOP);
    }
}
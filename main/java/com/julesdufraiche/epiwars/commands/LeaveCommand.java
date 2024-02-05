package com.julesdufraiche.epiwars.commands;

import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.manager.GameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LeaveCommand {
    public LeaveCommand(GameManager gameManager, Player player, int maxPlayers) {
        if(gameManager.getGameState() != GameState.LOBBY) return;
        if (!gameManager.delPlayerToList(player)) return;
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " left the waiting list! " + gameManager.getPlayersSize() + "/" + maxPlayers + " players");
    }
}

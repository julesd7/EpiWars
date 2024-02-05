package com.julesdufraiche.epiwars.commands;

import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.manager.GameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class JoinCommand {
    public JoinCommand(GameManager gameManager, Player player, int maxPlayers) {
        if (gameManager.getPlayersSize() >= maxPlayers) {
            player.sendMessage(ChatColor.RED + "The waiting list is full.");
            return;
        }
        if (gameManager.getGameState() != GameState.LOBBY) return;
        if (!gameManager.addPlayerToList(player)) return;
        Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " joined the waiting list! " + gameManager.getPlayersSize() + "/" + maxPlayers + " players");
    }
}

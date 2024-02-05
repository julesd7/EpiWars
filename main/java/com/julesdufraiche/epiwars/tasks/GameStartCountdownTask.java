package com.julesdufraiche.epiwars.tasks;

import com.julesdufraiche.epiwars.EpiWars;
import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.manager.GameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {

    private final GameManager gameManager;

    private int timeLeft;

    public GameStartCountdownTask(GameManager gameManager, EpiWars plugin) {
        this.gameManager = gameManager;

        timeLeft = plugin.getConfig().getInt("game.countdown", 10);
    }

    @Override
    public void run() {
        if (timeLeft <= 0) {
            cancel();
            gameManager.setGameState(GameState.ACTIVE);
            Bukkit.broadcastMessage(ChatColor.AQUA + "the game is start, good luck to everyone!");
            return;
        }
        Bukkit.broadcastMessage(ChatColor.GOLD + "" + timeLeft + " seconds until game starts!");
        timeLeft--;
    }
}

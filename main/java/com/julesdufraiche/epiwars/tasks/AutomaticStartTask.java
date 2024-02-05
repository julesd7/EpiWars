package com.julesdufraiche.epiwars.tasks;

import com.julesdufraiche.epiwars.EpiWars;
import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.manager.GameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class AutomaticStartTask extends BukkitRunnable {

    private final GameManager gameManager;
    private final int minPlayersToStart;

    private final int defaultTime;
    private int timeLeft;


    public AutomaticStartTask(GameManager gameManager) {
        this.gameManager = gameManager;

        this.minPlayersToStart = gameManager.getPlugin().getConfig().getInt("game.min-players", 2);
        this.defaultTime = gameManager.getPlugin().getConfig().getInt("game.auto-start", 60);
        this.timeLeft = this.defaultTime;
    }

    @Override
    public void run() {
        int currentPlayers = gameManager.getPlayersSize();
        if (gameManager.getGameState() == GameState.LOBBY && currentPlayers >= minPlayersToStart) {
            if (timeLeft <= 0) {
                Bukkit.getScheduler().runTask(gameManager.getPlugin(), () -> Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + ChatColor.ITALIC + "Teleporting to the waiting area, please wait."));
                cancel();
                Bukkit.getScheduler().runTaskLater(gameManager.getPlugin(), () -> gameManager.setGameState(GameState.STARTING), 40);
                return;
            }
            if (timeLeft <= 10) Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "[" + ChatColor.ITALIC + "AUTOMATIC START" + ChatColor.RESET + ChatColor.DARK_AQUA + "] " + timeLeft + " seconds until teleportation!");
            if (timeLeft == 15) Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "[" + ChatColor.ITALIC + "AUTOMATIC START" + ChatColor.RESET + ChatColor.DARK_AQUA + "] " + timeLeft + " seconds until teleportation!");
            if (timeLeft == 30) Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "[" + ChatColor.ITALIC + "AUTOMATIC START" + ChatColor.RESET + ChatColor.DARK_AQUA + "] " + timeLeft + " seconds until teleportation!");
            if (timeLeft == 60) Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "[" + ChatColor.ITALIC + "AUTOMATIC START" + ChatColor.RESET + ChatColor.DARK_AQUA + "] " + timeLeft + " seconds until teleportation!");
            timeLeft--;
        } else {
            timeLeft = defaultTime;
        }
    }
}
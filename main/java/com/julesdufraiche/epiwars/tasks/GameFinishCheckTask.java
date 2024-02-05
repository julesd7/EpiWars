package com.julesdufraiche.epiwars.tasks;

import com.julesdufraiche.epiwars.EpiWars;
import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.manager.GameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameFinishCheckTask extends BukkitRunnable {

    private final GameManager gameManager;

    private final int playerToWin;

    public GameFinishCheckTask(GameManager gameManager, EpiWars plugin) {
        this.gameManager = gameManager;

        playerToWin = plugin.getConfig().getInt("game.player-to-win");
    }

    @Override
    public void run() {
        int playersRemaining = (int) gameManager.getPlayersList().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).count();

        if (playersRemaining <= playerToWin) {
            gameManager.setGameState(GameState.WON);

            Player winner = null;
            for (Player player : gameManager.getPlayersList()) {
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    winner = player;
                    break;
                }
            }

            if (winner != null) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "Congratulations to " + winner.getName() + "! You have won the game!");
            } else {
                Bukkit.broadcastMessage(ChatColor.GREEN + "The game ended in a draw!");
            }

            gameManager.setGameState(GameState.LOBBY);
            this.cancel();
        }
    }
}
package com.julesdufraiche.epiwars.listeners;

import com.julesdufraiche.epiwars.EpiWars;
import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.manager.GameState;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;

public class SpawnListeners implements Listener {

    private final EpiWars plugin;
    private final GameManager gameManager;

    public SpawnListeners(GameManager gameManager, EpiWars plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Location location = plugin.getConfig().getLocation("spawn");

        if (location != null) {
            player.teleport(location);
        }
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (gameManager.getGameState() == GameState.ACTIVE || gameManager.getGameState() == GameState.WON) {
            Player p = event.getPlayer();
            gameManager.getPlayerManager().teleportSpec(p);
            return;
        }
        Location location = plugin.getConfig().getLocation("spawn");
        if (location != null) {
            event.setRespawnLocation(location);
        }
    }
}

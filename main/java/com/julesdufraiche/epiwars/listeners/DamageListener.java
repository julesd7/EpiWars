package com.julesdufraiche.epiwars.listeners;

import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.manager.GameState;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private final GameManager gameManager;

    public DamageListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    private void onBreak(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (gameManager.getGameState() != GameState.ACTIVE) {
                event.setCancelled(true);
            }
        }
    }
}

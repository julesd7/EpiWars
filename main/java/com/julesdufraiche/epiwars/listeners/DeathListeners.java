package com.julesdufraiche.epiwars.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListeners implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (isFallOrVoidDamage(event.getEntity().getLastDamageCause())) {
            String playerName = event.getEntity().getName();
            String deathMessage = ChatColor.RED + playerName + " died!";

            event.setDeathMessage(deathMessage);
        }
    }

    private boolean isFallOrVoidDamage(EntityDamageEvent damageEvent) {
        return damageEvent != null &&
                (damageEvent.getCause() == EntityDamageEvent.DamageCause.FALL ||
                        damageEvent.getCause() == EntityDamageEvent.DamageCause.VOID);
    }
}

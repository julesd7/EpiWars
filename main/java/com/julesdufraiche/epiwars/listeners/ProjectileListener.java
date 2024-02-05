package com.julesdufraiche.epiwars.listeners;

import com.julesdufraiche.epiwars.manager.GameManager;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        if (projectile instanceof Snowball && projectile.getShooter() instanceof Player) {
            Snowball snowball = (Snowball) projectile;
            Player shooter = (Player) snowball.getShooter();

            if (event.getHitEntity() instanceof Player) {
                Player hitPlayer = (Player) event.getHitEntity();

                hitPlayer.setVelocity(hitPlayer.getLocation().toVector().subtract(shooter.getLocation().toVector()).normalize().multiply(1.5));
            }
        }
    }
}

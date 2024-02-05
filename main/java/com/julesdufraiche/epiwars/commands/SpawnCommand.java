package com.julesdufraiche.epiwars.commands;

import com.julesdufraiche.epiwars.EpiWars;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SpawnCommand implements CommandExecutor {

    private final EpiWars plugin;

    public SpawnCommand(EpiWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            Location location = plugin.getConfig().getLocation("spawn");

            if (location != null) {
                player.teleport(location);

                player.sendMessage(ChatColor.ITALIC + "You have been teleported to the spawn point.");
            } else {
                player.sendMessage(ChatColor.RED + "There is no spawn point set. Use /setspawn to set it!");
            }

        }
        return true;
    }
}

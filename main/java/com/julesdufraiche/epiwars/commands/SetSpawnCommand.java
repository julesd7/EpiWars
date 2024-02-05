package com.julesdufraiche.epiwars.commands;

import com.julesdufraiche.epiwars.EpiWars;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SetSpawnCommand implements CommandExecutor {

    private final EpiWars plugin;

    public SetSpawnCommand(EpiWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.isOp()) {
                player.sendMessage(ChatColor.RED + "You do not have the permission to use this command.");
                return true;
            }

            Location location = player.getLocation();

            plugin.getConfig().set("spawn", location);

            plugin.saveConfig();

            player.sendMessage("Spawn location set!");
        }
        return true;
    }
}

package com.julesdufraiche.epiwars.manager;

import com.julesdufraiche.epiwars.EpiWars;
import com.julesdufraiche.epiwars.commands.JoinCommand;
import com.julesdufraiche.epiwars.commands.LeaveCommand;
import com.julesdufraiche.epiwars.commands.StartCommand;
import com.julesdufraiche.epiwars.commands.StopCommand;
import com.julesdufraiche.epiwars.map.GameMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.checkerframework.checker.nullness.qual.NonNull;

public class CommandManager  implements CommandExecutor {


    private final EpiWars plugin;
    private final GameManager gameManager;
    private final GameMap map;

    public CommandManager(GameManager gameManager, EpiWars plugin, GameMap map) {
        this.gameManager = gameManager;
        this.plugin = plugin;
        this.map = map;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, String[] args) {
        if (!plugin.getConfig().contains("spawn")) {
            commandSender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Before using " + ChatColor.ITALIC + "EpiWars" + ChatColor.RESET + " " + ChatColor.RED + ChatColor.BOLD + "commands, please initialize a spawn using the command " + ChatColor.UNDERLINE + "\"/setspawn\"" + ChatColor.RESET + ChatColor.RED + ".");
            return true;
        }
        if (args.length < 1) return false;
        switch (args[0]) {
            case "start":
                new StartCommand(gameManager, (Player) commandSender, plugin.getConfig().getInt("game.min-players"));
                return true;
            case "stop":
                new StopCommand(gameManager, (Player) commandSender);
                return true;
            case "reset":
                Player p = (Player) commandSender;
                if (!p.isOp()) {
                    p.sendMessage(ChatColor.RED + "You do not have the permission to use this command.");
                    return true;
                }
                map.restoreFromSource();
                gameManager.getChestManager().resetChests();
                return true;
            case "join":
                new JoinCommand(gameManager, (Player) commandSender, plugin.getConfig().getInt("game.map-size"));
                return true;
            case "leave":
                new LeaveCommand(gameManager, (Player) commandSender, plugin.getConfig().getInt("game.map-size"));
                return true;
            default:
                break;
        }
        return false;
    }
}

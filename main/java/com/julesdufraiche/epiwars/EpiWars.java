package com.julesdufraiche.epiwars;

import com.julesdufraiche.epiwars.commands.SetSpawnCommand;
import com.julesdufraiche.epiwars.commands.SpawnCommand;
import com.julesdufraiche.epiwars.listeners.*;
import com.julesdufraiche.epiwars.manager.ChestManager;
import com.julesdufraiche.epiwars.manager.CommandManager;
import com.julesdufraiche.epiwars.manager.GameManager;
import com.julesdufraiche.epiwars.map.GameMap;
import com.julesdufraiche.epiwars.map.LocalGameMap;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Objects;

public class EpiWars extends JavaPlugin implements Listener {

    private GameManager gameManager;
    private GameMap map;

    @Override
    public void onEnable() {
        this.createDefaults();
        ChestManager chestManager = new ChestManager(getConfig());
        this.gameManager = new GameManager(this, chestManager, map);

        getServer().getPluginManager().registerEvents(new SpawnListeners(gameManager, this), this);
        getServer().getPluginManager().registerEvents(new DeathListeners(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new DamageListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new ProjectileListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(chestManager, this);
        Objects.requireNonNull(getCommand("epiwars")).setExecutor(new CommandManager(gameManager, this, map));
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand(this));
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(this));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        String welcomeMessage = ChatColor.GREEN + "Welcome to EpiWars, " + playerName;

        event.setJoinMessage(welcomeMessage);
        System.out.println("Someone join the server.");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        String quitMessage = ChatColor.RED + playerName + " quit. Is this a rage quit ?";

        event.setQuitMessage(quitMessage);
        System.out.println("Someone quit the server.");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        gameManager.cleanup();
        map.unload();
        System.out.println("EpiWars was unloaded successfully!");
    }

    private void createDefaults() {
        if (getDataFolder().mkdirs()) {
            Bukkit.getLogger().severe("Failed in mkdir DataFolders");
        }

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
        }

        File gameMapsFolder = new File(getDataFolder(), "gameMaps");
        if (!gameMapsFolder.exists()) {
            if (!gameMapsFolder.mkdirs()) {
                Bukkit.getLogger().severe("Failed to create " + gameMapsFolder.getName());
            }
        }

        String mapName = this.getConfig().getString("game.map-name");
        map = new LocalGameMap(gameMapsFolder, mapName, true);
    }
}
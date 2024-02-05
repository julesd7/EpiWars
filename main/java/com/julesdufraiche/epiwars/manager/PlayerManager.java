package com.julesdufraiche.epiwars.manager;

import com.julesdufraiche.epiwars.EpiWars;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {

    private final EpiWars plugin;
    private int position = 1;
    private final GameManager gameManager;

    private final String mapName;


    public PlayerManager(GameManager gameManager, EpiWars plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
        this.mapName = plugin.getConfig().getString("game.map-name");
    }

    public void giveKits() {
        gameManager.getPlayersList().forEach(this::giveKit);
    }

    public void teleportPlayersToWait() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!gameManager.getPlayersList().contains(player)) {
                teleportSpec(player);
            } else {
                teleportWait(player);
            }
        }
    }

    public void teleportPlayersToGame() {
        position = 1;
        gameManager.getPlayersList().forEach(this::teleportGame);
    }

    public void teleportPlayersToLobby() {Bukkit.getOnlinePlayers().forEach(this::teleportLobby);}

    public void giveKit(Player player) {
        player.getInventory().addItem(new ItemStack(Material.STICK));
    }
    public void teleportWait(Player player) {
        String path = mapName + "-spawn.starting";
        Location location = getLocationFromConfig(path);
        if (location == null) return;
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.teleport(location);
    }

    public void teleportGame(Player player) {
        String path = mapName + "-spawn.pos" + position;
        Location location = getLocationFromConfig(path);
        if (location == null) return;
        player.teleport(location);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        position++;
    }

    public void teleportSpec(Player player) {
        String path = mapName + "-spawn.starting";
        Location location = getLocationFromConfig(path);
        if (location == null) return;
        Bukkit.getScheduler().runTask(plugin, () -> {
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(location);
        });
    }

    public void teleportLobby(Player player) {
        Location location = plugin.getConfig().getLocation("spawn");
        if (location == null) return;
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(location);
        player.setHealth(20.0);
        player.setFoodLevel(20);
    }

    private Location getLocationFromConfig(String path) {
        ConfigurationSection configSection = plugin.getConfig().getConfigurationSection(path);

        if (configSection == null) return null;

        double x = configSection.getDouble("x");
        double y = configSection.getDouble("y");
        double z = configSection.getDouble("z");

        String worldName = mapName + "_active";
        World world = Bukkit.getWorld(worldName);

        return new Location(world, x, y, z);
    }
}

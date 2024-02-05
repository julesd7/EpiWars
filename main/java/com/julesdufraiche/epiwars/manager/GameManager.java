package com.julesdufraiche.epiwars.manager;

import com.julesdufraiche.epiwars.EpiWars;
import com.julesdufraiche.epiwars.map.GameMap;
import com.julesdufraiche.epiwars.tasks.AutomaticStartTask;
import com.julesdufraiche.epiwars.tasks.GameFinishCheckTask;
import com.julesdufraiche.epiwars.tasks.GameStartCountdownTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final EpiWars plugin;
    private GameState gameState = GameState.LOBBY;

    private final PlayerManager playerManager;

    private final GameMap map;
    private final ChestManager chestManager;

    private GameStartCountdownTask gameStartCountdownTask;
    private GameFinishCheckTask gameFinishCheckTask;
    private AutomaticStartTask automaticStartTask;

    private final List<Player> players;

    public GameManager(EpiWars plugin, ChestManager chestManager, GameMap map) {
        this.plugin = plugin;
        this.map = map;
        this.chestManager = chestManager;
        this.players = new ArrayList<>();

        this.playerManager = new PlayerManager(this, plugin);
        this.automaticStartTask = new AutomaticStartTask(this);
        this.automaticStartTask.runTaskTimer(plugin, 0, 20);
    }

    public void setGameState(GameState gameState) {
        if (this.gameState == gameState || (this.gameState == GameState.ACTIVE && gameState == GameState.STARTING)) return;

        this.gameState = gameState;

        switch (gameState) {
            case LOBBY:
                if (this.automaticStartTask == null || this.automaticStartTask.isCancelled()) {
                    this.automaticStartTask = new AutomaticStartTask(this);
                    this.automaticStartTask.runTaskTimer(plugin, 0, 20);
                }
                if (this.gameStartCountdownTask != null) this.gameStartCountdownTask.cancel();
                if (this.gameFinishCheckTask != null) this.gameFinishCheckTask.cancel();
                getPlayerManager().teleportPlayersToLobby();
                this.chestManager.resetChests();
                this.players.clear();
                break;
            case STARTING:
                if (this.automaticStartTask != null) this.automaticStartTask.cancel();
                this.map.restoreFromSource();
                this.gameStartCountdownTask = new GameStartCountdownTask(this, plugin);
                this.gameStartCountdownTask.runTaskTimer(plugin, 0, 20);
                getPlayerManager().teleportPlayersToWait();
                break;
            case ACTIVE:
                if (this.gameStartCountdownTask != null) this.gameStartCountdownTask.cancel();
                getPlayerManager().teleportPlayersToGame();
                getPlayerManager().giveKits();
                this.gameFinishCheckTask = new GameFinishCheckTask(this, plugin);
                this.gameFinishCheckTask.runTaskTimer(plugin, 0, 20);
                break;
            case WON:
                if (this.gameFinishCheckTask != null) this.gameFinishCheckTask.cancel();
                break;
            case STOP:
                if (this.gameStartCountdownTask != null) this.gameStartCountdownTask.cancel();
                if (this.gameFinishCheckTask != null) this.gameFinishCheckTask.cancel();
                if (this.automaticStartTask != null) this.automaticStartTask.cancel();
                getPlayerManager().teleportPlayersToLobby();
                Bukkit.broadcastMessage(ChatColor.RED + "Game was stopped by an administrator!");
                this.setGameState(GameState.LOBBY);
                break;
            default:
                break;
        }
    }

    public void cleanup() {

    }

    public PlayerManager getPlayerManager() {return playerManager;}

    public GameState getGameState() {return gameState;}
    public boolean addPlayerToList(Player p) {
        for (Player player : players) {
            if (player.getUniqueId() == p.getUniqueId()) {
                p.sendMessage(ChatColor.RED + "You're already in the list!");
                return false;
            }
        }
        this.players.add(p);
        return true;
    }

    public boolean delPlayerToList(Player p) {
        if (this.players.isEmpty()) {
            p.sendMessage(ChatColor.RED + "You can't leave a list if you're not in a list!");
            return false;
        }
        return this.players.remove(p);
    }

    public int getPlayersSize() {
        if (this.players.isEmpty()) return 0;
        return this.players.size();
    }

    public List<Player> getPlayersList() {
        return players;
    }

    public ChestManager getChestManager() {return this.chestManager;}

    public EpiWars getPlugin() {return this.plugin;}
}

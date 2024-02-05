package com.julesdufraiche.epiwars.manager;

import com.julesdufraiche.epiwars.configurations.LootItem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ChestManager implements Listener {
    private final Set<Location> openedChests = new HashSet<>();
    private final List<LootItem> lootsItems = new ArrayList<>();

    public ChestManager(FileConfiguration lootConfig) {
        ConfigurationSection itemSection = lootConfig.getConfigurationSection("lootItems");
        if (itemSection == null) {
            Bukkit.getLogger().severe("Please setup your 'lootItems' in the config.yml!");
        }
        for (String key : itemSection.getKeys(false)) {
            ConfigurationSection section = itemSection.getConfigurationSection(key);
            lootsItems.add(new LootItem(section));
        }
    }

    @EventHandler
    private void onChestOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Chest) {
            Chest chest = (Chest) holder;
            if (hasBeenOpened(chest.getLocation())) return;

            markAsOpened(chest.getLocation());
            fill(chest.getBlockInventory());
        } else if (holder instanceof DoubleChest) {
            DoubleChest chest = (DoubleChest) holder;
            if (hasBeenOpened(chest.getLocation())) return;

            markAsOpened(chest.getLocation());
            fill(chest.getInventory());
        }
    }

    public void fill(Inventory inventory) {
        inventory.clear();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        Set<LootItem> used = new HashSet<>();

        for (int slotIndex = 0; slotIndex < inventory.getSize(); slotIndex++) {
            LootItem randomItem = lootsItems.get(
                    random.nextInt(lootsItems.size())
            );
            if (used.contains(randomItem)) continue;
            used.add(randomItem);

            if (randomItem.shouldFill(random)) {
                ItemStack itemStack = randomItem.make(random);
                inventory.setItem(slotIndex, itemStack);
            }
        }
    }

    public void markAsOpened(Location location) {
        openedChests.add(location);
    }

    public boolean hasBeenOpened(Location location) {
        return openedChests.contains(location);
    }

    public void resetChests() {
        openedChests.clear();
    }
}

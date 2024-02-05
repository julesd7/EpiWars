package com.julesdufraiche.epiwars.map;

import org.bukkit.World;

public interface GameMap {
    void load();
    void unload();
    void restoreFromSource();
    boolean isLoaded();
    World getWorld();
}

package de.dercoolejulianhd.pluginutilities.game.interfaces;

import org.bukkit.World;

public interface GameMap {

    MiniGamePlugin getPlugin();
    GameInstance getGame();
    boolean isLoaded();

    void load();
    void unload();

    World getWorld();
}

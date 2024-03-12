package de.dercoolejulianhd.minigame.bedwars.library.game.interfaces;

import de.dercoolejulianhd.minigame.bedwars.library.MiniGamePlugin;
import org.bukkit.World;

public interface GameMap {

    MiniGamePlugin getPlugin();
    GameInstance getGame();
    boolean isLoaded();

    void load();
    void unload();

    World getWorld();
}

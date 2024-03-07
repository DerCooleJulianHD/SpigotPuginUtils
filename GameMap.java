package de.dercoolejulianhd.minigameapi.api.game.map;

import de.dercoolejulianhd.minigameapi.api.MiniGame;
import de.dercoolejulianhd.minigameapi.api.game.GameInstance;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public interface GameMap {

    MiniGame getPlugin();
    GameInstance getGame();
    boolean isLoaded();

    boolean load();
    void unload();

    World getWorld();
}

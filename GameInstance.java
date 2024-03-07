package de.dercoolejulianhd.minigameapi.api.game;

import org.bukkit.*;
import de.dercoolejulianhd.minigameapi.api.game.configuration.GameConfig;
import de.dercoolejulianhd.minigameapi.api.game.map.GameMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public interface GameInstance {

    Plugin getPlugin();
    String getName();
    GameMap getMap();

    GameConfig getConfiguration();

    List<Player> getPlayers();
    Location getLobbyLocation();

    void stop();

    int getMinPlayers();
    int getMaxPlayers();

    static File getDataFolder(Plugin plugin, String name) {
        File dataFolder = plugin.getGameManager().getDataFolder();
        File folder = new File(dataFolder, name);

        if (!folder.exists()) folder.mkdir();

        return folder;
    }

}

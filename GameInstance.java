package de.dercoolejulianhd.pluginutilities.game.interfaces;

import de.dercoolejulianhd.pluginutilities.game.GameConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

public interface GameInstance {

    MiniGamePlugin getPlugin();
    String getName();
    GameMap getMap();

    GameConfig getConfiguration();

    List<Player> getPlayers();
    Location getLobbyLocation();

    void stop();

    int getMinPlayers();
    int getMaxPlayers();

    @Nullable
    static File getDataFolder(MiniGamePlugin plugin, String name) {
        File dataFolder = plugin.getGameManager().getDataFolder();
        File folder = new File(dataFolder, name);

        if (!folder.exists()) return null;

        return folder;
    }

}

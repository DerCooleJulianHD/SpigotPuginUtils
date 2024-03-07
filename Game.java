package de.dercoolejulianhd.pluginutilities.game;

import de.dercoolejulianhd.pluginutilities.game.interfaces.GameInstance;
import de.dercoolejulianhd.pluginutilities.game.interfaces.GameMap;
import de.dercoolejulianhd.pluginutilities.game.interfaces.MiniGamePlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Game implements GameInstance {

    private final MiniGamePlugin plugin;
    private final String name;
    private final GameConfig config;
    private final boolean loadOnInit;

    private GameMap map;

    private List<Player> players;

    private int minPlayers;
    private int maxPlayers;

    private Location lobbyLocation;

    public Game(MiniGamePlugin plugin, File dataFolder ,String name, boolean loadOnInit) {
        this.plugin = plugin;
        this.name = name;
        this.loadOnInit = loadOnInit;

        config = new GameConfig(plugin, dataFolder, this);

        if (loadOnInit) load();
    }

    public void load() {
        map = new LocalGameMap(plugin, this);
        players = new ArrayList<>();
        minPlayers = config.getMinPlayers();
        maxPlayers = config.getMaxPlayers();
        lobbyLocation = config.loadLocation("lobby");
    }

    @Override
    public void stop() {
        if (map == null) return;
        map.unload();
    }

    @Override
    public MiniGamePlugin getPlugin() {
        return plugin;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GameMap getMap() {
        return map;
    }

    @Override
    public GameConfig getConfiguration() {
        return config;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    @Override
    public int getMinPlayers() {
        return minPlayers;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isLoadOnInit() {
        return loadOnInit;
    }
}

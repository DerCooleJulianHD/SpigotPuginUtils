package de.dercoolejulianhd.minigameapi.api.game;

import org.bukkit.*
import de.dercoolejulianhd.minigameapi.api.game.configuration.GameConfig;
import de.dercoolejulianhd.minigameapi.api.game.map.GameMap;
import de.dercoolejulianhd.minigameapi.api.game.map.LocalGameMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Game implements GameInstance {

    private final Plugin plugin;
    public final String name;
    public final boolean loadOnInit;
    private GameMap map;
    private GameConfig config;
    private List<Player> players;

    private int minPlayers;
    private int maxPlayers;

    private Location lobbyLocation;

    public Game(Plugin miniGame, String name, boolean loadOnInit) {
        this.plugin = miniGame;
        this.name = name;
        this.loadOnInit = loadOnInit;

        if (!loadOnInit) return;

        File dataFolder = GameInstance.getDataFolder(miniGame, name);
        config = new GameConfig(plugin, dataFolder, this);

        load();
    }

    public void load() {
        map = new LocalGameMap(plugin, this);
        players = new ArrayList<>();
        minPlayers = config.getMinPlayers();
        maxPlayers = config.getMaxPlayers();
        lobbyLocation = config.loadLocation("lobby");
    }

    @Override
    public MiniGame getPlugin() {
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
    public void stop() {
        if (map == null) return;
        map.unload();
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

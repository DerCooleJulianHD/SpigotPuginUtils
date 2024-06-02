package com.plugin.utils.minigame.api.game;

import com.plugin.utils.minigame.api.IMinigameAPI;
import com.plugin.utils.minigame.api.config.Config;
import com.plugin.utils.minigame.api.listener.IListenerBundle;
import com.plugin.utils.minigame.api.utils.Spectator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Game implements IGameInstance {

    private final Plugin plugin;
    private final File dataFolder;
    private final Collection<Player> players;
    private final Collection<Spectator> spectators;

    private String name;
    private IGameMap map;
    private Config config;
    private GameState state;
    private IListenerBundle listenerBundle;

    public Game(Plugin plugin, File dataFolder) {
        this.plugin = plugin;
        this.dataFolder = dataFolder;
        this.players = new ArrayList<>();
        this.spectators = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public File getDataFolder() {
        return dataFolder;
    }

    @Override
    public IGameMap getMap() {
        return map;
    }

    public void setMap(IGameMap map) {
        this.map = map;
    }

    @Override
    public Config getConfiguration() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void setState(GameState state) {
        this.state = state;
    }

    @Override
    public Collection<Player> getPlayers() {
        return players;
    }

    @Override
    public Collection<Spectator> getSpectators() {
        return spectators;
    }

    @Override
    public void setSpectator(Player player) {
        Spectator spectator = new Spectator(player);
        this.spectators.add(spectator);
    }

    @Override
    public IListenerBundle getListeners() {
        return listenerBundle;
    }

    public void setListenerBundle(IListenerBundle listenerBundle) {
        this.listenerBundle = listenerBundle;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public static boolean exists(Plugin plugin, String name) {
        try {
            IMinigameAPI minigameAPI = (IMinigameAPI) plugin;

            File gameCollectionFolder = minigameAPI.getGameLoader().getDestinationFolder();
            if (gameCollectionFolder == null || (!(gameCollectionFolder.exists()))) return false;

            File destination = new File(gameCollectionFolder, name);
            return destination.exists();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
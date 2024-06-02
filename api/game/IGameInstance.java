package com.plugin.utils.minigame.api.game;

import com.plugin.utils.minigame.api.config.Config;
import com.plugin.utils.minigame.api.listener.IListenerBundle;
import com.plugin.utils.minigame.api.utils.Spectator;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Collection;

public interface IGameInstance {

    String getName();
    File getDataFolder();
    IGameMap getMap();
    Config getConfiguration();
    GameState getState();
    void setState(GameState state);
    Collection<Player> getPlayers();
    Collection<Spectator> getSpectators();
    void setSpectator(Player player);
    IListenerBundle getListeners();
}

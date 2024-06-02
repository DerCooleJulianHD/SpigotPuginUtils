package com.plugin.utils.minigame.api.listener;

import com.plugin.utils.minigame.api.game.IGameInstance;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class GameListener {

    public static String KEY;

    public final Plugin plugin;
    public final IGameInstance game;

    public GameListener(String key, Plugin plugin, IGameInstance game) {
        GameListener.KEY = key;
        this.plugin = plugin;
        this.game = game;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public IGameInstance getGame() {
        return game;
    }

    public abstract Listener getBukkitListener();
}

package com.plugin.utils.minigame.api.listener;

import com.plugin.utils.minigame.api.game.IGameInstance;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

public class ListenerBundle implements IListenerBundle {

    private final Plugin plugin;
    private final IGameInstance game;
    private final Map<String, GameListener> listeners;

    public ListenerBundle(Plugin plugin, IGameInstance game) {
        this.plugin = plugin;
        this.game = game;
        this.listeners = new HashMap<>();
    }

    @Override
    public void registerListener(String key, GameListener listener) {
        if (isActive(key) || isActive(listener)) return;
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listener.getBukkitListener(), plugin);
        listeners.put(key, listener);
    }

    @Override
    public void unregisterListener(String key) {
        if (!(isActive(key))) return;
        GameListener listener = listeners.get(key);
        HandlerList.unregisterAll(listener.getBukkitListener());
        listeners.remove(key);
    }

    @Override
    public void unregisterAll() {
        if (listeners.isEmpty()) return;
        listeners.keySet().forEach(this::unregisterListener);
    }

    @Override
    public boolean isActive(GameListener listener) {
        Map<String, GameListener> activeListeners = getActiveListeners();
        if (activeListeners.isEmpty()) return false;
        return activeListeners.containsValue(listener);
    }

    @Override
    public boolean isActive(String key) {
        Map<String, GameListener> activeListeners = getActiveListeners();
        if (activeListeners.isEmpty()) return false;
        return activeListeners.containsKey(key);
    }

    @Override
    public Map<String, GameListener> getActiveListeners(){
        return listeners;
    }

    public IGameInstance getGame() {
        return game;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}

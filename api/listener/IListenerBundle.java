package com.plugin.utils.minigame.api.listener;

import java.util.Collection;
import java.util.Map;

public interface IListenerBundle {
    
    Map<String, GameListener> getActiveListeners();
    void registerListener(String key, GameListener listener);
    void unregisterListener(String key);
    void unregisterAll();
    boolean isActive(GameListener listener);
    boolean isActive(String key);
}

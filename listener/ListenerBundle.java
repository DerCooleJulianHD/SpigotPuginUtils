package de.dercoolejulianhd.plugin.utils.listener;

import jdk.jfr.Description;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: Container with Listeners that Manages registration and unregistration directly
 * @since 1.0
 */
@Description(" /* Container with Listeners that Manages registration and unregistration directly */ ")
public class ListenerBundle {

    private final Plugin plugin;
    private final Map<String, Listener> listeners;

    public ListenerBundle(Plugin plugin) {
        this.plugin = plugin;
        this.listeners = new HashMap<>();
    }


    /**
     * Description: calls a Listener Registration and activation
     */
    @Description(" /* calls a Listener Registration and activation */ ")
    public void registerListener(String key, Listener listener) {
        if (isActive(key) || isActive(listener)) return;
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listener, plugin);
        listeners.put(key, listener);
    }


    /**
     * Description: unregister a specific Listener when contains the ActiveListeners
     */
    @Description(" /* unregister a specific Listener when contains the ActiveListeners */ ")
    public void unregisterListener(String key) {
        if (!(isActive(key))) return;
        Listener listener = listeners.get(key);
        HandlerList.unregisterAll(listener);
        listeners.remove(key);
    }


    /**
     * Description: unregister all Listeners from ActiveListeners Collection.
     */
    @Description(" /* unregister all Listeners from ActiveListeners Collection */ ")
    public void unregisterAll() {
        if (listeners.isEmpty()) return;
        listeners.keySet().forEach(this::unregisterListener);
    }


    /**
     * Description: returns the Collection with Listeners that are active on Server and Saved.
     * @return true or false
     */
    @Description(" /* returns true when a Listener contains the ActiveListeners Collection and is Active on Server */ ")
    public boolean isActive(Listener listener) {
        if (listeners.isEmpty()) return false;
        return listeners.containsValue(listener);
    }


    /**
     * Description: returns the Collection with Listeners that are active on Server and Saved.
     * @return true or false
     */
    @Description(" /* returns true when a Listener contains the ActiveListeners Collection and is Active on Server */ ")
    public boolean isActive(String key) {
        if (listeners.isEmpty()) return false;
        return listeners.containsKey(key);
    }


    /**
     * Description: returns the Collection with Listeners that are active on Server and Saved.
     * @return java.util.Map<String, org.bukkit.event.Listener>
     */
    @Description(" /* returns the Collection with Listeners that are active on Server and Saved  */ ")
    public Map<String, Listener> getActiveListeners(){
        return listeners;
    }


    /**
     * Description: returns the Plugin which is Executing.
     * @return org.bukkit.plugin.Plugin
     */
    @Description(" /* returns the Plugin which is Executing */ ")
    public Plugin getPlugin() {
        return plugin;
    }
}

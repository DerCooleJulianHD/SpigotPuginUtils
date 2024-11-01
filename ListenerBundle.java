package de.dercoolejulianhd.modulelibrary.listenerbundle;

import jdk.jfr.Description;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: Container with Listeners that Manages registration and unregistration directly
 * @since 1.0
 */
@Description(" /* Container with Listeners that Manages registration and unregistration directly */ ")
public final class ListenerBundle {
    public final Logger logger = LogManager.getLogger(ListenerBundle.class);

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
    public void register(String key, Listener listener) {
        if (this.isRegistered(key) || this.isRegistered(listener)) {
            logger.log(Level.ERROR, "Listener registration not possible. Listener already registered.");
            return;
        }

        PluginManager pluginManager = this.plugin.getServer().getPluginManager();
        pluginManager.registerEvents(listener, this.plugin);
        this.listeners.put(key, listener);
    }


    /**
     * Description: unregister a specific Listener when contains the ActiveListeners
     */
    @Description(" /* unregister a specific Listener when contains the ActiveListeners */ ")
    public void unregister(String key) {
        if (!(this.isRegistered(key))){
            logger.log(Level.ERROR, "Listener unregistration not possible. Listener not found or not registered.");
            return;
        }

        Listener listener = this.listeners.get(key);
        HandlerList.unregisterAll(listener);
        this.listeners.remove(key);
    }


    /**
     * Description: unregister all Listeners from ActiveListeners Collection.
     */
    @Description(" /* unregister all Listeners from ActiveListeners Collection */ ")
    public void unregisterAll() {
        if (this.listeners.isEmpty()) return;
        this.listeners.keySet().forEach(this::unregister);
    }


    /**
     * Description: returns the Collection with Listeners that are active on Server and Saved.
     * @return true or false
     */
    @Description(" /* returns true when a Listener contains the ActiveListeners Collection and is Active on Server */ ")
    public boolean isRegistered(Listener listener) {
        if (this.listeners.isEmpty()) return false;
        return this.listeners.containsValue(listener);
    }


    /**
     * Description: returns the Collection with Listeners that are active on Server and Saved.
     * @return true or false
     */
    @Description(" /* returns true when a Listener contains the ActiveListeners Collection and is Active on Server */ ")
    public boolean isRegistered(String key) {
        if (this.listeners.isEmpty()) return false;
        return this.listeners.containsKey(key);
    }


    /**
     * Description: returns the specific Listener which contains the ActiveListeners Collection and registered before.
     * @return org.bukkit.event.Listener
     */
    @Nullable
    @Description(" /* returns the specific Listener which contains the ActiveListeners Collection and registered before */ ")
    public Listener getRegisteredListener(String key) {
        if (!(this.isRegistered(key))) return null;
        return this.listeners.get(key);
    }


    /**
     * Description: returns true when Collection doesn't contain any Listeners.
     * @return true or false
     */
    @Description(" /* returns true when Collection doesn't contain any Listeners. */ ")
    public boolean isEmpty() {
        return this.listeners.isEmpty();
    }


    /**
     * Description: returns the Collection with Listeners that are active on Server and Saved.
     * @return java.util.Map<String, org.bukkit.event.Listener>
     */
    @Description(" /* returns the Collection with Listeners that are active on Server and Saved  */ ")
    public Map<String, Listener> getActiveListeners(){
        return this.listeners;
    }


    /**
     * Description: returns the Plugin which is Executing.
     * @return org.bukkit.plugin.Plugin
     */
    @Description(" /* returns the Plugin which is Executing */ ")
    public Plugin getPlugin() {
        return this.plugin;
    }
}

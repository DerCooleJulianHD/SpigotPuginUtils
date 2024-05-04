package de.dercoolejulianhd.plugin_utilites.listener;

import jdk.jfr.Description;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Object that saves and contains
 * active Event-Listeners from Plugin which is using this.
 */
@Description(" /* Object that saves and contains active Event-Listeners from Plugin which is using this */ ")
public class ListenerBundle {

    final List<Listener> listeners;

    public ListenerBundle() {
        this.listeners = new ArrayList<>();
    }


    /**
     * Description:
     * adds and register a Listener to a List of Listeners.
     */
    @Description(" /* adds and register a Listener to a List of Listeners */ ")
    public void addListener(Listener listener, Plugin plugin){
        PluginManager pluginManager = Bukkit.getPluginManager();

        if (this.listeners.contains(listener)) {
            return;
        }

        pluginManager.registerEvents(listener, plugin);
        this.listeners.add(listener);
    }


    /**
     * Description:
     * removes and unregister a Listener which was set to the List before.
     */
    @Description(" /* removes and unregister a Listener which was set to the List before */ ")
    public void removeListener(Listener listener) {

        if (!this.listeners.contains(listener)) {
            return;
        }

        HandlerList.unregisterAll(listener);
        this.listeners.remove(listener);
    }


    /**
     * Description:
     * returns the List which contains the saved Event-Listeners.
     */
    @Description(" /* returns the List which contains the saved Event-Listeners */ ")
    public List<Listener> listeners() {
        return listeners;
    }
}

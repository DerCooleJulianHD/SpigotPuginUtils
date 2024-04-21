package de.dercoolejulianhd.plugin_utilites.listener;


import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

public class ListenerManager {

    final Plugin plugin;
    final Map<String, ListenerBundle> bundles;

    public ListenerManager(Plugin plugin) {
        this.plugin = plugin;
        this.bundles = new HashMap<>();
    }

    public void registerBundle(String key, ListenerBundle bundle) {

        if (this.bundles.containsKey(key)) {
            return;
        }

        this.bundles.put(key, bundle);

        PluginManager pluginManager = plugin.getServer().getPluginManager();
        bundle.listeners().forEach(listener -> {
            pluginManager.registerEvents(listener, plugin);
        });
    }

    public void unregisterBundle(String key) {

        if (!this.bundles.containsKey(key)) {
            return;
        }

        ListenerBundle bundle = this.bundles.get(key);

        bundle.listeners().forEach(listener -> {
            HandlerList.unregisterAll(listener);
            bundle.removeListener(listener);
        });

        bundles.remove(key);
    }

    public boolean isActive(String registrationName) {
        return this.bundles.containsKey(registrationName);
    }
}

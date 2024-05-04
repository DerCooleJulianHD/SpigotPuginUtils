package de.dercoolejulianhd.plugin_utilites.listener;

import org.bukkit.plugin.Plugin;
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

        if (this.isActive(key)) {
            return;
        }

        this.bundles.put(key, bundle);
    }

    public void unregisterBundle(String key) {

        if (!this.isActive(key)) {
            return;
        }

        this.bundles.remove(key);
    }

    public boolean isActive(String registrationName) {
        return this.bundles.containsKey(registrationName);
    }

    public Plugin getPlugin() {
        return plugin;
    }
}

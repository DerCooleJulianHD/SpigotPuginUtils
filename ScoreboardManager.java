package de.dercoolejulianhd.module.animated_scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    private final Plugin plugin;
    private final Map<Player, ScoreboardBuilder> map;

    public ScoreboardManager(Plugin plugin) {
        this.plugin = plugin;
        map = new HashMap<>();
    }

    public ScoreboardBuilder getScoreboard(Player player) {
        return map.get(player);
    }

    public void setScoreboard(Player player, ScoreboardBuilder builder) {
        map.put(player, builder);
    }

    public Map<Player, ScoreboardBuilder> map() {
        return map;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}

package de.dercoolejulianhd.plugin.utils.minigameapi.scoreboard;

import de.dercoolejulianhd.plugin.utils.minigameapi.minigameplugin.MinigamePlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import src.de.dercoolejulianhd.plugin.utils.scoreboard.LocalScoreboard;
import src.de.dercoolejulianhd.plugin.utils.scoreboard.ScoreboardBuilder;

public class ScoreboardManager {

    private final MinigamePlugin plugin;
    private final ScoreboardBuilder builder;

    public ScoreboardManager(MinigamePlugin plugin) {
        this.plugin = plugin;
        this.builder = new LocalScoreboard(plugin.getPlugin(), ChatColor.RED.toString() + ChatColor.BOLD + "  BEDWARS  ");
    }

    public void setScoreboard(Player player) {
        player.setScoreboard(this.builder.getScoreboard());
    }

    public MinigamePlugin getPlugin() {
        return plugin;
    }

    public ScoreboardBuilder getBuilder() {
        return builder;
    }
}

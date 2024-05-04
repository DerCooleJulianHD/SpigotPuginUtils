package de.dercoolejulianhd.plugin_utilites.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    final Plugin plugin;

    public ScoreboardManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public Scoreboard getBaseScoreboard(Player player) {
        ScoreboardBuilder builder = new ScoreboardBuilder(player);
        Scoreboard scoreboard = builder.getScoreboard();

        if (scoreboard.getObjective("display") == null){
            Objective objective = scoreboard.registerNewObjective("display", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            builder.registerObjective(objective);
        }

        return scoreboard;
    }

    public Scoreboard getOwnScoreboard(Player player) {
        ScoreboardBuilder builder = new ScoreboardBuilder(player);
        Scoreboard scoreboard = builder.getScoreboard();

        if (scoreboard.getObjective("display:{" + player.getUniqueId() + "}") != null){
            scoreboard.getObjective("display:{" + player.getUniqueId() + "}").unregister();
        }

        Objective objective = scoreboard.registerNewObjective("display:{" + player.getUniqueId() + "}", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        builder.registerObjective(objective);

        return scoreboard;
    }

}

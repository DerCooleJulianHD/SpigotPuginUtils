package de.dercoolejulianhd.plugin_utilites.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashSet;
import java.util.Set;

public class ScoreboardBuilder {

    final Player player;

    final Scoreboard scoreboard;
    final Set<Objective> objectives;


    public ScoreboardBuilder(Player player) {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objectives = new HashSet<>();
    }

    public void registerObjective(Objective objective) {

        if (this.scoreboard.getObjectives().contains(objective)) {
            return;
        }

        this.scoreboard.getObjectives().add(objective);
        this.objectives.add(objective);
    }

    public void setPlayer() {

        if (player.getScoreboard().equals(this.scoreboard)) {
            return;
        }

        if (this.scoreboard.getObjectives().isEmpty()) {
            return;
        }

        player.setScoreboard(this.scoreboard);
    }

    public Player getPlayer() {
        return player;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Set<Objective> getObjectives() {
        return objectives;
    }
}

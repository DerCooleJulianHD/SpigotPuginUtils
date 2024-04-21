package de.dercoolejulianhd.plugin_utilites.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.util.*;

public abstract class ScoreboardBuilder {

    final Plugin plugin;
    protected Scoreboard bukkitScoreboard;
    protected final boolean overridden;
    protected Objective objective;

    final Set<Integer> scores;
    final List<Player> players;

    public ScoreboardBuilder(Plugin plugin, String displayName, DisplaySlot type, boolean override) {
        this.plugin = plugin;

        this.overridden = override;
        this.scores = new HashSet<>();
        this.players = new ArrayList<>();

        this.createScoreboard(displayName, type, override);
    }

    void createScoreboard(String displayName, DisplaySlot type, boolean override) {
        this.bukkitScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = bukkitScoreboard.getObjective("display");

        if (objective == null) {
            this.objective = bukkitScoreboard.registerNewObjective("display", "dummy");
        }

        if (override) {
            this.objective.unregister();
        }

        this.objective.setDisplaySlot(type);
        this.setDisplayName(displayName);
    }

    public abstract void update();

    public void setScoreboard(Player player) {

        if (player.getScoreboard().equals(this.bukkitScoreboard)) {
            return;
        }

        player.setScoreboard(this.bukkitScoreboard);
    }

    public void setScore(String content, int score) {
        Team team = getTeamFromScore(score);

        if (team == null) {
            return;
        }

        team.setPrefix(content);
        showScore(score);
    }

    @Nullable
    EntryName getEntryNameFromScore(int score) {
        for (EntryName name : EntryName.values()) {
            if (score == name.getEntry()) {
                return name;
            }

        }
        return null;
    }

    @Nullable
    Team getTeamFromScore(int score) {
        EntryName name = getEntryNameFromScore(score);

        if (name == null) {
            return null;
        }

        Team team = bukkitScoreboard.getEntryTeam(name.getEntryName());

        if (team != null) {
            return team;
        }

        team = bukkitScoreboard.registerNewTeam(name.name());
        team.addEntry(name.getEntryName());
        return team;
    }

    void showScore(int score) {
        EntryName name = getEntryNameFromScore(score);

        if (name == null) {
            return;
        }

        if (objective.getScore(name.getEntryName()).isScoreSet()) {
            return;
        }

        objective.getScore(name.getEntryName()).setScore(score);
    }

    void hideScore(int score) {
        EntryName name = getEntryNameFromScore(score);

        if (name == null) {
            return;
        }

        if (!objective.getScore(name.getEntryName()).isScoreSet()) {
            return;
        }

        bukkitScoreboard.resetScores(name.getEntryName());
    }

    public void setDisplayName(String displayName) {
        objective.setDisplayName(displayName);
    }

    public void removeScore(int score) {
        hideScore(score);
    }

    @Deprecated
    public void setScores(Map<String, Integer> map) {
        map.forEach(this::setScore);
    }

    public Set<Integer> getScores() {
        return scores;
    }

    public Scoreboard getBukkitScoreboard() {
        return bukkitScoreboard;
    }

    public boolean isOverridden() {
        return overridden;
    }

    public Objective getObjective() {
        return objective;
    }

    public List<Player> getPlayers() {
        return players;
    }
}

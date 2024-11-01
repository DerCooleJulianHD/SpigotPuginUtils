package de.dercoolejulianhd.modulelibrary.scoreboard;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardBuilder {

    private final Plugin plugin;
    private final Scoreboard scoreboard;
    private final Map<String, Objective> objectives;

    public ScoreboardBuilder(Plugin plugin, String displayName) {
        this.plugin = plugin;

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objectives = new HashMap<>();

        if (this.getMainObjective() == null) this.scoreboard.registerNewObjective("main", "dummy");
    }

    public void update(Objective obj, int score, String content) {}

    public void setDisplayName(Objective obj, String displayName) {
        obj.setDisplayName(displayName);
    }

    @NotNull
    public ScoreEntry getScoreEntry(int score) {
        for (ScoreEntry entry : ScoreEntry.values()) {
            if (score == entry.getEntry()) {
                return entry;
            }
        }

        return null;
    }

    @NotNull
    public Team getScoreEntryTeam(int score) {
        ScoreEntry entry = this.getScoreEntry(score);
        if (entry == null) return null;

        Team team = this.scoreboard.getEntryTeam(entry.getEntryName());
        if (team != null) return team;

        team = this.scoreboard.registerNewTeam(entry.name());
        team.addEntry(entry.getEntryName());
        return team;
    }

    public void showScore(Objective obj, int score) {
        ScoreEntry entry = this.getScoreEntry(score);
        if (entry == null) return;
        if (obj.getScore(entry.getEntryName()).isScoreSet()) return;
        obj.getScore(entry.getEntryName()).setScore(score);
    }

    private void hideScore(Objective obj, int score) {
        ScoreEntry entry = this.getScoreEntry(score);
        if (entry == null) return;
        if (!obj.getScore(entry.getEntryName()).isScoreSet()) return;
        scoreboard.resetScores(entry.getEntryName());
    }

    public void removeScore(Objective obj, int score) {
        Team team = this.getScoreEntryTeam(score);
        if (team == null) return;
        team.unregister();
        hideScore(obj, score);
    }

    public void setScore(Objective obj, String content, int score) {
        Team team = this.getScoreEntryTeam(score);
        if (team == null) return;
        team.setDisplayName(content);
        showScore(obj, score);
    }

    public void addObjective(String key, String criteria, String displayName, DisplaySlot slot) {
        if (isObjectiveExists(key)) return;
        Objective obj = this.scoreboard.registerNewObjective("obj:{" + key + "}", criteria);
        obj.setDisplayName(displayName);
        obj.setDisplaySlot(slot);
        this.objectives.put(key, obj);
    }

    public void removeObjective(String key) {
        if (!isObjectiveExists(key)) return;
        Objective obj = this.scoreboard.getObjective("obj:{" + key + "}");
        obj.unregister();
        this.objectives.remove(key, obj);
    }

    public Objective getRegisteredObjective(String key) {
        if (!isObjectiveExists(key)) return null;
        return this.objectives.get(key);
    }

    public boolean isObjectiveExists(String key) {
        boolean objExist = this.scoreboard.getObjective("obj:{" + key + "}") != null;
        boolean isAdded = this.objectives.containsKey(key);
        return (objExist && isAdded);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Map<String, Objective> getRegisteredObjectives() {
        return objectives;
    }

    public Objective getMainObjective() {
        return this.scoreboard.getObjective("main");
    }
}

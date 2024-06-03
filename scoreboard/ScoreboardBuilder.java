package de.dercoolejulianhd.plugin.utils.scoreboard;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public abstract class ScoreboardBuilder {

    private final Plugin plugin;
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Map<String, Objective> objectives;
    private final boolean local;

    public ScoreboardBuilder(Plugin plugin, String displayName, boolean local) {
        this.plugin = plugin;
        this.local = local;

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objectives = new HashMap<>();

        Objective obj = this.scoreboard.getObjective("main");
        if (!local) {
            if (obj != null) obj.unregister();
            this.objective = scoreboard.registerNewObjective("main", "dummy");
        } else {
            this.objective = obj;
        }
        objective.setDisplayName(displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public abstract void update(int score, String content);

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

        Team team = scoreboard.getEntryTeam(entry.getEntryName());
        if (team != null) return team;

        team = scoreboard.registerNewTeam(entry.name());
        team.addEntry(entry.getEntryName());
        return team;
    }

    public void showScore(int score) {
        ScoreEntry entry = this.getScoreEntry(score);
        if (entry == null) return;
        if (objective.getScore(entry.getEntryName()).isScoreSet()) return;
        objective.getScore(entry.getEntryName()).setScore(score);
    }

    private void hideScore(int score) {
        ScoreEntry entry = this.getScoreEntry(score);
        if (entry == null) return;
        if (!objective.getScore(entry.getEntryName()).isScoreSet()) return;
        scoreboard.resetScores(entry.getEntryName());
    }

    public void removeScore(int score) {
        Team team = this.getScoreEntryTeam(score);
        if (team == null) return;
        team.unregister();
        hideScore(score);
    }

    public void setScore(String content, int score) {
        Team team = this.getScoreEntryTeam(score);
        if (team == null) return;
        team.setDisplayName(content);
        showScore(score);
    }

    public void addObjective(String key, String displayName, DisplaySlot slot) {
        if (isObjectiveExists(key)) return;
        Objective obj = this.scoreboard.registerNewObjective("obj:{" + key + "}", "dummy");
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

    public Objective getAddedObjective(String key) {
        if (!isObjectiveExists(key)) return null;
        return this.objectives.get(key);
    }

    public boolean isObjectiveExists(String key) {
        boolean objExist = this.scoreboard.getObjective("obj:{" + key + "}") != null;
        boolean isAdded = this.objectives.containsKey(key);
        return (objExist && isAdded);
    }

    public void setDisplayName(String s) {
        this.objective.setDisplayName(s);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Map<String, Objective> getObjectives() {
        return objectives;
    }

    public Objective getObjective() {
        return objective;
    }

    public boolean isLocal() {
        return local;
    }
}

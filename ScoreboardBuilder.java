package de.dercoolejulianhd.module.animated_scoreboard;

import de.dercoolejulianhd.module.animated_scoreboard.utils.EntryName;
import de.dercoolejulianhd.module.animated_scoreboard.utils.ScoreboardConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public abstract class ScoreboardBuilder {

    private final ScoreboardConfig config;

    protected final Scoreboard bukkitScoreboard;
    protected final boolean overridden;
    private final Set<Integer> scores;

    protected Objective objective;

    private final Player player;

    public ScoreboardBuilder(Plugin plugin, Player player, String displayName, DisplaySlot type, boolean override) {
        this.player = player;
        this.overridden = override;
        this.config = new ScoreboardConfig((JavaPlugin) plugin);
        this.scores = new HashSet<>();

        if (player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }

        this.bukkitScoreboard = player.getScoreboard();

        this.objective = bukkitScoreboard.getObjective("display");

        if (objective == null) {
            this.objective = bukkitScoreboard.registerNewObjective("display", "dummy");
        }

        if (override) {
            objective.unregister();
        }

        objective.setDisplaySlot(type);
        this.setDisplayName(displayName);

        this.createScoreboard();
        this.run(plugin);
    }

    public abstract void createScoreboard();
    public abstract void update();

    public void setDisplayName(String displayName) {
        objective.setDisplayName(displayName);
    }

    public void setScore(String content, int score) {
        Team team = getTeamFromScore(score);

        if (team == null) {
            return;
        }

        team.setPrefix(content);
        showScore(score);
    }

    public void removeScore(int score) {
        hideScore(score);
    }

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

    public Player getPlayer() {
        return player;
    }

    public ScoreboardConfig getConfig() {
        return config;
    }

    private EntryName getEntryNameFromScore(int score) {
        for (EntryName name : EntryName.values()) {
            if (score == name.getEntry()) {
                return name;
            }

        }
        return null;
    }

    private Team getTeamFromScore(int score) {
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

    private void showScore(int score) {
        EntryName name = getEntryNameFromScore(score);

        if (name == null) {
            return;
        }

        if (objective.getScore(name.getEntryName()).isScoreSet()) {
            return;
        }

        objective.getScore(name.getEntryName()).setScore(score);
    }

    private void hideScore(int score) {
        EntryName name = getEntryNameFromScore(score);

        if (name == null) {
            return;
        }

        if (!objective.getScore(name.getEntryName()).isScoreSet()) {
            return;
        }

        bukkitScoreboard.resetScores(name.getEntryName());
    }

    private void run(Plugin plugin) {

        scores.forEach(score -> {

            if (scores.isEmpty()) {
                return;
            }

            ConfigurationSection section = config.getConfiguration().getConfigurationSection("contents").getConfigurationSection(String.valueOf(score));

            if (section == null) {
                return;
            }

            int update_intervall = section.getInt("update-intervall");

            if (update_intervall > -1) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        update();
                    }
                }.runTaskTimer(plugin, update_intervall, update_intervall);
            }
        });
    }
}

package de.dercoolejulianhd.minigame.bedwars.library.utils.scoreboard;

import de.dercoolejulianhd.minigame.bedwars.library.utils.scoreboard.utils.EntryName;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public abstract class ScoreboardBuilder {

    private final Scoreboard scoreboard;
    private final Objective objective;

    public ScoreboardBuilder(String displayName) {

        this.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        if (scoreboard.getObjective("display") != null) Objects.requireNonNull(scoreboard.getObjective("display")).unregister();
        this.objective = scoreboard.registerNewObjective("display", Criteria.DUMMY, displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        createScoreboard();
    }

    public void setPlayer(Player player) {
        if (player.getScoreboard().equals(scoreboard)) return;
        player.setScoreboard(scoreboard);
    }

    public void setScore(String content, int score){

        Team team = getTeamByScore(score);

        if (team == null) return;

        team.setPrefix(content);
        showScore(score);
    }

    public void removeScore(int score) {
        hideScore(score);
    }

    public abstract void createLobbyScoreboard();
    public abstract void createIngameScoreboard();

    private EntryName getEntryNameByScore(int score) {
        for (EntryName name : EntryName.values()) {
            if (score == name.getEntry()) return name;
        }

        return null;
    }

    private Team getTeamByScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if (name == null) return null;

        Team team = scoreboard.getEntryTeam(name.getEntryName());

        if (team != null) return team;

        team = scoreboard.registerNewTeam(name.name());
        team.addEntry(name.getEntryName());

        return team;
    }

    private void showScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if (name == null) return;
        if (objective.getScore(name.getEntryName()).isScoreSet()) return;

        objective.getScore(name.getEntryName()).setScore(score);
    }

    private void hideScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if (name == null) return;
        if (!objective.getScore(name.getEntryName()).isScoreSet()) return;

        scoreboard.resetScores(name.getEntryName());
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }
}

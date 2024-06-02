package com.plugin.utils.minigame.api.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Spectator {

    public static final String metadata = "team:spectator_player";
    private final Player player;
    private final Team team;

    public Spectator(Player player) {
        this.player = player;
        Scoreboard scoreboard = player.getScoreboard();

        Team team = scoreboard.getTeam(metadata);
        if (team == null) team = scoreboard.registerNewTeam(metadata);
        this.team = team;

        team.addEntry(player.getName());
        player.setGameMode(GameMode.SPECTATOR);
    }

    public Player getPlayer() {
        return player;
    }

    public Team getScoreboardTeam() {
        return team;
    }
}

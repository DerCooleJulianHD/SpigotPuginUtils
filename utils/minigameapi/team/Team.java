package de.dercoolejulianhd.plugin.utils.minigameapi.team;

import de.dercoolejulianhd.plugin.utils.minigameapi.game.Game;
import de.dercoolejulianhd.plugin.utils.minigameapi.minigameplugin.MinigamePlugin;
import de.dercoolejulianhd.plugin.utils.minigameapi.scoreboard.ScoreboardManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import src.de.dercoolejulianhd.plugin.utils.scoreboard.ScoreboardBuilder;

import java.util.ArrayList;
import java.util.Collection;

public class Team {

    private final MinigamePlugin plugin;
    private final Game game;
    private final String name;
    private final TeamColor color;
    private final int maxPlayers;
    private final org.bukkit.scoreboard.Team team;
    private final Collection<Player> playerList;

    private TargetBlock targetBlock;

    public Team(MinigamePlugin plugin, Game game, String name) {
        this.plugin = plugin;
        this.game = game;
        this.name = name;
        this.color = TeamColor.valueOf("RED");
        this.maxPlayers = 0;
        this.team = this.buildScoreboardTeam();
        this.playerList = new ArrayList<>();
        this.targetBlock = new TargetBlock();
    }

    public org.bukkit.scoreboard.Team buildScoreboardTeam() {
        ScoreboardManager scoreboardManager = plugin.getScoreboardManager();
        ScoreboardBuilder builder = scoreboardManager.getBuilder();
        Scoreboard scoreboard = builder.getScoreboard();
        String ID = this.ID();
        org.bukkit.scoreboard.Team team = scoreboard.getTeam(ID);
        if (team != null) return team;
        team = scoreboard.registerNewTeam(ID);
        team.setPrefix(color.getChatColor().toString());
        team.setCanSeeFriendlyInvisibles(false);
        team.setAllowFriendlyFire(false);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        return team;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return playerList.size();
    }

    public final String ID() {
        return "team:{" + game.getName() + "}:" + name;
    }

    public MinigamePlugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }

    public org.bukkit.scoreboard.Team getScoreboardTeam() {
        return team;
    }

    public TargetBlock getTarget() {
        return targetBlock;
    }

    public void setTarget(Block block) {
        this.targetBlock = new TargetBlock();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Collection<Player> getPlayers() {
        return playerList;
    }
}

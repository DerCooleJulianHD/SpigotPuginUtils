package de.dercoolejulianhd.plugin.utils.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerScoreboard extends ScoreboardBuilder {

    private final Player player;

    public PlayerScoreboard(Plugin plugin, Player player, String displayName) {
        super(plugin, displayName, false);
        this.player = player;
        player.setScoreboard(this.getScoreboard());
    }

    @Override
    public void update(int score, String content) {
        this.setScore(content, score);
    }

    public Player getPlayer() {
        return player;
    }
}

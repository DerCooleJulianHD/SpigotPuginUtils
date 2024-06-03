package de.dercoolejulianhd.plugin.utils.scoreboard;

import org.bukkit.plugin.Plugin;

public class LocalScoreboard extends ScoreboardBuilder {

    public LocalScoreboard(Plugin plugin, String displayName) {
        super(plugin, displayName, true);
    }

    @Override
    public void update(int score, String content) {
        this.setScore(content, score);
    }
}

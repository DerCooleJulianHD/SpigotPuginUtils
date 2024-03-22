package de.dercoolejulianhd.module.animated_scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;

public abstract class CraftPlayerBoard extends ScoreboardBuilder {


    public CraftPlayerBoard(Plugin plugin, Player player, String displayName, DisplaySlot type, boolean override) {
        super(plugin, player, displayName, type, override);
    }
}

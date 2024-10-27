package de.dercoolejulianhd.plugin.utils.minigameapi.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum TeamColor {

    BLUE("blau", ChatColor.BLUE, Color.BLUE, 11),
    RED("rot", ChatColor.RED, Color.RED, 14),
    YELLOW("gelb", ChatColor.YELLOW, Color.YELLOW, 4),
    GREEN("grün", ChatColor.GREEN, Color.GREEN, 5),
    AQUA("türkies", ChatColor.AQUA, Color.AQUA, 9),
    WHITE("weiß", ChatColor.WHITE, Color.WHITE, 0),
    GRAY("grau", ChatColor.DARK_GRAY, Color.GRAY, 8),
    PINK("pink", ChatColor.LIGHT_PURPLE, Color.fromBGR(100, 0, 255), 6);

    private final String name;
    private final ChatColor color;
    private final Color bukkitColor;
    private final int blockColor;

    TeamColor(String name, ChatColor color, Color bukkitColor, int blockColor) {
        this.name = name;
        this.color = color;
        this.bukkitColor = bukkitColor;
        this.blockColor = blockColor;
    }

    public ChatColor getChatColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Color getBukkitColor() {
        return bukkitColor;
    }

    public int getBlockColor() {
        return blockColor;
    }
}

package de.dercoolejulianhd.minigame.bedwars.plugin.teams.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

public enum TeamColor {

    BLUE(ChatColor.BLUE, Color.BLUE, "Blau", Material.BLUE_WOOL),
    RED(ChatColor.RED, Color.RED, "Rot", Material.RED_WOOL),
    YELLOW(ChatColor.YELLOW, Color.YELLOW, "Blau", Material.YELLOW_WOOL),
    GREEN(ChatColor.GREEN, Color.LIME, "Grün", Material.LIME_WOOL),
    AQUA(ChatColor.AQUA, Color.AQUA, "Türkies", Material.CYAN_WOOL),
    WHITE(ChatColor.WHITE, Color.WHITE, "Weiß", Material.WHITE_WOOL),
    GRAY(ChatColor.GRAY, Color.GRAY, "Blau", Material.GRAY_WOOL),
    PINK(ChatColor.LIGHT_PURPLE, Color.fromRGB(255, 0, 153).mixColors(Color.WHITE), "Pink", Material.PINK_WOOL);

    private final ChatColor chatColor;
    private final Color bukkitColor;
    private final String label;

    private final Material woolColor;

    TeamColor(ChatColor chatColor, Color bukkitColor, String label, Material woolColor) {
        this.chatColor = chatColor;
        this.bukkitColor = bukkitColor;
        this.label = label;
        this.woolColor = woolColor;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Color getBukkitColor() {
        return bukkitColor;
    }

    public String getLabel() {
        return label;
    }

    public Material getWoolColor() {
        return woolColor;
    }
}

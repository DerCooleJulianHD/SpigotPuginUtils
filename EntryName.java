package de.dercoolejulianhd.minigame.bedwars.library.utils.scoreboard.utils;

import org.bukkit.ChatColor;

public enum EntryName {

    ENTRY_0(0, ChatColor.BOLD.toString()),
    ENTRY_1(1, ChatColor.GRAY.toString()),
    ENTRY_2(2, ChatColor.RED.toString()),
    ENTRY_3(3, ChatColor.GREEN.toString()),
    ENTRY_4(4, ChatColor.BLUE.toString()),
    ENTRY_5(5, ChatColor.LIGHT_PURPLE.toString()),
    ENTRY_6(6, ChatColor.YELLOW.toString()),
    ENTRY_7(7, ChatColor.AQUA.toString()),
    ENTRY_8(8, ChatColor.ITALIC.toString()),
    ENTRY_9(9, ChatColor.RESET.toString()),
    ENTRY_10(10, ChatColor.WHITE.toString()),
    ENTRY_11(11, ChatColor.DARK_GRAY.toString()),
    ENTRY_12(12, ChatColor.STRIKETHROUGH.toString()),
    ENTRY_13(13, ChatColor.DARK_PURPLE.toString()),
    ENTRY_14(14, ChatColor.DARK_BLUE.toString()),
    ENTRY_15(15, ChatColor.DARK_RED.toString());

    private final int entry;
    private final String entryName;

    EntryName(int entry, String entryName) {
        this.entry = entry;
        this.entryName = entryName;
    }

    public int getEntry() {
        return entry;
    }

    public String getEntryName() {
        return entryName;
    }
}

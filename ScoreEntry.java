package de.dercoolejulianhd.modulelibrary.scoreboard;

import org.bukkit.ChatColor;

public enum ScoreEntry {

    ENTRY_0(0, ChatColor.YELLOW.toString()),
    ENTRY_1(1, ChatColor.GREEN.toString()),
    ENTRY_2(2, ChatColor.GRAY.toString()),
    ENTRY_3(3, ChatColor.DARK_AQUA.toString()),
    ENTRY_4(4, ChatColor.GOLD.toString()),
    ENTRY_5(5, ChatColor.RED.toString()),
    ENTRY_6(6, ChatColor.BOLD.toString()),
    ENTRY_7(7, ChatColor.DARK_GRAY.toString()),
    ENTRY_8(8, ChatColor.UNDERLINE.toString()),
    ENTRY_9(9, ChatColor.ITALIC.toString()),
    ENTRY_10(10, ChatColor.AQUA.toString()),
    ENTRY_11(11, ChatColor.BLACK.toString()),
    ENTRY_12(12, ChatColor.DARK_PURPLE.toString()),
    ENTRY_13(13, ChatColor.MAGIC.toString()),
    ENTRY_14(14, ChatColor.STRIKETHROUGH.toString()),
    ENTRY_15(15, ChatColor.WHITE.toString());

    private final int entry;
    private final String entryName;

    ScoreEntry(int entry, String entryName) {
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

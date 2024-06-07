package de.dercoolejulianhd.plugin.utils.minigameapi.game.gamestate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.ChatColor;

public enum GameState {

    LOBBY(ChatColor.GREEN + "warten", true),
    RUNNING(ChatColor.RED + "läüft bereits!", false),
    RELOAD(ChatColor.GRAY + "lädt", false);


    public static final Logger logger = LogManager.getLogger(GameState.class);
    private final String motd;
    private final boolean canPlayersJoin;

    GameState(String motd, boolean canPlayersJoin) {
        this.motd = motd;
        this.canPlayersJoin = canPlayersJoin;
    }

    public String getMotd() {
        return motd;
    }

    public boolean isCanPlayersJoin() {
        return canPlayersJoin;
    }
}

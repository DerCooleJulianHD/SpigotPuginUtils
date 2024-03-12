package de.dercoolejulianhd.minigame.bedwars.library;

import de.dercoolejulianhd.minigame.bedwars.library.game.manager.GameManager;
import org.bukkit.plugin.Plugin;

public interface MiniGamePlugin {

    Plugin getBukkitPlugin();

    String getPrefix();


    GameManager getGameManager();


}

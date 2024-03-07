package de.dercoolejulianhd.pluginutilities.game.interfaces;

import de.dercoolejulianhd.pluginutilities.game.GameManager;
import org.bukkit.plugin.Plugin;

public interface MiniGamePlugin {

    Plugin getBukkitPlugin();

    String getPrefix();

    void load();

    GameManager getGameManager();


}

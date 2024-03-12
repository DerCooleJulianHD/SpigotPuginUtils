package de.dercoolejulianhd.minigame.bedwars.plugin.timers;

import de.dercoolejulianhd.minigame.bedwars.library.MiniGamePlugin;
import de.dercoolejulianhd.minigame.bedwars.library.game.interfaces.GameInstance;
import de.dercoolejulianhd.minigame.bedwars.library.utils.timer.Timer;
import de.dercoolejulianhd.minigame.bedwars.library.utils.timer.TimerCount;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LobbyCountdown extends Timer {

    private final GameInstance game;

    public LobbyCountdown(Plugin plugin, GameInstance game, int start, int end) {
        super(plugin, TimerCount.DOWN, 60, 1, 1);
        this.game = game;
    }

    @Override
    public void onTimeChange() {

        MiniGamePlugin plugin = game.getPlugin();

        if (game.getPlayers().isEmpty() || game.getPlayers().size() < game.getMinPlayers()) {
            if (getDelay() < 10) setDelay(10);
            setRunning(false);

            for (Player player : game.getPlayers()) {
                sendMessage(player, plugin.getPrefix() + ChatColor.RED + "Der Start wurde angehalten, weil zu wenig Spieler im Spiel sind!");
                sendActionBar(player,"Warten auf mehr Spieler!", ChatColor.RED);
            }

        }

        if (getDelay() != 1) {
            setDelay(1);
            if (!isRunning()) setRunning(true);
        }

        for (Player player : game.getPlayers()) {
            player.setLevel(getTime());
        }
    }

    @Override
    public void onCancel() {

    }
}

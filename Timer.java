package de.dercoolejulianhd.pluginutilities.timer;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public abstract class Timer extends BukkitRunnable {

    private final Plugin plugin;
    private final TimerCount timerCount;
    private boolean running;
    private final int end;
    private int time, delay;

    public Timer(Plugin plugin, TimerCount timerCount , int start, int end, int delay) /* Counts a specific time down or up and do something when Timer is reaching the declared end-time */ {
        this.plugin = plugin;
        this.timerCount = timerCount;

        this.running = false;
        this.time = start;
        this.end = end;
        this.delay = delay;

        runTaskTimer(plugin, delay, delay);
    }

    @Override
    public void run() /* method that count up and down and check for each count if timer reaches the declared end time */ {

        if (!isRunning()) return;

        if (time >= end) {
            onCancel();
            cancel();
        }

        onTimeChange();
        switch (timerCount) {
            case UP -> {
                setTime(time + 1);
            }
            case DOWN -> {
                setTime(time - 1);
            }
        }
    }

    public abstract void onTimeChange(); /* method which defines what happens on each count */
    public abstract void onCancel(); /* method which defines what happens when Timer is reaching max count value (called: int end) */

    public void sendActionBar(Player player, ChatColor... formations) {
        TextComponent component = new TextComponent(Arrays.toString(formations) + getTime());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(message);
    }

    public void sendBossBar(Player player, BarColor color, ChatColor... formations) {
        TextComponent component = new TextComponent(Arrays.toString(formations) + getTime());
        NamespacedKey namespacedKey = NamespacedKey.minecraft("{timer}");
        BossBar bossBar = player.getServer().getBossBar(namespacedKey);

        if (bossBar == null) bossBar = plugin.getServer().createBossBar(namespacedKey, String.valueOf(component), color, BarStyle.SEGMENTED_12);

        bossBar.addPlayer(player);
        bossBar.setVisible(true);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public TimerCount getTimerCount() {
        return timerCount;
    }

    public int getEnd() {
        return end;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}

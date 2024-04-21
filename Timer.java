package de.dercoolejulianhd.plugin_utilites.timer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public abstract class Timer {

    final Plugin plugin;
    boolean running;
    int currentTimeSec;

    final TimerType type;

    public Timer(Plugin plugin, TimerType type, int time, int cancel, double update) {
        this.type = type;
        this.plugin = plugin;
        this.currentTimeSec = time;
        this.running = false;

        run(cancel, update);
    }

    public void resume() {

        if (isRunning()) {
            return;
        }

        onResume();
        setRunning(true);
    }

    public void pause() {

        if (!isRunning()) {
            return;
        }

        setRunning(false);
    }


    public abstract void onResume();
    public abstract void onTimeChange();
    public abstract void onCancel();

    public boolean isRunning() {
        return running;
    }

    void setRunning(boolean running) {
        this.running = running;
    }

    public int getCurrentTimeSec() {
        return currentTimeSec;
    }

    public void setCurrentTimeSec(int currentTimeSec) {
        this.currentTimeSec = currentTimeSec;
    }

    void sendActionBar(Collection<Player> players, String format) {
        players.forEach(player -> {
            MessageOutput.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', format).replaceAll("%time%", String.valueOf(this.currentTimeSec)));
        });
    }

    void run(int stop, double update) {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (!isRunning()) {
                    return;
                }

                switch (type) {

                    case COUNTER: {

                        if (currentTimeSec >= stop) {
                            onCancel();
                            cancel();
                        }

                        onTimeChange();
                        setCurrentTimeSec(currentTimeSec + 1);
                        break;
                    }

                    case COUNTDOWN: {

                        if (currentTimeSec <= stop) {
                            onCancel();
                            cancel();
                        }

                        onTimeChange();
                        setCurrentTimeSec(currentTimeSec - 1);
                        break;
                    }
                }
            }
        }.runTaskTimer(plugin, (long) (update*20), (long) (update*20));
    }
}

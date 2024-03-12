package de.dercoolejulianhd.minigame.bedwars.library.game;

import de.dercoolejulianhd.minigame.bedwars.library.MiniGamePlugin;
import de.dercoolejulianhd.minigame.bedwars.library.game.interfaces.GameInstance;
import de.dercoolejulianhd.minigame.bedwars.library.game.interfaces.GameMap;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.Objects;

public class LocalGameMap implements GameMap {

    private final MiniGamePlugin plugin;
    private final GameInstance gameInstance;
    private World bukkitWorld;

    public LocalGameMap(MiniGamePlugin miniGame, GameInstance gameInstance) {
        this.plugin = miniGame;
        this.gameInstance = gameInstance;

        load();
    }

    @Override
    public MiniGamePlugin getPlugin() {
        return plugin;
    }

    @Override
    public GameInstance getGame() {
        return gameInstance;
    }

    @Override
    public void load() {

        if (isLoaded()) return;

        Server server = plugin.getBukkitPlugin().getServer();

        bukkitWorld = server.createWorld(new WorldCreator(gameInstance.getName()));
        Objects.requireNonNull(bukkitWorld).setAutoSave(false);

        isLoaded();
    }

    @Override
    public void unload() {

        if (!isLoaded()) return;

        if (bukkitWorld == null) return;

        plugin.getBukkitPlugin().getServer().unloadWorld(bukkitWorld, false);
        bukkitWorld = null;
    }

    @Override
    public World getWorld() {
        return bukkitWorld;
    }

    @Override
    public boolean isLoaded() {
        return Bukkit.getWorld(bukkitWorld.getName()) != null;
    }
}

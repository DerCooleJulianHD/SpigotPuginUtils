package de.dercoolejulianhd.minigameapi.api.game.map;

import de.dercoolejulianhd.minigameapi.api.MiniGame;
import de.dercoolejulianhd.minigameapi.api.game.GameInstance;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class LocalGameMap implements GameMap {

    private final MiniGame plugin;
    private final GameInstance gameInstance;
    private World bukkitWorld;

    public LocalGameMap(MiniGame miniGame, GameInstance gameInstance) {
        this.plugin = miniGame;
        this.gameInstance = gameInstance;

        load();
    }

    @Override
    public MiniGame getPlugin() {
        return plugin;
    }

    @Override
    public GameInstance getGame() {
        return gameInstance;
    }

    @Override
    public boolean load() {

        if (isLoaded()) return true;

        Server server = plugin.getServer();

        bukkitWorld = plugin.getServer().createWorld(new WorldCreator(gameInstance.getName()));
        Objects.requireNonNull(bukkitWorld).setAutoSave(false);

        return isLoaded();
    }

    @Override
    public void unload() {

        if (!isLoaded()) return;

        if (bukkitWorld == null) return;

        plugin.getServer().unloadWorld(bukkitWorld, false);
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

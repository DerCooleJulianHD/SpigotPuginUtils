package com.plugin.utilities.minigame.gamemap;

import com.api.dev.minigame.MiniGame;
import com.api.dev.minigame.impl.config.Config;
import com.api.dev.minigame.impl.config.YMLConfig;
import com.api.dev.minigame.impl.listener.ListenerBundle;
import com.api.dev.minigame.impl.manager.LocationManager;
import com.plugin.utilities.minigame.gamemap.utilities.FileManager;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public final class LocalGameMap implements GameMap, Listener {

    private static final String key = "game:{" + UUID.randomUUID() + "}_gameMap";

    private final MiniGame plugin;
    private final String name;
    private File sourceFolder;
    private File activeFolder;
    private World world;

    private final Config config;


    public LocalGameMap(String name, MiniGame plugin) throws FileNotFoundException {
        this.plugin = plugin;
        this.name = name;
        this.config = this.loadConfig(name);
        File parent = this.getParent();
        if (!(parent.exists())) throw new FileNotFoundException("Parent-Folder: '.game_maps' not found in Server's Root-Folder!");
        this.sourceFolder = new File(parent, name);
        if (!(sourceFolder.exists())) throw new FileNotFoundException("Game-Map: '" + name + "' could not be found in Folder: '" + parent.getName() + "'!");
        this.load();
    }

    /* ---------------------------------------------------------------------------------------------------[ Methods ]----------------------------------------------------------------------------------------------------- */

    @Override
    public boolean load() {
        if (this.isLoaded()) return this.restore(false);
        if (this.sourceFolder == null) return false;
        this.createActiveWorldFolder();
        WorldCreator creator = new WorldCreator(name);
        this.world = Bukkit.createWorld(creator);
        return isLoaded();
    }

    @Override
    public void unload(boolean save) {
        Bukkit.unloadWorld(this.world, save);
        FileManager.delete(activeFolder);
        this.sourceFolder = null;
        this.activeFolder = null;
        this.world = null;
    }

    @Override
    public void createActiveWorldFolder() {
        File folder = new File(Bukkit.getWorldContainer(), sourceFolder + "_active_" + System.currentTimeMillis());
        try {
            folder.mkdirs();
            FileManager.copy(sourceFolder, folder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.activeFolder = folder;
    }

    @Override
    public boolean restore(boolean save) {
        this.unload(save);
        return this.load();
    }

    @Override
    public boolean save(boolean b) {
        if (!(this.isLoaded())) return false;
        if (b) this.world.save();
        return true;
    }

    public void setListenersActive() {
        Server server = Bukkit.getServer();
        PluginManager pm = server.getPluginManager();
        ListenerBundle bundle = new ListenerBundle(plugin);
        bundle.register(key, this);
        server.getLogger().log(Level.INFO, ChatColor.stripColor(plugin.getPrefix()) + "GameMap Listener was enabled successful!");
    }

    public Config loadConfig(String gameName) throws FileNotFoundException {
        File destination = new File(plugin.getDataFolder(), gameName);
        if (!(destination.exists())) throw new FileNotFoundException("Game's destination-folder could not be found!");
        return new YMLConfig(plugin, destination, "game", true);
    }

    /* ---------------------------------------------------------------------------------------------------[ Getter ]----------------------------------------------------------------------------------------------------- */

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public File getSourceFolder() {
        return this.sourceFolder;
    }

    @Override
    public File getActiveWorldFolder() {
        return this.activeFolder;
    }

    @Override
    public World getGameWorld() {
        return this.world;
    }

    @Override
    public boolean isLoaded() {
        return (this.world != null && this.activeFolder.exists());
    }

    @Override
    public MiniGame getPlugin() {
        return this.plugin;
    }

    @Override
    public Location getSpectatorLocation() {
        ConfigurationSection section = this.config.getFileConfig().getConfigurationSection("spectator-location");
        return LocationManager.parseLocation(section);
    }


    public File getParent() {
        return new File(plugin.getDataFolder().getParentFile(), ".game_maps");
    }

    public static String getKey() {
        return key;
    }

    public Config getConfig() {
        return config;
    }

    /* ---------------------------------------------------------------------------------------------------[ Listener-Events ]----------------------------------------------------------------------------------------------------- */

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        p.teleport(getSpectatorLocation());
        p.setGameMode(GameMode.SPECTATOR);

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.hidePlayer(p);
        });

        PlayerInventory inventory = p.getInventory();
        inventory.setItem(4, new ItemStack(Material.COMPASS));
    }
}

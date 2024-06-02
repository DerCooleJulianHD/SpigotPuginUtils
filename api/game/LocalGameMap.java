package com.plugin.utils.minigame.api.game;

import com.plugin.utils.minigame.api.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

public class LocalGameMap implements IGameMap, Listener {

    private final Plugin plugin;
    private final IGameInstance game;

    private final String worldName;
    private final File sourceFolder;
    private File worldFolder;
    private World bukkitWorld;
    private Collection<Block> blockList;

    public LocalGameMap(Plugin plugin, IGameInstance game, String worldName, boolean load) {
        this.plugin = plugin;
        this.game = game;
        this.worldName = worldName;
        File directory = new File(plugin.getDataFolder(), ".maps");
        this.sourceFolder = new File(directory, worldName);
        if (load) this.load();
    }

    public LocalGameMap(Plugin plugin, IGameInstance game, boolean load) {
        this.plugin = plugin;
        this.game = game;
        this.worldName = game.getName();
        File directory = new File(plugin.getDataFolder(), ".maps");
        this.sourceFolder = new File(directory, worldName);
        if (load) this.load();
    }

    public void load() {
        if (this.isLoaded()) return;
        if (worldFolder == null) return;
        this.worldFolder = new File(Bukkit.getWorldContainer(), sourceFolder.getName());
        FileManager.copy(sourceFolder, worldFolder);
        WorldCreator creator = new WorldCreator(worldFolder.getName());
        World world = Bukkit.createWorld(creator);
        world.setAnimalSpawnLimit(0);
        world.setDifficulty(Difficulty.EASY);
        world.setPVP(true);
        world.setAutoSave(false);
        world.setTime(8000);
        world.setGameRuleValue("doDaylightCycle", "false");
        this.setBukkitWorld(world);
        this.setBlockList(Collections.emptyList());
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void unload() {
        if (!this.isLoaded()) return;
        if (worldFolder == null) return;
        Bukkit.unloadWorld(this.bukkitWorld, false);
        FileManager.delete(this.worldFolder);
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        if (game.getState() != GameState.INGAME) return;
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        if (!(game.getPlayers().contains(player))) {
            event.setCancelled(true);
            return;
        }
        if (!(block.getWorld().equals(this.getBukkitWorld()))) return;
        block.setMetadata("placed_by:player", new FixedMetadataValue(plugin, block));
        block.setBiome(Biome.PLAINS);
        this.getBlocksPlaced().add(block);
    }

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if ((!this.isMapBlock(block)) || player.isOp()) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }

    @Override
    public void restore() {
        this.unload();
        this.load();
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public String getGameName() {
        return game.getName();
    }

    @Override
    public String getWorldName() {
        return worldName;
    }

    @Override
    public World getBukkitWorld() {
        return bukkitWorld;
    }

    @Override
    public File getWorldFolder() {
        return worldFolder;
    }

    @Override
    public Collection<Block> getBlocksPlaced() {
        return blockList;
    }

    public IGameInstance getGame() {
        return game;
    }

    public File getSourceFolder() {
        return sourceFolder;
    }

    public void setBukkitWorld(World bukkitWorld) {
        this.bukkitWorld = bukkitWorld;
    }

    public Collection<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(Collection<Block> blockList) {
        this.blockList = blockList;
    }

    public boolean isLoaded() {
        return bukkitWorld != null;
    }

    @Override
    public boolean isMapBlock(Block block) {
        return block.getWorld().equals(this.getBukkitWorld()) && (!block.hasMetadata("placed_by:player"));
    }
}

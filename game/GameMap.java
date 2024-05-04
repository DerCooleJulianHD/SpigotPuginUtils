package de.dercoolejulianhd.plugin_utilites.game;

import de.dercoolejulianhd.plugin_utilites.config.Config;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class GameMap {

    final GameInstance game;
    String gameName;
    World bukkitWorld;

    final Set<Block> mapBlocks;

    public GameMap(GameInstance game) {
        this.game = game;
        this.mapBlocks = new HashSet<>();

        Config config = game.getConfiguration();

        if (!config.isLoaded()) {
            return;
        }

        this.gameName = (String) config.getObject(config.getFileConfig().getDefaultSection(), "world");
        load();
    }

    void load() {

        if (isLoaded()) {
            return;
        }

        Config config = game.getConfiguration();
        File worldFolder = new File(Bukkit.getWorldContainer().getParent(), gameName);

        if (!worldFolder.exists()) {
            return;
        }

        this.bukkitWorld = Bukkit.createWorld(WorldCreator.name(gameName));

        this.bukkitWorld.setAutoSave(false);
        this.bukkitWorld.setDifficulty(Difficulty.NORMAL);
        this.bukkitWorld.setPVP(true);
        this.bukkitWorld.setMonsterSpawnLimit(0);
        this.bukkitWorld.setAnimalSpawnLimit(0);

        ConfigurationSection region = config.getFileConfig().getConfigurationSection("region");
        Location first = (Location) config.getObject(region, "first");
        Location second = (Location) config.getObject(region, "second");
        setMapBlocks(first, second);
    }

    public void unload() {

        if (!isLoaded()) {
            return;
        }

        Bukkit.unloadWorld(bukkitWorld, false);
        this.bukkitWorld = null;
    }

    void setMapBlocks(Location firstLoc, Location secondLoc) {
        for (int x = firstLoc.getBlockX(); x <= secondLoc.getBlockX(); x++) {
            for (int y = firstLoc.getBlockY(); y <= secondLoc.getBlockY(); y++) {
                for (int z = firstLoc.getBlockZ(); z <= secondLoc.getBlockZ(); z++) {
                    Block block = bukkitWorld.getBlockAt(x, y, z);

                    if (block.getType() == Material.AIR) {
                        continue;
                    }

                    mapBlocks.add(block);
                }
            }
        }
    }

    public boolean isMapBlock(Block block) {
        return mapBlocks.contains(block);
    }

    public boolean isLoaded() {
        return bukkitWorld != null;
    }

    public GameInstance getGame() {
        return game;
    }

    public World getWorld() {
        return bukkitWorld;
    }

    public Set<Block> getMapBlocks() {
        return mapBlocks;
    }
}

package com.plugin.utils.minigame.api.game;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public interface IGameMap {

    Plugin getPlugin();
    String getGameName();
    String getWorldName();
    World getBukkitWorld();
    File getWorldFolder();
    void restore();
    void unload();
    boolean isMapBlock(Block block);
    Collection<Block> getBlocksPlaced();
}

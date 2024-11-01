package de.dercoolejulianhd.modulelibrary.gameworld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.FileUtil;

import java.io.File;

public class LocalGameWorld implements GameWorld {
    private final Plugin plugin;
    private final String worldName;
    private final File source;
    private File activeWorldFolder;
    private World world;

    public LocalGameWorld(Plugin plugin, File destination, String worldName) {
        this.plugin = plugin;
        this.worldName = worldName;
        this.source = new File(destination, worldName);
    }

    @Override
    public boolean load() {
        if (this.isLoaded()){
            return false;
        }

        this.activeWorldFolder = new File(Bukkit.getWorldContainer().getParent(), this.source.getName() + "_active_" + System.currentTimeMillis());
        this.activeWorldFolder.mkdirs();

        File[] files = this.source.listFiles();
        if (files == null){
            this.activeWorldFolder.delete();
            return false;
        }

        for (File file : files) {
            FileUtil.copy(file, this.activeWorldFolder);
        }

        if (this.activeWorldFolder.length() == 0) {
            this.activeWorldFolder.delete();
            return false;
        }

        this.world = new WorldCreator(this.activeWorldFolder.getName()).createWorld();
        this.world.setAutoSave(false);
        this.world.setPVP(true);
        this.world.setMetadata("gameworld", new FixedMetadataValue(plugin, this.world));

        return this.isLoaded();
    }

    @Override
    public boolean isLoaded() {
        if (this.activeWorldFolder == null) {
            return false;
        }

        return Bukkit.getWorld(this.worldName) != null && this.world != null;
    }

    @Override
    public boolean restoreFromSource() {
        if (!this.isLoaded()) {
            return false;
        }

        this.unload();
        if (this.isLoaded()) {
            return false;
        }
        return this.load();
    }

    @Override
    public void unload() {
        if (!this.isLoaded()) {
            return;
        }

        this.world.getPlayers().forEach(player -> player.kickPlayer(ChatColor.RED + "unloading game World."));
        if (!this.world.getPlayers().isEmpty()) {
            return;
        }

        Bukkit.unloadWorld(this.world, false);
        this.deleteActiveWorldFolder();
        this.world = null;
    }

    @Override
    public void delete() {
        this.deleteActiveWorldFolder();
        this.delete(this.source);
    }

    public void delete(File file) {
        if (file.isDirectory()) {

            File[] childs = file.listFiles();
            if (childs == null) return;

            for (File child : childs) delete(child);
        }

        file.delete();
    }

    public static LocalGameWorld from(Plugin plugin, File storageLocation, String worldName) {
        if (!storageLocation.exists()) {
            return null;
        }

        return new LocalGameWorld(plugin, storageLocation, worldName);
    }

    @Override
    public void deleteActiveWorldFolder() {
        delete(this.activeWorldFolder);
    }

    @Override
    public String getName() {
        return this.worldName;
    }

    @Override
    public File getSourceFolder() {
        return this.source;
    }

    @Override
    public File getActiveWorldFolder() {
        return this.activeWorldFolder;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }
}

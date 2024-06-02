package com.plugin.utils.minigame.api.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;

/**
 * Uses Configuration type: YALConfiguration !.
 * @since   1.0
 */

public class YMLConfig implements Config {

    final Plugin plugin;

    final File file, dir;
    FileConfiguration fileConfiguration;


    public YMLConfig(Plugin plugin, File dir, String fileName) {
        this.plugin = plugin;
        this.dir = dir;
        this.file = new File(dir, fileName + ".yml");
    }

    @Override
    public void createConfigFiles(boolean defaults, Map<ConfigurationSection, Map<String, Object>> values) {
        try {
            this.dir.mkdirs();
            this.file.createNewFile(); /* Creating Config File */

            if (defaults) {
                this.load();
                this.setDefaults(values);
            }
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.CONFIG, exception.fillInStackTrace().toString());
        }
    }

    @Override
    public void load() {
        try {
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file); /* load */
        } catch (Exception exception){
            Bukkit.getLogger().log(Level.CONFIG, exception.fillInStackTrace().toString());
        }
    }

    @Override
    public void unload() {

        if (!isLoaded()) {
            return;
        }

        this.save();
        this.fileConfiguration = null;
    }

    @Override
    public void save() {
        try {
            this.fileConfiguration.save(this.file); /* save */
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.CONFIG, exception.fillInStackTrace().toString());
        }
    }

    @Override
    public Object getObject(ConfigurationSection section, String path) {

        if (!this.exists()) {
            return null;
        }

        if (!this.isLoaded()) {
            this.load();
        }

        if (section == null) {
            throw new NullPointerException("provided section could not be found in config: '" + this.file.getName() + "'!");
        }

        return section.get(path);
    }

    @Override
    public void set(ConfigurationSection section, String path, Object o) {

        if (!this.exists()) {
            return;
        }

        if (!this.isLoaded()) {
            this.load();
        }

        if (section == null) {
            throw new NullPointerException("provided section could not be found in config: '" + this.file.getName() + "'!");
        }

        section.set(path, o);
    }

    @Override
    public void setDefaults(Map<ConfigurationSection, Map<String, Object>> defaults) {

        if (!this.exists()) {
            return;
        }

        if (!this.isLoaded()) {
            this.load();
        }

        if (defaults == null) {
            return;
        }

        defaults.forEach((section, value) -> {
            value.forEach(section::set);
        });
    }

    @Override
    public Location loadLocation(ConfigurationSection section, String path) {
        String worldName = this.getObject(section, path + ".world").toString();
        double x = (double) this.getObject(section, path + ".x");
        double y = (double) this.getObject(section, path + ".y");
        double z = (double) this.getObject(section, path + ".z");
        Location location = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        if (worldName != null) location.setWorld(Bukkit.getWorld(worldName));
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        if (section.contains(path + ".yaw")) location.setYaw((float) this.getObject(section, path + ".yaw"));
        if (section.contains(path + ".pitch")) location.setPitch((float) this.getObject(section, path + ".pitch"));
        return location;
    }

    @Override
    public Location loadLocation(ConfigurationSection section) {
        String worldName = this.getObject(section,"world").toString();
        double x = (double) this.getObject(section,"x");
        double y = (double) this.getObject(section,"y");
        double z = (double) this.getObject(section,"z");
        Location location = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        if (worldName != null) location.setWorld(Bukkit.getWorld(worldName));
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        if (section.contains("yaw")) location.setYaw((float) this.getObject(section,"yaw"));
        if (section.contains("pitch")) location.setPitch((float) this.getObject(section,"pitch"));
        return location;
    }

    @Override
    public void setLocation(ConfigurationSection section, String path, World world, double x, double y, double z, float yaw, float pitch) {
        if (world != null) this.set(section, path + ".world", world.getName());
        this.set(section, path + ".x", x);
        this.set(section, path + ".y", y);
        this.set(section, path + ".z", z);
        this.set(section, path + ".yaw", yaw);
        this.set(section, path + ".pitch", pitch);
    }

    @Override
    public void setLocation(ConfigurationSection section, World world, double x, double y, double z, float yaw, float pitch) {
        if (world != null) this.set(section, "world", world.getName());
        this.set(section,"x", x);
        this.set(section,"y", y);
        this.set(section,"z", z);
        this.set(section,"yaw", yaw);
        this.set(section,"pitch", pitch);
    }

    @Override
    public boolean exists(){
        return (this.dir.exists() && this.file.exists());
    }

    @Override
    public boolean isLoaded() {
        return this.fileConfiguration != null;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public FileConfiguration getFileConfig() {
        return this.fileConfiguration;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}

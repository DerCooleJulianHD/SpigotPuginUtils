package de.dercoolejulianhd.modulelibrary.configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Configuration {


    private static final Logger logger = LogManager.getLogger(Configuration.class);

    private final Plugin plugin;
    private final File destination, file;
    private FileConfiguration fileConfiguration;
    private boolean loaded;

    public Configuration(Plugin plugin, File destination, String name) {
        this.plugin = plugin;
        this.destination = destination;
        this.file = new File(destination, name);
    }

    public void setDefaults() {}

    public void set(String path, Object value) {
        if (!this.isLoaded()) {
            return;
        }
        this.fileConfiguration.set(path, value);
    }

    public void set(ConfigurationSection section, String path, Object value) {
        if (section == null) {
            return;
        }
        section.set(path, value);
    }

    public void createConfigFiles(boolean defaults) {
        if (!this.destination.exists()) {
            this.destination.mkdirs();
        }

        try {
            this.file.createNewFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        if (defaults) {
            this.load();
            this.setDefaults();
        }
    }


    public void save() {
        try {
            this.fileConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void load() {

        if (!this.exists()) {
            return;
        }

        this.setFileConfiguration(new YamlConfiguration());

        try {
            this.fileConfiguration.load(file);
            this.setLoaded(true);
        } catch (InvalidConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void unload(boolean save) {
        if (save) {
            this.save();
        }

        this.setFileConfiguration(null);
        this.setLoaded(false);
    }

    public boolean reload() {
        this.unload(true);

        if (!this.isLoaded()) {
            return false;
        }

        this.load();
        return this.isLoaded();
    }

    public boolean exists() {
        return (this.destination.exists() && this.file.exists());
    }

    public File getDestinationFile() {
        return this.destination;
    }

    public File getFile() {
        return this.file;
    }

    public File getDestination() {
        return destination;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void setFileConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public boolean isLoaded() {
        return this.loaded && this.fileConfiguration != null;
    }

    private void setLoaded(boolean b) {
        this.loaded = b;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }
}

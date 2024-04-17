package de.dercoolejulianhd.plugin_utilites.configuration;

import org.bukkit.Bukkit;
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
    FileConfiguration configuration;


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
            this.configuration = YamlConfiguration.loadConfiguration(this.file); /* load */
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
        this.configuration = null;
    }

    @Override
    public void save() {
        try {
            this.configuration.save(this.file); /* save */
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
    public boolean exists(){
        return (this.dir.exists() && this.file.exists());
    }

    @Override
    public boolean isLoaded() {
        return this.configuration != null;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}

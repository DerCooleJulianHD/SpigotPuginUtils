package de.dercoolejulianhd.plugin.minigame.bedwars.lib.classes;

import jdk.jfr.Description;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Uses Configuration type: YALConfiguration !.
 * @since   1.0
 */

public class YAMLConfig {

    protected final File file, dir;
    protected FileConfiguration configuration;


    /**
     * Description:
     * Constructs a File and Directory for Config representation.
     */
    public YAMLConfig(File dir, String file) {
        this.dir = dir;
        this.file = new File(dir, file + ".yml");
    }



    /**
     * Description:
     * creates Configuration-File if it doesn't exist.
     */
    @Description("  /* creates Configuration-File if it doesn't exist */ ")
    public void createNewConfiguration(boolean defaults, Map<ConfigurationSection, Map<String, Object>> values) {
        try {
            this.dir.mkdirs();
            this.file.createNewFile(); /* Creating Config File */
            this.load();

            if (defaults) {
                this.setDefaults(values);
            }
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.CONFIG, exception.fillInStackTrace().toString());
        }
    }


    /**
     * Description:
     * loads configuration if not already loaded.
     */
    @Description("  /* loads configuration */ ")
    public void load()  {
        try {
            this.configuration = YamlConfiguration.loadConfiguration(this.file); /* load */
        } catch (Exception exception){
            Bukkit.getLogger().log(Level.CONFIG, exception.fillInStackTrace().toString());
        }
    }


    /**
     * Description:
     * saves configuration changes.
     */
    @Description("  /* saves configuration changes */ ")
    public void save() {
        try {
            this.configuration.save(this.file); /* save */
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.CONFIG, exception.fillInStackTrace().toString());
        }
    }


    /**
     * Description:
     * sets the default values when the config is created first .
     */
    @Description(" /* sets the default values when the config is created first */ ")
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

        this.save();
    }


    /**
     * Description:
     * check if directory & file is existing .
     */
    @Description("  /* check if directory & file is existing */ ")
    public boolean exists(){
        return (this.dir.exists() && this.file.exists());
    }



    /**
     * Description:
     * check if configuration is already loaded .
     */
    @Description("  /* check if configuration is already loaded */ ")
    public boolean isLoaded() {
        return this.configuration != null;
    }


    /**
     * Description:
     * returns the file you are editing on.
     */
    public File getFile() {
        return this.file;
    }


    /**
     * Description:
     * returns fileConfiguration that edits the file.
     */
    public FileConfiguration getConfiguration() {
        return this.configuration;
    }


    /**
     * Description:
     * returns object that was saved in configuration before.
     */
    public Object getObject(String section, String path) {

        if (!this.exists()) {
            return null;
        }

        if (!this.isLoaded()) {
            this.load();
        }

        ConfigurationSection cs = this.configuration.getConfigurationSection(section);

        if (section == null) {
            throw new NullPointerException("no objects found!");
        }

        return cs.get(path);
    }

    /**
     * Description:
     * returns object that was saved in configuration before.
     */
    public void set(String section, String path, Object o) {

        if (!this.exists()) {
            return;
        }

        if (!this.isLoaded()) {
            this.load();
        }

        ConfigurationSection cs = this.configuration.getConfigurationSection(section);

        if (cs == null) {
            cs = this.configuration.createSection(section);
        }

        cs.set(path, o);
        save();
    }
}

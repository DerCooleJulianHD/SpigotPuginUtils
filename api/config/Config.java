package com.plugin.utils.minigame.api.config;

import jdk.jfr.Description;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Map;

public interface Config {

    /**
     * Description:
     * creates Configuration-Files and setup defaults
     * when Config is created first.
     */
    @Description("  /* creates Configuration-Files and setup defaults when Config is created first. */  ")
    void createConfigFiles(boolean defaults, Map<ConfigurationSection, Map<String, Object>> values);


    /**
     * Description:
     * loads configuration.
     */
    @Deprecated
    @Description("  /* loads configuration. */  ")
    void load();


    /**
     * Description:
     * loads configuration.
     */
    @Description("  /* unloads configuration when configuration object returns not null. */  ")
    void unload();


    /**
     * Description:
     * saves configuration changes.
     */
    @Description("  /* saves configuration changes. */  ")
    void save();

    /**
     * Description:
     * returns object that was saved in configuration before.
     */
    @Description("  /* returns object that was saved in configuration before. */  ")
    Object getObject(ConfigurationSection section, String path);


    /**
     * Description:
     * saves an object in the loaded configuration file.
     */
    @Description("  /* saves an object in the loaded configuration file. */  ")
    void set(ConfigurationSection section, String path, Object o);


    /**
     * Description:
     * sets the default values when the config is created first.
     */
    @Deprecated
    @Description("  /* sets the default values when the config is created first. */  ")
    void setDefaults(Map<ConfigurationSection, Map<String, Object>> defaults);


    /**
     * Description:
     * loads a specific Location by getting values of block-coordinates and world.
     */
    @Description("  /* loads a specific Location by getting values of block-coordinates and world */ ")
    Location loadLocation(ConfigurationSection section, String path);
    Location loadLocation(ConfigurationSection section);


    /**
     * Description:
     * saves a specific Location by setting value of block-coordinates and world.
     */
    @Description("  /* saves a specific Location by setting value of block-coordinates and world */ ")
    void setLocation(ConfigurationSection section, String path, World world, double x, double y, double z, float yaw, float pitch);
    void setLocation(ConfigurationSection section, World world, double x, double y, double z, float yaw, float pitch);


    /**
     * Description:
     *  returns true when directory & file is existing.
     */
    @Description("  /* returns true when directory & file is existing */  ")
    boolean exists();


    /**
     * Description:
     * returns true when configuration is loaded already
     * and configuration object returns not null.
     */
    @Description("  /* returns true when configuration is loaded already and configuration object returns not null */  ")
    boolean isLoaded();


    /**
     * Description:
     * returns the file which is loaded.
     */
    @Description("  /* returns the file which is loaded */  ")
    File getFile();


    /**
     * Description:
     * returns file-configuration which has loaded the file.
     */
    @Description("  /* returns file-configuration which has loaded the file */  ")
    FileConfiguration getFileConfig();


    /**
     * Description:
     * returns plugin which is using this config
     */
    @Description("  /* returns plugin which is using this config */  ")
    Plugin getPlugin();
}

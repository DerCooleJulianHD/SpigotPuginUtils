package de.dercoolejulianhd.coinsystem.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public abstract class Config /* Type: YAML-Configuration */ {

    private final Plugin plugin;
    private File file;
    private YamlConfiguration yamlConfiguration;
    private boolean loaded, exist;


    public Config(Plugin plugin, File directory, String name/* Name from File which will be created! */) {
        
        this.plugin = plugin;
        
        if (!directory.exists() /* check directory path existing */ ){ 
            /* Directory wasn't found */
            Bukkit.getServer().getConsoleSender().sendMessage(plugin.getName() + ChatColor.RED + "The specified folder path doesn't exist!");
            return;
        }
        
        /* Directory Found */
        this.file = new File(directory, name + ".yml");
        
        this.exist = (directory.exists() && file.exists());
        this.loaded = false;
    }

    public void createNewConfiguration() /* creates Configuration-File if it doesn't exist */ {
    
        try {
            file.createNewFile(); /* Creating Config File */
            
            loadConfiguration();
            setDefaults();
            save();
            
        } catch (Exception exception) {
            /* Config File couldn't be created */
            Bukkit.getServer().getConsoleSender().sendMessage(plugin.getName() + ChatColor.RED + "Couldn't create file: " + file.getName() + ": " + exception.getCause());
        }
        
    }

    public void loadConfiguration() /* loads configuration */ {
        
        if (isLoaded() /* check if the Configuration is already loaded */ ) return;
        
        try { /* load */
            this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            setLoaded(true);
        } catch (Exception exception /* loading error */){
            Bukkit.getServer().getConsoleSender().sendMessage(plugin.getName() + ChatColor.RED + "Couldn't load configuration: " + exception.getCause());
        }
        
    }

    public void save() /* saves configuration changes */ {
        
        try {
            yamlConfiguration.save(file);
        } catch (Exception exception) {
            /* Config File couldn't be saved */
            Bukkit.getServer().getConsoleSender().sendMessage(plugin.getName() + ChatColor.RED + "Couldn't save file: " + file.getName() + ": " + exception.getCause());
        }
        
    }

    public abstract void setDefaults(); /* sets the default values when the config is created first */

    public Plugin getPlugin() {
        return plugin;
    }

    public YamlConfiguration getConfiguration() {
        return yamlConfiguration;
    }
    
    public boolean exists(){
        return exist;
    }

    private void setExists(boolean exist) {
        this.exist = exist;
    }
        
    public File getFile() {
        return file;
    }
    
    public boolean isLoaded() {
        return loaded;
    }
    
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
    
}

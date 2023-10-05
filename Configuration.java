package de.dercoolejulianhd.coinsystem.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Configuration { // Type: YAML-Configuration //

    // Attribute
    private final Plugin plugin;
    private File file;
    private YamlConfiguration yamlConfiguration;
    private boolean loaded, exist;


    // creating new Config File with Directory File//
    public Configuration(Plugin plugin, File directory, String name) { // File-Name without ending, only name//
        this.plugin = plugin;
        if (!directory.exists()){ // check directory path existing //
            // Directory doesn't found //
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(plugin.getName() + ChatColor.RED + "The specified folder path doesn't exist!");
            return;
        }
        // Directory Found //
        this.file = new File(directory, name + ".yml");
        this.exist = (directory.exists() && file.exists());
        this.loaded = false;
    }

    public void createNewConfiguration(){
        if (file.exists()) return; // check file existing //
        // Creating Config File //
        try {
            file.createNewFile();
        } catch (Exception exception) {
            // Config File couldn't be created //
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(plugin.getName() + ChatColor.RED + "Couldn't create file: " + file.getName() + ": " + exception.getCause());
        }
    }

    public void loadConfiguration(){ // Load Config File //
        if (isLoaded()) return;
        try {
            this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            this.loaded = true;
        } catch (Exception exception){
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(plugin.getName() + ChatColor.RED + "Couldn't load configuration: " + exception.getCause());
        }
    }

    public void save() {
        try {
            // Save Configuration //
            yamlConfiguration.save(file);
        } catch (Exception exception) {
            // Config File couldn't be saved //
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(plugin.getName() + ChatColor.RED + "Couldn't save file: " + file.getName() + ": " + exception.getCause());
        }
    }

    public YamlConfiguration configuration() {
        return yamlConfiguration;
    }
    public boolean exists(){
        return exist;
    }
    public Plugin plugin() {
        return plugin;
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

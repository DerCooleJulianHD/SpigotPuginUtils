package main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration { // Type: YAML-Configuration //

    // Attribute
    public File file;
    public YamlConfiguration config;


    // creating new Config File with Directory File//
    public Configuration(File directory, String name) { // File-Name without ending only name//
        if (!directory.exists()){ // check directory path existing //
            // Directory doesn't found //
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(ChatColor.RED + "The specified folder path doesn't exist!");
            return;
        }
        // Directory Found //
        this.file = new File(directory, name + ".yml");
        if (!file.exists()){ // check file existing //
            // Creating Config File //
            try {
                file.createNewFile();
            } catch (IOException e) {
                // Config File couldn't be created //
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                console.sendMessage(ChatColor.RED + "couldn't create file: " + name + ".yml" + ":\n" + e);
                return;
            }
        }
        // Load Config File //
        this.config = YamlConfiguration.loadConfiguration(file);
        config.options().copyDefaults(true);
    }

    public void save() {
        try {
            // Save Configuration //
            config.save(file);
        } catch (IOException e) {
            // Config File couldn't be saved //
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(ChatColor.RED + "couldn't save file: " + file.getName() + ":\n" + e);
            return;
        }
    }
}

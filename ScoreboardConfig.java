package de.dercoolejulianhd.module.animated_scoreboard.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ScoreboardConfig extends Config {

    public ScoreboardConfig(JavaPlugin plugin) {
        super(plugin.getDataFolder(), "config.yml");

        if (!exists()) {
            createNewConfiguration(true);
        }

        if (!isLoaded()) load();
    }

    @Override
    public void setDefaults() {
        configuration.addDefault("settings.displayName", "&f&lScoreboard");
        ConfigurationSection contents = configuration.createSection("contents");

        for (int score = 0; score < 15; score++) {
            ConfigurationSection contentsSection = contents.createSection(String.valueOf(score));
            contentsSection.set("content", List.of(" "));
            contentsSection.set("update-intervall", -1);
        }
    }

}

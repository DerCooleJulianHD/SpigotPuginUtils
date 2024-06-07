package de.dercoolejulianhd.plugin.utils.minigameapi.minigameplugin;

import de.dercoolejulianhd.plugin.utils.minigameapi.game.Game;
import de.dercoolejulianhd.plugin.utils.minigameapi.scoreboard.ScoreboardManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import src.de.dercoolejulianhd.plugin.utils.config.YMLConfig;

import java.io.File;
import java.util.Map;

public class MinigamePlugin extends JavaPlugin {

    private static Plugin plugin;
    private final File saves;
    private final YMLConfig config;

    private Game gameLoaded;
    private ScoreboardManager scoreboardManager;

    public MinigamePlugin(Plugin plugin) {
        MinigamePlugin.plugin = plugin;
        this.saves = createSaves();
        this.config = loadPluginConfig();
    }

    private static YMLConfig loadPluginConfig() {
        File pluginFolder = plugin.getDataFolder();
        YMLConfig config = new YMLConfig(plugin, pluginFolder, "config", false);
        if (!config.exists()) config.createConfigFiles(false, null);
        if (!config.isLoaded()) config.load();
        assert config.getFileConfig() != null;
        config.setDefaults(Map.of(config.getFileConfig(), Map.of("prefix", " ")));
        return config;
    }

    private static File createSaves() {
        File pluginFolder = plugin.getDataFolder();
        File savedGamesFolder = new File(pluginFolder, "games");
        if (!savedGamesFolder.exists()) savedGamesFolder.mkdirs();
        return savedGamesFolder;
    }

    @Override
    public void onLoad() {
        this.scoreboardManager = new ScoreboardManager(this);
        this.gameLoaded = null;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(Plugin plugin) {
        MinigamePlugin.plugin = plugin;
    }

    public YMLConfig getPluginConfiguration() {
        return config;
    }

    public File getSaves() {
        return saves;
    }

    public Game getGameLoaded() {
        return gameLoaded;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}

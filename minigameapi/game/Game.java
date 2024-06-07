package de.dercoolejulianhd.plugin.utils.minigameapi.game;

import com.avaje.ebean.validation.NotNull;
import de.dercoolejulianhd.plugin.utils.minigameapi.game.gamestate.GameState;
import de.dercoolejulianhd.plugin.utils.minigameapi.game.listener.LobbyListener;
import de.dercoolejulianhd.plugin.utils.minigameapi.minigameplugin.MinigamePlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import src.de.dercoolejulianhd.plugin.utils.config.YMLConfig;
import src.de.dercoolejulianhd.plugin.utils.listener.ListenerBundle;

import java.io.File;

public class Game {


    public static final Logger logger = LogManager.getLogger(Game.class);

    private final MinigamePlugin plugin;
    private final File dataFolder;
    private final YMLConfig config;
    private String name;
    private World world;
    private int maxPlayers;
    private int minPlayers;
    private GameState gameState;
    private ListenerBundle listenerBundle;

    public Game(MinigamePlugin plugin, String gameName) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getSaves(), gameName);
        this.config = this.loadConfiguration(dataFolder);
    }

    public void start() {
        this.name = config.getObject(config.getFileConfig(), "name").toString();
        this.world = this.loadWorld();
        this.minPlayers = (int) config.getObject(config.getFileConfig(), "min-players");
        this.maxPlayers = (int) config.getObject(config.getFileConfig(), "max-players");
        this.listenerBundle = new ListenerBundle(plugin.getPlugin());
        this.listenerBundle.registerListener("lobby", new LobbyListener(plugin, this));
        this.gameState = GameState.LOBBY;
    }

    public World loadWorld() {
        World world = Bukkit.createWorld(new WorldCreator(config.getObject(config.getFileConfig(), "world").toString()));
        world.setAutoSave(false);
        world.setPVP(true);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setDifficulty(Difficulty.EASY);
        world.setTime(8000);
        world.save();
        return world;
    }

    @NotNull
    private YMLConfig loadConfiguration(File dataFolder) {
        if (!dataFolder.exists()) return null;
        YMLConfig config = new YMLConfig(plugin, dataFolder, "game", false);
        if (!config.exists()) config.createConfigFiles(false, null);
        if (!config.isLoaded()) config.load();
        assert config.getFileConfig() != null;
        return config;
    }

    public MinigamePlugin getPlugin() {
        return plugin;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public YMLConfig getConfiguration() {
        return config;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public World getWorld() {
        return world;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ListenerBundle getListeners() {
        return listenerBundle;

    }
}


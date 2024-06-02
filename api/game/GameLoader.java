package com.plugin.utils.minigame.api.game;

import com.plugin.utils.minigame.api.IMinigameAPI;
import com.plugin.utils.minigame.api.config.Config;
import com.plugin.utils.minigame.api.config.YMLConfig;
import com.plugin.utils.minigame.api.listener.ListenerBundle;
import com.plugin.utils.minigame.api.utils.IChatMessenger;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class GameLoader {

    private final Plugin plugin;

    public GameLoader(Plugin plugin) {
        this.plugin = plugin;
        this.getDestinationFolder();
    }

    public void loadGame(Plugin plugin, File destination) {

        if (!(Game.exists(plugin, destination.getName()))) return;

        try {
            Game game = new Game(plugin, destination);

            Config config = new YMLConfig(plugin, destination, "game-settings.yml");
            if (!config.exists()) throw new NullPointerException(destination.getName() + ": configuration file 'game-settings.yml' not found!");
            if (!config.isLoaded()) config.load();
            game.setConfig(config);

            game.setName(config.getObject(config.getFileConfig(), "name").toString());
            game.setMap(new LocalGameMap(plugin, game, true));
            game.setListenerBundle(new ListenerBundle(plugin, game));
            game.setState(GameState.LOBBY);

            BaseComponent formatedMessage = IChatMessenger.getFormatedMessage(plugin, ChatColor.GREEN + "Game: '" + game.getName() + "' loaded successfully.", null);
            assert formatedMessage != null;
            String output = formatedMessage.toString();
            Bukkit.getConsoleSender().sendMessage(output);
            ((IMinigameAPI) plugin).setLoadedGame(game);
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
    }

    public void unloadGame(Plugin plugin) {
        try {
            IMinigameAPI minigameAPI = (IMinigameAPI) plugin;
            IGameInstance loadedGame = minigameAPI.getLoadedGame();
            loadedGame.getListeners().unregisterAll();
            loadedGame.getMap().unload();
            loadedGame.getConfiguration().unload();
            loadedGame.getSpectators().clear();
            loadedGame.getPlayers().clear();
            loadedGame.setState(null);
            minigameAPI.setLoadedGame(null);
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
    }

    public File getDestinationFolder(){
        File folder = new File(plugin.getDataFolder(), "games");
        if (!folder.exists()) folder.mkdirs();
        return folder;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}

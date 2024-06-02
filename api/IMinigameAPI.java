package com.plugin.utils.minigame.api;

import com.plugin.utils.minigame.api.game.GameLoader;
import com.plugin.utils.minigame.api.game.IGameInstance;
import com.plugin.utils.minigame.api.utils.IChatMessenger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface IMinigameAPI {

    Plugin getPlugin();
    String getPrefix();
    IGameInstance getLoadedGame();
    void setLoadedGame(IGameInstance game);
    GameLoader getGameLoader();

    static void sendMessage(Plugin plugin, Player player, String message){
        IChatMessenger.send(plugin, player, message, null, false);
    }

    static void broadcastMessage(Plugin plugin, IGameInstance game, String message){
        IChatMessenger.broadcastMessage(plugin, game.getPlayers(), message, null, false);
    }
}
package de.dercoolejulianhd.plugin_utilites.game;

import de.dercoolejulianhd.plugin_utilites.config.Config;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface GameInstance {

    String getName();
    World getWorld();
    Config getConfiguration();
    GameState getState();
    void setState(GameState state);
    Collection<String> getTeams();
    Collection<Player> getOnlinePlayers();
    Collection<Player> getSpectators();
    Location getLobbyLocation();
    Location getSpectatorLocation();
    void start();
    void stop();
    void unload();
}

package com.plugin.utilities.minigame.gamemap;
import com.api.dev.minigame.MiniGame;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface GameMap {

    /**
     * Description:
     * Loads the GameMap and Bukkit-World.
     */
    @Deprecated
    boolean load();


    /**
     * Description:
     * Unloads the GameMap and saves it by provided parameter.
     */
    void unload(boolean save);


    /**
     * Description:
     * Unloads and Loads the GameMap and Bukkit-World.
     * When calling this method The GameMap will reset.
     */
    boolean restore(boolean save);


    /**
     * Description:
     * saves the GameMap and Bukkit-World.
     */
    boolean save(boolean b);


    /**
     * Description:
     * creates the World Folder in Server's Root-Folder and copies all Files
     * from source into this folder so that Bukkit can load it.
     */
    @Deprecated
    void createActiveWorldFolder();


    /**
     * Description:
     * returns the name of GameMap's World-Folder which is most the Game-Name.
     */
    String getName();


    /**
     * Description:
     * returns the Folder which has all the source from World-Data which will be copied
     * by 'createActiveWorldFolder()' method into the Active World-Folder.
     */
    File getSourceFolder();


    /**
     * Description:
     * return the Folder which has the temporary Data from World where the game is Running.
     * This File will be deleted while game is Stopping
     */
    File getActiveWorldFolder();


    /**
     * Description:
     * returns the loaded Bukkit-World
     */
    World getGameWorld();


    /**
     * Description:
     * check if this GameMap is already loaded an in Progress while Running.
     */
    boolean isLoaded();


    /**
     * Description:
     * returns the Spectator Location where Players spawn if they
     * join the Game after it's lobby state or while Game is already
     * running.
     */
    Location getSpectatorLocation();


    /**
     * Description:
     * returns the Plugin which provides and is using the GameMap.
     */
    MiniGame getPlugin();
}


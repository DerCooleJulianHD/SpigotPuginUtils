package de.dercoolejulianhd.modulelibrary.gameworld;

import jdk.jfr.Description;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface GameWorld {
    /**
     * Description: returns the name of the active World when loaded.
     */
    @Description(" /* returns the name of the active World when loaded. */ ")
    String getName();


    /**
     * Description: returns the Folder which contains the Source of the World which will be copied on load.
     */
    @Description(" /* returns the Folder which contains the Source of the World which will be copied on load. */ ")
    File getSourceFolder();


    /**
     * Description: returns the Folder which will be created by loading.
     */
    @Description(" /* returns the Folder which will be created by loading. */ ")
    File getActiveWorldFolder();


    /**
     * Description: returns the Bukkit World of Game. This will be deleted on unloading process.
     */
    @Description(" /* returns the Bukkit World of Game. This will be deleted on unloading process. */ ")
    World getWorld();


    /**
     * Description: loads the Game World and setup for Playing.
     */
    @Description(" /* loads the Game World and setup for Playing. */ ")
    boolean load();


    /**
     * Description: returns true when 'load' method has been called before and the Game World is ready to play.
     */
    @Description(" /* returns true when 'load' method has been called before and the Game World is ready to play. */ ")
    boolean isLoaded();


    /**
     * Description: unloads Game World, resets all and loads it again.
     */
    @Description(" /* unloads Game World, resets all and loads it again. */ ")
    boolean restoreFromSource();


    /**
     * Description: unloads the Game World and deletes all temporary Files for reset.
     */
    @Description(" /* unloads the Game World and deletes all temporary Files for reset. */ ")
    void unload();


    /**
     * Description: deletes the temporary Files of the active World by unloading process.
     */
    @Description(" /* deletes the temporary Files of the active World by unloading process. */ ")
    void deleteActiveWorldFolder();


    /**
     * Description: deletes the source World Folder.
     */
    @Description(" /* deletes the source World Folder. */ ")
    void delete();


    /**
     * Description: returns the Plugin.
     */
    @Description(" /* returns the Plugin. */ ")
    Plugin getPlugin();
}

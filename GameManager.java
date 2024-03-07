package de.dercoolejulianhd.minigameapi.api.managers;

import de.dercoolejulianhd.minigameapi.api.MiniGame;
import de.dercoolejulianhd.minigameapi.api.game.Game;
import de.dercoolejulianhd.minigameapi.api.game.GameInstance;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GameManager {

    private final MiniGame miniGame;
    private final File dataFolder;

    private GameInstance game;

    public GameManager(MiniGame plugin) {
        this.miniGame = plugin;
        dataFolder = createDataFolder();
    }


    public void createGameDataFolder(String name){

    }

    private File createDataFolder() {
        File pluginFolder = miniGame.getDataFolder();
        if (!pluginFolder.exists()) pluginFolder.mkdirs();

        File dataFolder = new File(pluginFolder, "games");
        if (!dataFolder.exists()) dataFolder.mkdir();
        return dataFolder;
    }

    private GameInstance loadGameByName(String name) {
        List<String> strings = gameList();

        if (strings == null || strings.isEmpty() || !strings.contains(name)) {
            return null;
        }

        return new Game(miniGame, name, true);
    }

    public List<String> gameList(){
        String[] stringArray = dataFolder.list();

        if (stringArray == null) {
            return null;
        }

        return Arrays.stream(stringArray).toList();
    }


    public MiniGame getPlugin() {
        return miniGame;
    }

    public GameInstance getGame() {
        return game;
    }

    public File getDataFolder() {
        return dataFolder;
    }
}

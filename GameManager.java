package de.dercoolejulianhd.pluginutilities.game;

import de.dercoolejulianhd.pluginutilities.game.interfaces.GameInstance;
import de.dercoolejulianhd.pluginutilities.game.interfaces.MiniGamePlugin;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GameManager {

    private final MiniGamePlugin plugin;
    private final File dataFolder;

    private GameInstance game;

    public GameManager(MiniGamePlugin plugin) {
        this.plugin = plugin;
        dataFolder = loadDataFolder();
    }


    public void createGameData(String name, int minPlayerValue, int maxPlayerValue){
        File folder = GameInstance.getDataFolder(plugin, name);

        Game game = new Game(plugin, folder, name, false);

        ConfigurationSection data = game.getConfiguration().getData();
        data.set("min-players", minPlayerValue);
        data.set("max-players", maxPlayerValue);
        data.createSection("teams");

        game.getConfiguration().save();
    }

    private File loadDataFolder() {
        File pluginFolder = plugin.getBukkitPlugin().getDataFolder();
        if (!pluginFolder.exists()) pluginFolder.mkdirs();

        File dataFolder = new File(pluginFolder, "games");
        if (!dataFolder.exists()) dataFolder.mkdir();
        return dataFolder;
    }
    @Nullable
    private GameInstance loadGameByName(String name) {
        List<String> strings = gameList();

        if (strings == null || strings.isEmpty() || !strings.contains(name)) {
            return null;
        }

        File folder = GameInstance.getDataFolder(plugin, name);

        if (folder == null) return null;

        return new Game(plugin, folder, name, true);
    }

    public List<String> gameList(){
        String[] stringArray = dataFolder.list();

        if (stringArray == null) {
            return null;
        }

        return Arrays.stream(stringArray).toList();
    }


    public MiniGamePlugin getPlugin() {
        return plugin;
    }

    public GameInstance getGame() {
        return game;
    }

    public File getDataFolder() {
        return dataFolder;
    }
}

package de.dercoolejulianhd.pluginutilities.game;

import de.dercoolejulianhd.pluginutilities.game.interfaces.GameInstance;
import de.dercoolejulianhd.pluginutilities.game.interfaces.MiniGamePlugin;
import de.dercoolejulianhd.pluginutilities.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

public class GameConfig extends Config {

    private final MiniGamePlugin miniGamePlugin;
    private final GameInstance game;

    public GameConfig(MiniGamePlugin plugin, File dataFolder, Game game) {
        super(plugin.getBukkitPlugin(), dataFolder, "settings.yml");
        this.miniGamePlugin = plugin;
        this.game = game;
    }


    @Override
    public void setDefaults() {
        ConfigurationSection data = getData();
        data.set("name", game.getName());

        World world = game.getMap().getWorld();
        data.set("world", world.getName());

        save();
    }

    public ConfigurationSection getData() {
        String dataPath = "game-data";
        if (getConfiguration().getConfigurationSection(dataPath) == null) getConfiguration().createSection(dataPath);

        return getConfiguration().getConfigurationSection(dataPath);
    }

    public MiniGamePlugin getMiniGamePlugin() {
        return miniGamePlugin;
    }

    public GameInstance getGame() {
        return game;
    }

    public int getMinPlayers() {
        return getData().getInt("min-players");
    }

    public int getMaxPlayers() {
        return getData().getInt("max-players");
    }

    @Nullable
    public Location loadLocation(String name) {
        ConfigurationSection locationSection = getData().getConfigurationSection(name);

        if (locationSection == null) return null;

        double x = locationSection.getDouble("x");
        double y = locationSection.getDouble("y");
        double z = locationSection.getDouble("z");

        Location location = new Location(game.getMap().getWorld(), x,y,z);

        if (locationSection.isSet("yaw") && locationSection.isSet("pitch")) {
            float yaw = (float) locationSection.getDouble("yaw");
            float pitch = (float) locationSection.getDouble("pitch");

            location.setYaw(yaw);
            location.setPitch(pitch);
        }

        if (locationSection.isSet("world")) {
            String worldName = locationSection.getString("world");

            if (worldName == null) return location;
            if (Objects.requireNonNull(location.getWorld()).getName().equalsIgnoreCase(worldName)) return location;

            location.setWorld(Bukkit.getWorld(worldName));
        }

        return location;
    }
}

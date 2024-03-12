package de.dercoolejulianhd.minigame.bedwars.library.game.config;

import de.dercoolejulianhd.minigame.bedwars.library.MiniGamePlugin;
import de.dercoolejulianhd.minigame.bedwars.library.game.Game;
import de.dercoolejulianhd.minigame.bedwars.library.game.interfaces.GameInstance;
import de.dercoolejulianhd.minigame.bedwars.library.utils.Config;
import de.dercoolejulianhd.minigame.bedwars.plugin.teams.interfaces.MinigameTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

public class GameConfig extends Config {

    private final MiniGamePlugin miniGamePlugin;
    private final GameInstance game;

    public GameConfig(MiniGamePlugin plugin, File dataFolder, Game game) {
        super(plugin.getBukkitPlugin(), dataFolder, "settings.yml");

        if (!exists()) createNewConfiguration();
        if (!isLoaded()) loadConfiguration();

        this.miniGamePlugin = plugin;
        this.game = game;
    }


    @Override
    public void setDefaults() {
        ConfigurationSection data = getData();
        data.set("name", game.getName());
        save();
    }

    public ConfigurationSection getData() {
        String dataPath = "game-data";
        if (getConfiguration().getConfigurationSection(dataPath) == null) getConfiguration().createSection(dataPath);

        return getConfiguration().getConfigurationSection(dataPath);
    }

    public void addTeam(MinigameTeam team) {
        ConfigurationSection teams = getData().getConfigurationSection("teams");
        if (teams == null) return;

        ConfigurationSection section = teams.createSection(team.getName());
        section.set("name", team.getName());
        section.set("color", team.getColor().name());
        section.set("max-players", team.getMaxPlayers());

        save();
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
    public Location loadLocation(ConfigurationSection data, String name) {
        ConfigurationSection locationSection = data.getConfigurationSection(name);

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

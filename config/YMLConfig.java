package de.dercoolejulianhd.plugin.utils.config;

import com.avaje.ebean.validation.NotNull;
import jdk.jfr.Description;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;

/**
 * Uses Configuration type: YAMLConfiguration !.
 * @since   1.0
 */
@Description(" /* Uses Configuration type: YAMLConfiguration ! */ ")
public abstract class Configiguration {

    public static Logger logger = LogManager.getLogger(this.getClass());

    private final Plugin plugin;
    private final File file, dir;
    private FileConfiguration fileConfiguration;

    public YMLConfig(Plugin plugin, File dir, String fileName, boolean loadOnInit) {
        this.plugin = plugin;
        this.dir = dir;
        this.file = new File(dir, fileName + ".yml");

        if (loadOnInit) {
            if (!this.exists()) this.createConfigFiles(false, null);
            this.load();
        }
    }

    public native void onLoad();
    public native void onCreate();

    /**
     * Description: creates Configuration-Files and setup defaults when Config is created first.
     */
    @Description(" /* creates Configuration-Files and setup defaults when Config is created first */ ")
    public void createConfigFiles(boolean defaults, Map<ConfigurationSection, Map<String, Object>> values) {
        this.onCreate();
        try {
            this.dir.mkdirs();
            this.file.createNewFile();

            if (defaults) {
                this.load();
                this.setDefaults(values);
            }
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.CONFIG, exception.fillInStackTrace().toString());
        }
    }


    /**
     * Description: loads configuration.
     */
    @NotNull
    @Description("  /* loads configuration. */  ")
    public void load() {
        this.onLoad();
        try {
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file); /* load */
        } catch (Exception exception){
            Bukkit.getLogger().log(Level.CONFIG, exception.fillInStackTrace().toString());
        }
    }


    /**
     * Description: unloads configuration when configuration object returns not null.
     */
    @NotNull
    @Description("  /* unloads configuration when configuration object returns not null */  ")
    public void unload() {

        if (!isLoaded()) {
            return;
        }

        this.save();
        this.fileConfiguration = null;
    }


    /**
     * Description: saves configuration changes.
     */
    @NotNull
    @Description("  /* saves configuration changes. */  ")
    public void save() {
        try {
            this.fileConfiguration.save(this.file); /* save */
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.CONFIG, exception.fillInStackTrace().toString());
        }
    }


    /**
     * Description: returns object that was saved in configuration before.
     */
    @NotNull
    @Description("  /* returns object that was saved in configuration before. */  ")
    public Object getObject(ConfigurationSection section, String path) {

        if (!this.exists()) {
            return null;
        }

        if (!this.isLoaded()) {
            this.load();
        }

        if (section == null) {
            throw new NullPointerException("provided section could not be found in config: '" + this.file.getName() + "'!");
        }

        return section.get(path);
    }


    /**
     * Description: sets an object in the loaded configuration file.
     */
    @NotNull
    @Description("  /* sets an object in the loaded configuration file. */  ")
    public void set(ConfigurationSection section, String path, Object o) {

        if (!this.exists()) {
            return;
        }

        if (!this.isLoaded()) {
            this.load();
        }

        if (section == null) {
            throw new NullPointerException("provided section could not be found in config: '" + this.file.getName() + "'!");
        }

        section.set(path, o);
    }


    /**
     * Description: sets the default values when the config is created first.
     */
    @NotNull
    @Description("  /* sets the default values when the config is created first. */  ")
    public void setDefaults(Map<ConfigurationSection, Map<String, Object>> defaults) {

        if (!this.exists()) {
            return;
        }

        if (!this.isLoaded()) {
            this.load();
        }

        if (defaults == null) {
            return;
        }

        defaults.forEach((section, value) -> {
            value.forEach((path, object) -> this.set(section, path, object));
        });
    }


    /**
     * Description:
     * loads a specific Location by getting values of block-coordinates and world.
     */
    @NotNull
    @Description("  /* loads a specific Location by getting values of block-coordinates and world */ ")
    public Location loadLocation(ConfigurationSection section, String path) {
        String worldName = this.getObject(section, path + ".world").toString();
        double x = (double) this.getObject(section, path + ".x");
        double y = (double) this.getObject(section, path + ".y");
        double z = (double) this.getObject(section, path + ".z");
        Location location = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        if (worldName != null) location.setWorld(Bukkit.getWorld(worldName));
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        if (section.contains(path + ".yaw")) location.setYaw((float) this.getObject(section, path + ".yaw"));
        if (section.contains(path + ".pitch")) location.setPitch((float) this.getObject(section, path + ".pitch"));
        return location;
    }


    /**
     * Description:
     * loads a specific Location by getting values of block-coordinates and world.
     */
    @NotNull
    @Description("  /* loads a specific Location by getting values of block-coordinates and world */ ")
    public Location loadLocation(ConfigurationSection section) {
        String worldName = this.getObject(section,"world").toString();
        double x = (double) this.getObject(section,"x");
        double y = (double) this.getObject(section,"y");
        double z = (double) this.getObject(section,"z");
        Location location = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        if (worldName != null) location.setWorld(Bukkit.getWorld(worldName));
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        if (section.contains("yaw")) location.setYaw((float) this.getObject(section,"yaw"));
        if (section.contains("pitch")) location.setPitch((float) this.getObject(section,"pitch"));
        return location;
    }


    /**
     * Description: sets a specific Location by setting value of block-coordinates and world.
     */
    @NotNull
    @Description("  /* sets a specific Location by setting value of block-coordinates and world */ ")
    public void setLocation(ConfigurationSection section, String path, @Nullable World world, double x, double y, double z, @Nullable Float yaw, @Nullable Float pitch) {
        if (world != null) this.set(section, path + ".world", world.getName());
        this.set(section, path + ".x", x);
        this.set(section, path + ".y", y);
        this.set(section, path + ".z", z);
        if (yaw == null && pitch == null) return;
        this.set(section, path + ".yaw", yaw);
        this.set(section, path + ".pitch", pitch);
    }


    /**
     * Description: sets a specific Location by setting value of block-coordinates and world.
     */
    @NotNull
    @Description("  /* sets a specific Location by setting value of block-coordinates and world */ ")
    public void setLocation(ConfigurationSection section, @Nullable World world, double x, double y, double z, @Nullable Float yaw, @Nullable Float pitch) {
        if (world != null) this.set(section, "world", world.getName());
        this.set(section,"x", x);
        this.set(section,"y", y);
        this.set(section,"z", z);
        if (yaw == null && pitch == null) return;
        this.set(section,"yaw", yaw);
        this.set(section,"pitch", pitch);
    }


    /**
     * Description: returns true when directory & file is existing.
     */
    @Description("  /* returns true when directory & file is existing */  ")
    public boolean exists(){
        return (this.dir.exists() && this.file.exists());
    }


    /**
     * Description: returns true when configuration is loaded already and configuration object returns not null.
     */
    @Description("  /* returns true when configuration is loaded already and configuration object returns not null */  ")
    public boolean isLoaded() {
        return this.fileConfiguration != null;
    }


    /**
     * Description: returns the file which is loaded.
     */
    @Description("  /* returns the file which is loaded */  ")
    public File getFile() {
        return this.file;
    }


    /**
     * Description: returns file-configuration which has loaded the file.
     */
    @Nullable
    @Description("  /* returns file-configuration which has loaded the file */  ")
    public FileConfiguration getFileConfig() {
        return this.fileConfiguration;
    }


    /**
     * Description: returns plugin which is using this config
     */
    @Description("  /* returns plugin which is using this config */  ")
    public Plugin getPlugin() {
        return plugin;
    }
}

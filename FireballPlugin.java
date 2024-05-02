import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FireballPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (event.getAction().toString().contains("RIGHT_CLICK") && player.getItemInHand().getType() == Material.BLAZE_ROD) {
            Location eye = player.getEyeLocation();
            Location target = eye.add(eye.getDirection().multiply(2));
            
            Fireball fireball = (Fireball) player.getWorld().spawnEntity(eye.add(eye.getDirection().multiply(2)), EntityType.FIREBALL);
            fireball.setDirection(eye.getDirection());
            
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                if (fireball.isOnGround()) {
                    player.getWorld().createExplosion(fireball.getLocation(), 4.0F);
                    fireball.remove();
                }
            }, 20L);
        }
    }
}

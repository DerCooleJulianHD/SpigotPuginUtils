package de.dercoolejulianhd.plugin.utils.minigameapi.game.listener;

import de.dercoolejulianhd.plugin.utils.minigameapi.game.Game;
import de.dercoolejulianhd.plugin.utils.minigameapi.minigameplugin.MinigamePlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record LobbyListener(MinigamePlugin plugin, Game game) implements Listener {

    public static final Logger logger = LogManager.getLogger(LobbyListener.class);

    @EventHandler
    public void handleInteractItem(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        if (event.getItem() == null) return;
        if (event.getItem().getItemMeta() == null) return;
        if (event.getItem().getType() != Material.SLIME_BALL) return;
        player.kickPlayer(ChatColor.RED + "Disconnected");
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String joinMessage = ChatColor.translateAlternateColorCodes('&', plugin.getPluginConfiguration().getObject(plugin.getPluginConfiguration().getFileConfig(), "connection.join").toString()).replaceAll("%player%", player.getName());
        event.setJoinMessage(joinMessage);
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String quitMessage = ChatColor.translateAlternateColorCodes('&', plugin.getPluginConfiguration().getObject(plugin.getPluginConfiguration().getFileConfig(), "connection.quit").toString()).replaceAll("%player%", player.getName());
        event.setQuitMessage(quitMessage);
    }

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().isOp()) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().isOp()) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void handleEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleBlockExplode(BlockExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handleBlockPhysics(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }
}

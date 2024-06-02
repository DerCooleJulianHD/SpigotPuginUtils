package com.plugin.utils.minigame.api.utils;

import com.plugin.utils.minigame.api.IMinigameAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;


public interface IChatMessenger {

    static void send(Plugin plugin, Player player, String s, ClickEvent event, boolean sound) {
        try {
            String prefix = ((IMinigameAPI) plugin).getPrefix();
            BaseComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', prefix) + prefix);
            message.addExtra(s);
            if (event != null) message.setClickEvent(event);
            player.spigot().sendMessage(message);
            if (sound) player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 2, 0);
        } catch (ClassCastException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Provided Plugin couldn't be cast to IMiniGameAPI: \n" + ex.fillInStackTrace().toString());
        }
    }

    static void broadcastMessage(Plugin plugin, Collection<Player> receivers, String s, ClickEvent event, boolean sound) {
        receivers.forEach(player -> send(plugin, player, s, event, sound));
    }

    static BaseComponent getFormatedMessage(Plugin plugin, String s, ClickEvent event){
        try {
            String prefix = ((IMinigameAPI) plugin).getPrefix();
            BaseComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', prefix) + prefix);
            message.addExtra(s);
            if (event != null) message.setClickEvent(event);
            return message;
        } catch (ClassCastException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Provided Plugin couldn't be cast to IMiniGameAPI: \n" + ex.fillInStackTrace().toString());
        }
        return null;
    }
}

package de.dercoolejulianhd.plugin_utilites.timer;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IPlayerFileData;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MessageOutput {


    public static void sendActionBar(Player player, String message) {
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a(message);
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chatBaseComponent, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }


    public static void sendBossBar(Player player, String message) {

    }


    public static void send(Player player, String message) {
        player.sendMessage(message);
    }


    public static void sendTitle(Player player, String title, String subtitle, double fadeIn, double stay, double fadeOut) {

    }
}

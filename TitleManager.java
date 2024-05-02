import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleManager {
    
    // Methode zum Senden eines Titels an einen Spieler mithilfe von Packets
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, craftPlayer.getHandle().a(title));
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, craftPlayer.getHandle().a(subtitle), fadeIn, stay, fadeOut);
        craftPlayer.getHandle().playerConnection.sendPacket(titlePacket);
        craftPlayer.getHandle().playerConnection.sendPacket(subtitlePacket);
    }


    public static void sendActionBar(Player player, String title) {
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a(title);
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chatBaseComponent, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }
}

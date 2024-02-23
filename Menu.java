package de.dercoolejulianhd.pluginutilities.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface Menu {

    Player getViewer();

    Inventory getInventory();
    int size();
    String getTitle();

    Map<ItemStack, Integer> itemList();

    double getAnimationTicks();
    int getAnimationTicksAsSeconds();

    void open();
    void close();

}

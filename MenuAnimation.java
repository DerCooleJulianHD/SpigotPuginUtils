package de.dercoolejulianhd.pluginutilities.menu;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class MenuAnimation extends BukkitRunnable {

    private final Map<ItemStack, Integer> itemList;
    private final Plugin plugin;
    private final Menu menu;

    public MenuAnimation(Plugin plugin, Menu menu, int animationTicks, Map<ItemStack, Integer> itemList) {

        this.plugin = plugin;
        this.menu = menu;
        this.itemList = new HashMap<>(itemList);

        runTaskTimer(plugin, 10, animationTicks);
    }

    @Override
    public void run() {

        if (!itemList.isEmpty()) {

            for (ItemStack itemStack : itemList.keySet()) {
                int slot = itemList.get(itemStack);

                setItem(slot, itemStack);
                itemList.remove(itemStack);

                return;
            }
        }

        menu.getViewer().playSound(menu.getViewer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2);
        cancel();
    }

    private void setItem(int slot, ItemStack itemStack){
        menu.getInventory().setItem(slot, itemStack);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Menu getMenu() {
        return menu;
    }

    public Map<ItemStack, Integer> getItemList() {
        return itemList;
    }
}

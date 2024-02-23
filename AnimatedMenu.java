package de.dercoolejulianhd.pluginutilities.menu;


import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AnimatedMenu implements Menu {

    private final Plugin plugin;
    private final Player player;

    private final int size;
    private final String title;
    private final double animationTicks;
    private final Inventory inventory;
    private final List<ItemStack> itemList;

    public AnimatedMenu(Plugin plugin, Player player, int size, String title, double animationTicks, List<ItemStack> itemList /* provides ItemList directly */ ) {
        this.plugin = plugin;
        this.player = player;
        this.title = title;
        this.animationTicks = animationTicks;

        this.size = size;
        this.itemList = itemList;

        this.inventory = Bukkit.createInventory(player, size, title);
    }

    public AnimatedMenu(Plugin plugin, Player player, int size, String title, double animationTicks) /* you have to provide your items self by using "initializeItems()" method */ {
        this.plugin = plugin;
        this.player = player;
        this.title = title;
        this.animationTicks = animationTicks; /* Speed per Item set */

        this.size = size;
        this.itemList = new ArrayList<>(); /* List of Items that will be set in the Menu */

        this.inventory = Bukkit.createInventory(player, size, title);
    }

    public abstract void initializeItems(); /* sets Items in the ItemList for loading it into Inventory on Open */

    @Override
    public Player getViewer() {
        return player;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Map<ItemStack, Integer> itemList() {
        return null;
    }

    @Override
    public double getAnimationTicks() {
        return animationTicks;
    }

    @Override
    public int getAnimationTicksAsSeconds() {
        return (int) animationTicks;
    }

    @Override
    public void open() {
        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 2, 1);

        new MenuAnimation(plugin, this, getAnimationTicksAsSeconds(), itemList());
    }

    @Override
    public void close() {
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 2, -3);
    }
}

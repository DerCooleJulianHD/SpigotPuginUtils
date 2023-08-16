package main;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    // Attribute
    public final ItemStack itemStack;
    private final ItemMeta itemMeta;
    private int id;

    // new Item //
    public ItemBuilder(Material material, int id, int amount) {
        this.itemStack = new ItemStack(material, amount, (byte) id);
        this.id = id;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String displayName){
        itemMeta.setDisplayName(displayName);
        return this;
    }
    public ItemBuilder setLore(String... content){
        itemMeta.setLore(List.of(content));
        return this;
    }
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }
    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }
    public ItemBuilder setHideEnchants(boolean hidden){
        if (hidden) itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }
    public ItemStack toItemStack() {
        // creating new Item //
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemMeta getItemMeta() {
        return itemMeta;
    }
    public int getId() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
}

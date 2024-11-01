package de.dercoolejulianhd.modulelibrary.itemstackbuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackBuilder {

    public static final Logger logger = LogManager.getLogger(ItemStackBuilder.class);
    private final ItemStack itemStack;
    private final Material material;
    private ItemMeta itemMeta;

    private String displayName = "";
    private int amount = 1;
    private int id = 0;
    private boolean unbreakable = false;

    public ItemStackBuilder(Material material, boolean unbreakable) {
        this.material = material;
        this.itemStack = new ItemStack(material,1);
        this.amount = itemStack.getAmount();
        this.itemMeta = this.itemStack.getItemMeta();
        this.itemMeta.spigot().setUnbreakable(unbreakable);
        this.unbreakable = unbreakable;
    }

    public ItemStackBuilder(Material material, int amount, boolean unbreakable) {
        this.material = material;
        this.itemStack = new ItemStack(material,1);
        this.amount = amount;
        this.itemMeta = this.itemStack.getItemMeta();
        this.itemMeta.spigot().setUnbreakable(unbreakable);
        this.unbreakable = unbreakable;
    }

    public ItemStackBuilder(Material material, int id, int amount, boolean unbreakable) {
        this.material = material;
        this.itemStack = new ItemStack(material, amount, (byte) id);
        this.id = id;
        this.amount = itemStack.getAmount();
        this.itemMeta = this.itemStack.getItemMeta();
        this.itemMeta.spigot().setUnbreakable(unbreakable);
        this.unbreakable = unbreakable;
    }
    public ItemStackBuilder(Material material, int id, int amount, ItemMeta meta) {
        this.material = material;
        this.itemStack = new ItemStack(material, amount, (byte) id);
        this.id = id;
        this.amount = itemStack.getAmount();
        this.itemStack.setItemMeta(meta);
        this.itemMeta = this.itemStack.getItemMeta();
    }


    public ItemStackBuilder setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        this.displayName = displayName;
        return this;
    }

    public ItemStackBuilder setId(int id) {
        return new ItemStackBuilder(this.itemStack.getType(), id, this.itemMeta.spigot().isUnbreakable());
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.getItemStack();
    }

    public ItemStack build(ItemMeta meta) {
        this.setItemMeta(meta);
        this.itemStack.setItemMeta(meta);
        return this.getItemStack();
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getId() {
        return this.id;
    }

    public ItemMeta getItemMeta() {
        return this.itemMeta;
    }

    public Material getMaterial() {
        return material;
    }

    public void setItemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }
}

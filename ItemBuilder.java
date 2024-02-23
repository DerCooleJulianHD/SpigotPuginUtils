package de.dercoolejulianhd.pluginutilities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder /* creates ann ItemStack easier with parameters */ {

    private final Material material;
    private final int amount;
    private final int id;
    protected final ItemStack itemStack;

    private String displayName;
    private boolean enchanted;
    private boolean enchantsHidden;
    private boolean unbreakable;
    private Map<Enchantment, Integer> enchantments;
    private List<ItemFlag> itemFlags;

    public ItemBuilder(Material material, int id, int amount) {
        this.material = material;
        this.id = id;
        this.amount = amount;
        itemStack = new ItemStack(material, amount, (byte) id);
    }

    private static ItemBuilder createItemPiece(Material material , int id , int amount, String displayName, Map<Enchantment, Integer> enchantments, boolean hideEnchants, List<ItemFlag> itemFlags, boolean unbreakable) /* creates an Item with full specks on scheme: type, subid from item, how many */ {

       ItemBuilder itemBuilder = new ItemBuilder(material, id, amount);
       itemBuilder.setDisplayName(displayName);

       if (enchantments != null) {

           itemBuilder.setEnchantments(enchantments);

           for (Enchantment enchantment : enchantments.keySet()) {

               int level = enchantments.get(enchantment);
               itemBuilder.addEnchant(enchantment, level);
           }

           if (hideEnchants) itemBuilder.addItemFlag(ItemFlag.HIDE_ENCHANTS);
       }

       if (itemFlags != null) {

           itemBuilder.setItemFlags(itemFlags);

           for (ItemFlag flag : itemFlags) {

               itemBuilder.addItemFlag(flag);
           }
       }

       if (unbreakable) itemBuilder.setUnbreakable(true);
       return itemBuilder;
    }

    public void addEnchant(Enchantment enchantment, int level){

        if (enchantments == null) {
            setEnchantments(new HashMap<>());
        }

        enchantments.put(enchantment, level);

    }

    public void addItemFlag(ItemFlag itemFlag){

        if (itemFlags == null) {
            setItemFlags(new ArrayList<>());
        }

        itemFlags.add(itemFlag);

    }

    public Material getMaterial() {
        return material;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isEnchanted() {
        return enchanted;
    }

    public void setEnchanted(boolean enchanted) {
        this.enchanted = enchanted;
    }

    public boolean isEnchantsHidden() {
        return enchantsHidden;
    }

    public void setEnchantsHidden(boolean enchantsHidden) {
        this.enchantsHidden = enchantsHidden;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public List<ItemFlag> getItemFlags() {
        return itemFlags;
    }

    public void setItemFlags(List<ItemFlag> itemFlags) {
        this.itemFlags = itemFlags;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    public ItemStack build() /* creates the final ItemStack */ {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return itemStack;

        meta.setDisplayName(getDisplayName());
        meta.setUnbreakable(isUnbreakable());

        getItemFlags().forEach(meta::addItemFlags);

        itemStack.addEnchantments(getEnchantments());
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}

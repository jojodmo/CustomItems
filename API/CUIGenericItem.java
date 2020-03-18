package com.jojodmo.customitems.api;

import com.jojodmo.customitems.item.generic.GenericItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class CUIGenericItem{

    private final Material material;
    private final CUIItem customItem;

    public CUIGenericItem(Material material){this(material, null);}
    public CUIGenericItem(CUIItem customItem){this(null, customItem);}

    private CUIGenericItem(Material material, CUIItem customItem){
        this.material = material;
        this.customItem = customItem;
    }

    public CUIGenericItem(String id){this(id, false);}
    public CUIGenericItem(String id, boolean caseSensitive){
        GenericItem gi = GenericItem.get(id, 1, !caseSensitive);
        if(gi == null){
            this.material = null;
            this.customItem = null;
        }
        else if(gi.isCustomItem()){
            this.material = null;
            this.customItem = new CUIItem(gi.getCustomItem());
        }
        else{
            this.material = gi.getMaterial();
            this.customItem = null;
        }
    }

    /**
     * @return true iff the item exists either as a custom item or native Minecraft item
     */
    public boolean exists(){
        return this.material != null || this.customItem != null;
    }

    /**
     * @return the custom item represented by this object, or null
     */
    public CUIItem getCustomItem(){
        return this.customItem;
    }

    /**
     * @return the native Minecraft item represented by this object, or null
     */
    public Material getNativeItem(){
        return this.material;
    }

    /**
     * @return true iff the item is a custom item
     */
    public boolean isCustomItem(){
        return this.customItem != null;
    }

    /**
     * @param amount the amount to get in the ItemStack
     * @return the ItemStack represented by this object, or null if this object does not represent any item
     */
    public ItemStack getItemStack(int amount){
        return this.customItem != null ? this.customItem.getItemStack(amount) : (this.material != null ? new ItemStack(this.material, amount) : null);
    }
}

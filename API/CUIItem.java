package com.jojodmo.customitems.api;

import com.jojodmo.customitems.item.custom.CustomItem;
import com.jojodmo.customitems.item.custom.handler.CustomItemHandler;
import org.bukkit.inventory.ItemStack;

public class CUIItem{

    private CustomItem wrapped;

    protected CUIItem(CustomItem item){
        wrapped = item;
    }

    public CUIItem(String id){
        this(id, false);
    }

    public CUIItem(String id, boolean caseSensitive){
        wrapped = CustomItem.get(id, !caseSensitive);
    }

    public boolean exists(){
        return this.wrapped != null;
    }

    public String getID(){
        return this.wrapped.getId();
    }

    public ItemStack getItemStack(){
        return getItemStack(1);
    }

    public ItemStack getItemStack(int amount){
        return wrapped.getAmountItemStack(amount);
    }
}

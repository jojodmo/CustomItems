/*
   Copyright 2019 jojodmo

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.jojodmo.customitems.api;

import com.jojodmo.customitems.Main;
import com.jojodmo.customitems.item.custom.CustomItem;
import com.jojodmo.customitems.item.custom.handler.CustomItemDamageHandler;
import com.jojodmo.customitems.item.custom.handler.CustomItemHandler;
import com.jojodmo.customitems.item.custom.block.CustomItemBlockHandler;
import com.jojodmo.customitems.item.generic.GenericItem;
import com.jojodmo.customitems.item.generic.action.handler.GenericItemActionHandler;
import com.jojodmo.customitems.util.ConfigUtil;
import com.jojodmo.customitems.util.classes.CUIPlugin;
import com.jojodmo.customitems.util.classes.DoubleTuple;
import com.jojodmo.customitems.version.SafeMaterial;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CustomItems API
 *
 * Most of these API functions will only work if the plugin is enabled.
 * Use the isEnabled() method to check if the plugin is enabled.
 */
public class CustomItemsAPI{

    /**
     * Enables the plugin to be used by the Custom Items API
     */
    private static boolean isSetup;
    public static void setup(){
        if(isSetup){return;}
        isSetup = CUIPlugin.setup();

        if(!isSetup){
            Main.that.getServer().getScheduler().runTaskLater(Main.that, () -> Main.that.getPluginLoader().disablePlugin(Main.that), 6000L);
        }
    }

    //TODO: ALLOW GETTING RECIPES OF CUSTOM ITEMS

    /**
     * Most API functions will only work if the plugin is enabled (if this functions returns true)
     * @return true iff the plugin is enabled
     */
    public static boolean isEnabled(){
        return Main.that.isSetup();
    }

    /**
     * @param stack - the ItemStack
     * @return the ID of the Custom Item, or null of stack is not a Custom Item
     */
    public static String getCustomItemID(ItemStack stack){
        CustomItem ci = CustomItemHandler.getCustomItem(stack);
        return ci == null ? null : ci.getId();
    }

    /*
    *
    * GETTING CUSTOM ITEMS
    *
     */

    /**
     * Get the ItemStack for the Custom Item with the id ID
     * Alias for getCustomItem(id, 1, true)
     * @param id - the ID of the Custom Item to get, case-insensitive
     * @return the ItemStack for the Custom Item with the ID id, or null if the Custom Item does not exist
     */
    public static ItemStack getCustomItem(String id){
        return getCustomItem(id, 1);
    }

    /**
     * Get the ItemStack for the Custom Item with the id ID, case-insensitive
     * Alias for getCustomItem(id, amount, true)
     * @param id - the ID of the Custom Item to get, case-insensitive
     * @param amount - the amount of items in the ItemStack
     * @return the ItemStack for the Custom Item with the ID id, or null if the Custom Item does not exist
     */
    public static ItemStack getCustomItem(String id, int amount){
        return getCustomItem(id == null ? null : id.toLowerCase(), amount, false);
    }

    /**
     * Get the ItemStack for the Custom Item with the ID id
     * Alias for getCustomItem(id, amount, true)
     * @param id - the ID of the Custom Item to get, Case-sensitive if caseSensitive is true.
     * @param amount - the amount of items in the ItemStack
     * @param caseSensitive - set to true to make sure the stack's ID matches id case-sensitively
     * @return the ItemStack for the Custom Item with the ID id, or null if the Custom Item does not exist
     */
    public static ItemStack getCustomItem(String id, int amount, boolean caseSensitive){
        if(id == null || id.length() == 0){return null;}
        CustomItem ci = CustomItem.get(id, !caseSensitive);
        if(ci == null){
            id = id.replaceAll("^([Cc]ustom[Ii]tems?|[Cc][Uu][Ii]s?):(.+)$", "$2");
            ci = CustomItem.get(id, !caseSensitive);
        }
        return ci == null ? null : ci.getAmountItemStack(amount);
    }

    /**
     * Lists all valid Custom Item IDs on the server. You can then get an ItemStack for each item using getCustomItem(String id)
     **/
    public static List<String> listCustomItemIDs(){
        List<String> result = new ArrayList<>();
        for(CustomItem ci : CustomItemHandler.allCustomItems){
            result.add(ci.getId());
        }
        return result;
    }

    /**
     * Lists all valid Custom Item IDs for blocks on the server. You can then get an ItemStack for each item using getCustomItem(String id)
     **/
    public static List<String> listBlockCustomItemIDs(){
        List<String> result = new ArrayList<>();
        for(CustomItem ci : CustomItemHandler.allCustomItems){
            if(ci.canBePlaced()){
                result.add(ci.getId());
            }
        }
        return result;
    }

//    /*
//    *
//    * RECIPES
//    *
//     */
//
//    public static  List<Recipe> getCustomItemRecipes(String id){
//        return getCustomItemRecipes(id, false);
//    }
//
//    public static List<Recipe> getCustomItemRecipes(String id, boolean caseSensitive){
//        CustomItem ci = CustomItem.get(id, !caseSensitive);
//        if(ci == null){return null;}
//        GenericItem gi = new GenericItem(ci, 1);
//        List<GenericItemRecipe> rl = GenericItemRecipeHandler.getRecipes(gi);
//    }

    /*
    *
    * WORLD INFORMATION
    *
     */

    /**
     * Get the ID of the Custom Item at the block
     * @param block - the block to get the Custom Item ID at
     * @return the ID of the Custom Item of the block, or null if the block is not Custom
     */
    public static String getCustomItemIDAtBlock(Block block){
        CustomItem ci = CustomItemBlockHandler.get(block.getLocation());
        return ci == null ? null : ci.getId();
    }

    /**
     * Set the Block to the specified Custom Item. Shorthand for setCustomItemIDAtBlock(block, id, doBlockUpdate, false)
     * @param block - the Block to set
     * @param id - the ID of the Custom Item to get, case-insensitive.
     * @param doBlockUpdate - whether or not to do a block update. This should be true in almost all cases.
     * @return the Block that has been updated, or null if the CustomItem id you provided doesn't exist. If you provide {@code null} for id, this will always return the block
     */
    public static Block setCustomItemIDAtBlock(Block block, String id, boolean doBlockUpdate){
        return setCustomItemIDAtBlock(block, id, doBlockUpdate, false);
    }

    /**
     * Set the Block to the specified Custom Item.
     * @param block - the Block to set
     * @param id - the ID of the Custom Item to get, Case-sensitive if caseSensitive is true. Set this to null to remove the block at the given location
     * @param doBlockUpdate - whether or not to do a block update. This should be true in almost all cases.
     * @param caseSensitive - set to true to make sure the stack's ID matches id case-sensitively
     * @return the Block that has been updated, or null if the CustomItem id you provided doesn't exist. If you provide {@code null} for id, this will always return the block
     */
    public static Block setCustomItemIDAtBlock(Block block, String id, boolean doBlockUpdate, boolean caseSensitive){
        if(id == null){
            return breakCustomItemAtBlock(block, doBlockUpdate);
        }
        CustomItem ci = CustomItem.get(id, !caseSensitive);
        if(ci == null){return null;}
        return CustomItemBlockHandler.place(block, ci, doBlockUpdate);
    }

    /**
     * Remove the CustomItems block at the given location, WITHOUT DROPPING ITEMS.
     * @param block - the block to remove
     * @param doBlockUpdate - whether or not to do a block update. This should be true in almost all cases.
     * @return the block that has been updated, or {@code null} if the block was not placed by CustomItems
     */
    public static Block breakCustomItemAtBlock(Block block, boolean doBlockUpdate){
        return CustomItemBlockHandler.remove(block, doBlockUpdate);
    }

    /**
     * Break the given block, and return the drops instead of dropping them normally.
     * @param block - the block that should be broken
     * @param player - the player that broke the block
     * @return
     *      {@code null} if the block is not a custom block, and a two-element entry otherwise:
     *          The first entry is false if CustomItems cancelled the block break, and true if the block was broken.
     *          The second entry is a list of the items that the block would have normally dropped
     */
    public static AbstractMap.SimpleEntry<Boolean, List<ItemStack>> breakCustomItemBlockWithoutDrops(Block block, Player player){
        return breakCustomItemBlockWithoutDrops(block, player, true);
    }

    /**
     * Break the given block, and return the drops instead of dropping them normally.
     * @param block - the block that should be broken
     * @param doBlockUpdate - whether or not this should trigger a block update
     * @param player - the player that broke the block
     * @return
     *      {@code null} if the block is not a custom block, and a two-element entry otherwise:
     *          The first entry is false if CustomItems cancelled the block break, and true if the block was broken.
     *          The second entry is a list of the items that the block would have normally dropped
     */
    public static AbstractMap.SimpleEntry<Boolean, List<ItemStack>> breakCustomItemBlockWithoutDrops(Block block, Player player, boolean doBlockUpdate){
        return breakCustomItemBlockWithoutDrops(block, player, doBlockUpdate, false);
    }

    /**
     * Break the given block, and return the drops instead of dropping them normally
     * @param block - the block that should be broken
     * @param player - the player that broke the block
     * @param doBlockUpdate - whether or not this should trigger a block update
     * @param override - false by default. Set this to true if you want to override CustomItem's decision to cancel the block break
     * @return
     *      {@code null} if the block is not a custom block, and a two-element entry otherwise:
     *          The first entry is false if CustomItems cancelled the block break, and true if the block was broken. This will always be true if you set override to true.
     *          The second entry is a list of the items that the block would have normally dropped. This will be empty if the first entry is false
     */
    public static AbstractMap.SimpleEntry<Boolean, List<ItemStack>> breakCustomItemBlockWithoutDrops(Block block, Player player, boolean doBlockUpdate, boolean override){
        CustomItem ci = CustomItemBlockHandler.get(block.getLocation());
        if(ci == null){return null;}
        GenericItem gi = new GenericItem(ci, 1);

        BlockBreakEvent e = new BlockBreakEvent(block, player);
        List<ItemStack> list = GenericItemActionHandler.handleBlockMineReturningDrops(e, gi);
        boolean cancelled = !override && e.isCancelled();

        if(cancelled){
            list = new ArrayList<>();
        }
        else{
            CustomItemBlockHandler.remove(block, doBlockUpdate);
        }

        return new AbstractMap.SimpleEntry<>(!cancelled, list);
    }


    /*
    *
    * CUSTOM ITEM CHECKING
    *
     */

    /**
     * @param stack - the ItemStack
     * @return true iff stack is a custom item
     */
    public static boolean isCustomItem(ItemStack stack){
        return CustomItemHandler.isCustomItem(stack);
    }

    /**
     * alias for isCustomItem(stack, id, true)
     * @param stack - the ItemStack to check
     * @param id - the Custom Item ID, case-insensitive
     * @return true iff stack is the Custom Item with the Custom Item ID id, case-insensitive
     */
    public static boolean isCustomItem(ItemStack stack, String id){
        return isCustomItem(stack, id, false);
    }

    /**
     *
     * @param stack - the ItemStack to check
     * @param id - the Custom Item ID. Case-sensitive if caseSensitive is true
     * @param caseSensitive - set to true to make sure the stack's ID matches id case-sensitively
     * @return true iff stack is the Custom Item with the Custom Item ID id.
     */
    public static boolean isCustomItem(ItemStack stack, String id, boolean caseSensitive){
        String itemID = getCustomItemID(stack);
        return itemID != null && (!caseSensitive ? itemID.equalsIgnoreCase(id) : itemID.equals(id));
    }

    /*
     *
     * DAMAGE
     *
     */

    /**
     * Get the tool damage on a CustomItem
     * @param stack the ItemStack to check
     * @return the damage on the item (0 is completely unused, getCustomItemMaxDamage(stack) is the max).
     * Returns {@code null} if the stack is not a CustomItem
     */
    public static Integer getCustomItemDamage(ItemStack stack){
        return CustomItemDamageHandler.tryGetDurability(stack);
    }

    /**
     * Get the maximum tool damage on a CustomItem
     * @param stack the ItemStack to check
     * @return the maximum possible damage on the ItemStack before it breaks. Returns {@code null} if the stack is not a CustomItem
     */
    public static Integer getCustomItemMaxDamage(ItemStack stack){
        if(!CustomItemHandler.isCustomItem(stack)){return null;}
        return CustomItemDamageHandler.getMaxDurability(stack);
    }

    /**
     * Set the tool damage on a CustomItem ItemStack
     * @param stack the ItemStack to set damage on
     * @param amount the damage to set the item to. 0 or lower is completely unused, and setting this to
     *               getCustomItemMaxDamage(stack) or anything higher will cause this method to return new ItemStack(Material.AIR)
     * @return {@code null} if the stack is not a CustomItem, {@code stack} if the item is
     * not damageable, new ItemStack(Material.AIR) if the item is broken, and the
     * newly damaged ItemStack otherwise.
     */
    public static ItemStack setCustomItemDamage(ItemStack stack, int amount){
        DoubleTuple<Boolean, ItemStack> dt = CustomItemDamageHandler.trySetDamage(stack, amount);
        if(!dt.val1()){return null;}
        ItemStack res = dt.val2();
        if(res == null){return SafeMaterial.AIR.parseItem();}
        return res;
    }

    /*
    *
    * GETTING CUSTOM ITEM INFO
    *
     */

    /**
     * Get the friendly name of the Custom Item with the ID id ("name" in the item YAML file)
     * @param id - the ID of the Custom Item to get, case-insensitive
     * @return The friendly name of the Custom Item with the ID id
     */
    public static String getCustomItemFriendlyName(String id){
        return getCustomItemFriendlyName(id, false);
    }

    /**
     * Get the friendly name of the Custom Item with the ID id ("name" in the item YAML file)
     * @param id - the ID of the Custom Item to get, Case-sensitive if caseSensitive is true
     * @param caseSensitive - set to true to make sure the stack's ID matches id case-sensitively
     * @return The friendly name of the Custom Item with the ID id
     */
    public static String getCustomItemFriendlyName(String id, boolean caseSensitive){
        CustomItem ci = CustomItem.get(id, !caseSensitive);
        return ci == null ? null : ci.getItemName();
    }

}

//    /**
//     *
//     * @param stack the ItemStack
//     * @return the custom item that stack represents, or null
//     */
//    public static CustomItemsItem getCustomItem(ItemStack stack){
//        CustomItem ci = CustomItemHandler.getCustomItem(stack);
//        return ci == null ? null : new CustomItemsItem(ci);
//    }
//
//    /**
//     *
//     * @param stack the ItemStack
//     * @param item the custom item
//     * @return true iff stack is the custom item item
//     */
//    public static boolean isSameCustomItem(ItemStack stack, CustomItemsItem item){
//        return item != null && item.exists() && item.equals(getCustomItem(stack));
//    }
//
//
//    /**
//     *
//     * @param id the unique ID, case-sensitive
//     * @return the custom item given by the unique ID id
//     */
//    public static CustomItemsItem getCustomItem(String id){return getCustomItem(id, false);}
//
//    /**
//     *
//     * @param id the unique ID, case-sensitivity depending on ignoreCase
//     * @param ignoreCase set to true to make the id case-insensitive, or false to make it case-sensitive. case-sensitive is slightly faster.
//     * @return the custom item given by the unique ID id
//     */
//    public static CustomItemsItem getCustomItem(String id, boolean ignoreCase){
//        return new CustomItemsItem(id, ignoreCase);
//    }
//
//    /**
//     * @param id the unique custom item ID or native Minecraft material name, case-sensitive
//     * @return a generic item that represents the item given by id
//     */
//    public static CUIGenericItem getCustomOrNativeItem(String id){return getCustomOrNativeItem(id, false);}
//
//    /**
//     *
//     * @param id the unique custom item ID or native Minecraft material name, case-sensitivity depending on ignoreCase
//     * @param ignoreCase set to true to make the id case-insensitive, or false to make it case-sensitive. case-sensitive is slightly faster.
//     * @return a generic item that represents the item given by id
//     */
//    public static CUIGenericItem getCustomOrNativeItem(String id, boolean ignoreCase){
//        return new CUIGenericItem(id, ignoreCase);
//    }
//
//    // BLOCKS
//
//    /**
//     *
//     * @param location the location of the block to check
//     * @return true if the block at location is a custom item
//     */
//    public static boolean isCustomItem(Location location){
//        return getCustomItem(location) != null;
//    }
//
//    /**
//     *
//     * @param location the location of the block to get
//     * @return the custom item that is placed at location, or null if it is not a custom item
//     */
//    public static CustomItemsItem getCustomItem(Location location){
//        CustomItem ci = CustomItemBlockHandler.get(location);
//        return ci == null ? null : new CustomItemsItem(ci);
//    }
//
//    /**
//     *
//     * @param item the custom item to place. Set to null to remove the block at location
//     * @param location the location of the block to set
//     * @return true iff item is invalid
//     */
//    public boolean placeCustomItem(CustomItemsItem item, Location location){
//        if(item != null && !item.exists()){return false;}
//        CustomItem ci = item == null ? null : item.getWrapped();
//        CustomItemBlockHandler.place(location.getBlock(), ci);
//
//        return true;
//    }
//
//    /***
//     * Removes the custom block at location
//     * @param location location of the block to remove
//     */
//    public void removeCustomItem(Location location){
//        placeCustomItem(null, location);
//    }
//
//    // INVENTORIES
//
//    /**
//     * Gives player 1 item
//     * @param player Player to give items to
//     * @param item the item to give the player
//     * @return true iff item is invalid
//     */
//    public boolean giveCustomItem(Player player, CustomItemsItem item){
//        return giveCustomItem(player, item, 1);
//    }
//
//    /**
//     * Gives player amount item
//     * @param player Player to give items to
//     * @param item the item to give the player
//     * @param amount the amount of the item to give to the player
//     * @return true iff item is invalid
//     */
//    public boolean giveCustomItem(Player player, CustomItemsItem item, int amount){
//        if(!item.exists()){
//            return false;
//        }
//        player.getInventory().addItem(item.getItemStack(amount));
//        return true;
//    }
//
//    /**
//     * @param inventory The inventory to check
//     * @param item The item to count
//     * @return the amount of times item appears in inventory
//     */
//    public int getAmountOfCustomItemsInInventory(Inventory inventory, CustomItemsItem item){
//        if(item == null || !item.exists()){return 0;}
//
//        int num = 0;
//        for(ItemStack is : inventory.getContents()){
//            if(isSameCustomItem(is, item)){
//                num+=is.getAmount();
//            }
//        }
//        return num;
//    }
//}

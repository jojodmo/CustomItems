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

/**
 * CustomItems API
 *
 * Most of these API functions will only work if the plugin is enabled.
 * Use the isEnabled() method to check if the plugin is enabled.
 */
public class CustomItemsAPI{

    /**
     * Most API functions will only work if the plugin is enabled (if this functions returns true)
     * @return true iff the plugin is enabled
     */
    public static boolean isEnabled(){
        return Main.that.isEnabled();
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


    /*
    *
    * CUSTOM ITEM VERIFICATION
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

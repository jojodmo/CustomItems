package com.jojodmo.customitems.api;

import com.jojodmo.customitems.item.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CUIItemMaker{

    private String id;
    private String name;
    private int version = 1;

    private Material material;
    private Integer textureID;
    private Integer blockTextureID;

    private String category;
    private boolean forceUpdate;

    private ItemStack itemStack;
    private boolean damageable;
    private int maxDurability;
    private int initialItemDamage;
    private boolean hideDurability;
    private boolean hideDurabilityBar;
    private boolean hideFlags;
    private boolean hideAttributes;
    private boolean hideEnchantments;



    private ItemStack placedType;
//    private CustomItemNBT nbt;
//    private CustomItemAttributes attributes;
//    private GenericItemRecipe[] recipes;
//
//    private CustomArmorAttributes armorAttributes;

    private boolean enchantable;
    private boolean throwable;
    private boolean canBeUsedGenerically;
    private boolean canBePlaced;
    private boolean isConsumable;
    private boolean isProjectable;

    private Boolean doBlockPhysics;
    private int blockLightLevel;

    private boolean itemGlows;

//    private List<GenericBlockGenerationProperty> generationProperties;
//    private Map<String, Integer> blockItemDropIDs = new HashMap<>();
//    private List<GenericItem> blockItemDrops = null;
//    private int blockEXPDrop = 0;
    private boolean calculateBlockFortuneDrops;
    private boolean doSilkTouchBlockDrops;

    //    private CustomItemInfo info;
    //    public ConfigurationSection infoConfigurationSection;
    //    private GenericItemHandleList handleList;
    //    public ConfigurationSection handleListConfigurationSection;
    //    private boolean valid = true;

    private CUIItem registered;

    public CUIItemMaker(String id, String name, ItemStack stack){
        this.name = name;
        this.id = id;
        this.itemStack = stack;
    }

    public CUIItemRegistrationStatus register(){
        if(registered != null){return CUIItemRegistrationStatus.ALREADY_REGISTERED;}

        CustomItem ci = new CustomItem(
                id, this
        );
        registered = new CUIItem(ci);

        return ci.isValid() ? CUIItemRegistrationStatus.SUCCESS : CUIItemRegistrationStatus.INVALID_ITEM;
    }

    public CUIItem getItem(){
        return this.registered;
    }
    
    
    
    

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getVersion(){
        return version;
    }

    public CUIItemMaker setName(String name){
        this.name = name; return this;
    }

    public CUIItemMaker setVersion(int version){
        this.version = version; return this;
    }

    public Material getMaterial(){
        return material;
    }

    public CUIItemMaker setMaterial(Material material){
        this.material = material; return this;
    }

    public Integer getTextureID(){
        return textureID;
    }

    public CUIItemMaker setTextureID(Integer textureID){
        this.textureID = textureID; return this;
    }

    public Integer getBlockTextureID(){
        return blockTextureID;
    }

    public CUIItemMaker setBlockTextureID(Integer blockTextureID){
        this.blockTextureID = blockTextureID; return this;
    }

    public String getCategory(){
        return category;
    }

    public CUIItemMaker setCategory(String category){
        this.category = category; return this;
    }

    public boolean getForceUpdate(){
        return forceUpdate;
    }

    public CUIItemMaker setForceUpdate(boolean forceUpdate){
        this.forceUpdate = forceUpdate; return this;
    }

    public ItemStack getItemStack(){
        return itemStack;
    }
    
    public boolean isDamageable(){
        return damageable;
    }

    public CUIItemMaker setDamageable(boolean damageable){
        this.damageable = damageable; return this;
    }

    public int getMaxDurability(){
        return maxDurability;
    }

    public CUIItemMaker setMaxDurability(int maxDurability){
        this.maxDurability = maxDurability; return this;
    }

    public int getInitialItemDamage(){
        return initialItemDamage;
    }

    public CUIItemMaker setInitialItemDamage(int initialItemDamage){
        this.initialItemDamage = initialItemDamage; return this;
    }

    public boolean getHideDurability(){
        return hideDurability;
    }

    public CUIItemMaker setHideDurability(boolean hideDurability){
        this.hideDurability = hideDurability; return this;
    }

    public boolean getHideDurabilityBar(){
        return hideDurabilityBar;
    }

    public CUIItemMaker setHideDurabilityBar(boolean hideDurabilityBar){
        this.hideDurabilityBar = hideDurabilityBar; return this;
    }

    public boolean getHideFlags(){
        return hideFlags;
    }

    public CUIItemMaker setHideFlags(boolean hideFlags){
        this.hideFlags = hideFlags; return this;
    }

    public boolean getHideAttributes(){
        return hideAttributes;
    }

    public CUIItemMaker setHideAttributes(boolean hideAttributes){
        this.hideAttributes = hideAttributes; return this;
    }

    public boolean getHideEnchantments(){
        return hideEnchantments;
    }

    public CUIItemMaker setHideEnchantments(boolean hideEnchantments){
        this.hideEnchantments = hideEnchantments; return this;
    }

    public ItemStack getPlacedType(){
        return placedType;
    }

    public CUIItemMaker setPlacedType(ItemStack placedType){
        this.placedType = placedType; return this;
    }

    public boolean isEnchantable(){
        return enchantable;
    }

    public CUIItemMaker setEnchantable(boolean enchantable){
        this.enchantable = enchantable; return this;
    }

    public boolean isThrowable(){
        return throwable;
    }

    public CUIItemMaker setThrowable(boolean throwable){
        this.throwable = throwable; return this;
    }

    public boolean getCanBeUsedGenerically(){
        return canBeUsedGenerically;
    }

    public CUIItemMaker setCanBeUsedGenerically(boolean canBeUsedGenerically){
        this.canBeUsedGenerically = canBeUsedGenerically; return this;
    }

    public boolean getCanBePlaced(){
        return canBePlaced;
    }

    public CUIItemMaker setCanBePlaced(boolean canBePlaced){
        this.canBePlaced = canBePlaced; return this;
    }

    public boolean isConsumable(){
        return isConsumable;
    }

    public CUIItemMaker setConsumable(boolean consumable){
        isConsumable = consumable; return this;
    }

    public boolean isProjectable(){
        return isProjectable;
    }

    public CUIItemMaker setProjectable(boolean projectable){
        isProjectable = projectable; return this;
    }

    public Boolean getDoBlockPhysics(){
        return doBlockPhysics;
    }

    public CUIItemMaker setDoBlockPhysics(Boolean doBlockPhysics){
        this.doBlockPhysics = doBlockPhysics; return this;
    }

    public int getBlockLightLevel(){
        return blockLightLevel;
    }

    public CUIItemMaker setBlockLightLevel(int blockLightLevel){
        this.blockLightLevel = blockLightLevel; return this;
    }

    public boolean getItemGlows(){
        return itemGlows;
    }

    public CUIItemMaker setItemGlows(boolean itemGlows){
        this.itemGlows = itemGlows; return this;
    }

    public boolean getCalculateBlockFortuneDrops(){
        return calculateBlockFortuneDrops;
    }

    public CUIItemMaker setCalculateBlockFortuneDrops(boolean calculateBlockFortuneDrops){
        this.calculateBlockFortuneDrops = calculateBlockFortuneDrops; return this;
    }

    public boolean getDoSilkTouchBlockDrops(){
        return doSilkTouchBlockDrops;
    }

    public CUIItemMaker setDoSilkTouchBlockDrops(boolean doSilkTouchBlockDrops){
        this.doSilkTouchBlockDrops = doSilkTouchBlockDrops; return this;
    }

    public CUIItem getRegistered(){
        return registered;
    }

    public CUIItemMaker setRegistered(CUIItem registered){
        this.registered = registered; return this;
    }
}

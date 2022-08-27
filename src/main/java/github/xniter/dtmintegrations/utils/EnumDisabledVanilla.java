package github.xniter.dtmintegrations.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum EnumDisabledVanilla implements IStringSerializable {

    // ARMOR
    IRON_CHESTPLATE,
    IRON_HELMET,
    IRON_BOOTS,
    IRON_LEGGINGS,
    LEATHER_CHESTPLATE,
    LEATHER_HELMET,
    LEATHER_BOOTS,
    LEATHER_LEGGINGS,
    GOLDEN_CHESTPLATE,
    GOLDEN_HELMET,
    GOLDEN_BOOTS,
    GOLDEN_LEGGINGS,
    DIAMOND_CHESTPLATE,
    DIAMOND_HELMET,
    DIAMOND_BOOTS,
    DIAMOND_LEGGINGS,

    // TOOLS
    WOODEN_AXE,
    WOODEN_PICKAXE,
    WOODEN_SHOVEL,
    WOODEN_HOE,
    STONE_AXE,
    STONE_PICKAXE,
    STONE_SHOVEL,
    STONE_HOE,
    IRON_AXE,
    IRON_PICKAXE,
    IRON_SHOVEL,
    IRON_HOE,
    GOLDEN_AXE,
    GOLDEN_PICKAXE,
    GOLDEN_SHOVEL,
    GOLDEN_HOE,
    DIAMOND_AXE,
    DIAMOND_PICKAXE,
    DIAMOND_SHOVEL,
    DIAMOND_HOE,

    // WEAPONS
    WOODEN_SWORD,
    STONE_SWORD,
    IRON_SWORD,
    GOLDEN_SWORD,
    DIAMOND_SWORD;


    private final Item item;

    EnumDisabledVanilla() {
        this.item = new Item();
    }

    public Item getItem() {
        return this.item;
    }

    public ItemStack getStack() {
        return  new ItemStack(this.item);
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
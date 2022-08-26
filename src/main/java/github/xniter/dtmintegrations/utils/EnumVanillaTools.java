package github.xniter.dtmintegrations.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum EnumVanillaTools implements IStringSerializable {

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
    DIAMOND_HOE;

    private final Item item;

    EnumVanillaTools() {
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

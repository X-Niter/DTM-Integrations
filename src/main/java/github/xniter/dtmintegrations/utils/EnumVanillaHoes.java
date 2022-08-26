package github.xniter.dtmintegrations.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum EnumVanillaHoes implements IStringSerializable {

    WOODEN_HOE,
    STONE_HOE,
    IRON_HOE,
    GOLDEN_HOE,
    DIAMOND_HOE;

    private final Item item;

    EnumVanillaHoes() {
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
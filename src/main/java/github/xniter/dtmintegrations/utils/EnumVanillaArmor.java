package github.xniter.dtmintegrations.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum EnumVanillaArmor implements IStringSerializable {

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
    DIAMOND_LEGGINGS;

    private final Item item;

    EnumVanillaArmor() {
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

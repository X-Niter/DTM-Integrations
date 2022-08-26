package github.xniter.dtmintegrations.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum EnumVanillaWeapons implements IStringSerializable {

    WOODEN_SWORD,
    STONE_SWORD,
    IRON_SWORD,
    GOLDEN_SWORD,
    DIAMOND_SWORD;

    private final Item item;

    EnumVanillaWeapons() {
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

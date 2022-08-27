package github.xniter.dtmintegrations.mixin.sevendaystomine.util;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.crafting.RecipeManager;
import nuparu.sevendaystomine.util.VanillaManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({VanillaManager.class})
public class MixinVanillaManager {

    /**
     * @author X_Niter
     * @reason Changing how we block/restrict vanilla stuff
     */
    @Overwrite(remap = false)
    public static void removeVanillaRecipes() {
        if (ModConfig.players.disableVanillaBlocksAndItems && !ConfigGetter.getDisableVanillaBlocksAndItemsUsage()) {
            RecipeManager.removeRecipe(Blocks.FURNACE);
            RecipeManager.removeRecipe(Blocks.PLANKS);
            RecipeManager.removeItem(Items.WOODEN_SWORD);
            RecipeManager.removeItem(Items.WOODEN_AXE);
            RecipeManager.removeItem(Items.WOODEN_PICKAXE);
            RecipeManager.removeItem(Items.WOODEN_SHOVEL);
            RecipeManager.removeItem(Items.WOODEN_HOE);
            RecipeManager.removeItem(Items.STONE_SWORD);
            RecipeManager.removeItem(Items.STONE_AXE);
            RecipeManager.removeItem(Items.STONE_PICKAXE);
            RecipeManager.removeItem(Items.STONE_SHOVEL);
            RecipeManager.removeItem(Items.STONE_HOE);
            RecipeManager.removeItem(Items.IRON_SWORD);
            RecipeManager.removeItem(Items.IRON_AXE);
            RecipeManager.removeItem(Items.IRON_PICKAXE);
            RecipeManager.removeItem(Items.IRON_SHOVEL);
            RecipeManager.removeItem(Items.IRON_HOE);
            RecipeManager.removeRecipe(Items.IRON_CHESTPLATE);
            RecipeManager.removeRecipe(Items.IRON_HELMET);
            RecipeManager.removeRecipe(Items.IRON_BOOTS);
            RecipeManager.removeRecipe(Items.IRON_LEGGINGS);
            RecipeManager.removeRecipe(Items.LEATHER_CHESTPLATE);
            RecipeManager.removeRecipe(Items.LEATHER_HELMET);
            RecipeManager.removeRecipe(Items.LEATHER_BOOTS);
            RecipeManager.removeRecipe(Items.LEATHER_LEGGINGS);
            RecipeManager.removeRecipe(Items.GOLDEN_SWORD);
            RecipeManager.removeRecipe(Items.GOLDEN_AXE);
            RecipeManager.removeRecipe(Items.GOLDEN_PICKAXE);
            RecipeManager.removeRecipe(Items.GOLDEN_SHOVEL);
            RecipeManager.removeRecipe(Items.GOLDEN_HOE);
            RecipeManager.removeRecipe(Items.DIAMOND_SWORD);
            RecipeManager.removeRecipe(Items.DIAMOND_AXE);
            RecipeManager.removeRecipe(Items.DIAMOND_PICKAXE);
            RecipeManager.removeRecipe(Items.DIAMOND_SHOVEL);
            RecipeManager.removeRecipe(Items.DIAMOND_HOE);
            RecipeManager.removeRecipe(Items.GOLDEN_CHESTPLATE);
            RecipeManager.removeRecipe(Items.GOLDEN_HELMET);
            RecipeManager.removeRecipe(Items.GOLDEN_BOOTS);
            RecipeManager.removeRecipe(Items.GOLDEN_LEGGINGS);
            RecipeManager.removeRecipe(Items.DIAMOND_CHESTPLATE);
            RecipeManager.removeRecipe(Items.DIAMOND_HELMET);
            RecipeManager.removeRecipe(Items.DIAMOND_BOOTS);
            RecipeManager.removeRecipe(Items.DIAMOND_LEGGINGS);
            if (ConfigGetter.getChangeTorches()) {
                RecipeManager.removeRecipe(Blocks.TORCH);
            }
            if (ModConfig.players.disableBlockToIngot) {
                RecipeManager.removeRecipe(Items.IRON_INGOT);
                RecipeManager.removeRecipe(Items.IRON_NUGGET);
                RecipeManager.removeRecipe(Items.GOLD_INGOT);
                RecipeManager.removeRecipe(Items.GOLD_INGOT);
            }

            RecipeManager.removeSmelting(new ItemStack(Items.GOLD_INGOT), "minecraft");
            RecipeManager.removeSmelting(new ItemStack(Items.IRON_INGOT), "minecraft");
        }

        if (ConfigGetter.getDisableVanillaBlocksAndItemsUsage()) {
            ModConfig.players.disableVanillaBlocksAndItems = false;
        }
    }
}

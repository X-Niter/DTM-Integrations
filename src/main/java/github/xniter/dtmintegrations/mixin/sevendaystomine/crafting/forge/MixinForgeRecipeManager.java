package github.xniter.dtmintegrations.mixin.sevendaystomine.crafting.forge;

import github.xniter.dtmintegrations.utils.modconstants.EnumMatWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import nuparu.sevendaystomine.crafting.forge.ForgeRecipeManager;
import nuparu.sevendaystomine.crafting.forge.ForgeRecipeMaterial;
import nuparu.sevendaystomine.crafting.forge.IForgeRecipe;
import nuparu.sevendaystomine.init.ModFluids;
import nuparu.sevendaystomine.init.ModItems;
import nuparu.sevendaystomine.item.EnumMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.HashMap;

@Mixin(value = ForgeRecipeManager.class, remap = false)
public class MixinForgeRecipeManager {

    @Shadow
    private static ForgeRecipeManager INSTANCE;

    @Shadow
    private ArrayList<IForgeRecipe> recipes;

    /**
     * @author X_Niter
     * @reason Forge Recipe Changes
     */
    @Overwrite(remap = false)
    public void addRecipes() {
        HashMap<EnumMaterial, Integer> ingotbronze2 = new HashMap();
        ingotbronze2.put(EnumMaterial.COPPER, 4);
        ingotbronze2.put(EnumMaterial.TIN, 2);
        this.addRecipe(new ForgeRecipeMaterial(new ItemStack(ModItems.INGOT_BRONZE), new ItemStack(ModItems.MOLD_INGOT), ingotbronze2));

        HashMap<EnumMaterial, Integer> ingotbrass2 = new HashMap();
        ingotbrass2.put(EnumMaterial.COPPER, 4);
        ingotbrass2.put(EnumMaterial.ZINC, 2);
        this.addRecipe(new ForgeRecipeMaterial(new ItemStack(ModItems.INGOT_BRASS), new ItemStack(ModItems.MOLD_INGOT), ingotbrass2));

        HashMap<EnumMaterial, Integer> ingotsteel2 = new HashMap();
        ingotsteel2.put(EnumMaterial.IRON, 4);
        ingotsteel2.put(EnumMaterial.CARBON, 2);
        this.addRecipe(new ForgeRecipeMaterial(new ItemStack(ModItems.INGOT_STEEL), new ItemStack(ModItems.MOLD_INGOT), ingotsteel2));

        HashMap<EnumMaterial, Integer> cement = new HashMap();
        cement.put(EnumMaterial.STONE, 4);
        cement.put(EnumMaterial.SAND, 1);
        this.addRecipe(new ForgeRecipeMaterial(new ItemStack(ModItems.CEMENT), new ItemStack(ModItems.CEMENT_MOLD), cement));

        HashMap<EnumMaterial, Integer> bulletCasing = new HashMap();
        bulletCasing.put(EnumMaterial.BRASS, 1);
        this.addRecipe(new ForgeRecipeMaterial(new ItemStack(ModItems.BULLET_CASING), new ItemStack(ModItems.BULLET_CASING_MOLD), bulletCasing));

        HashMap<EnumMaterial, Integer> bulletTip = new HashMap();
        bulletTip.put(EnumMaterial.LEAD, 1);
        this.addRecipe(new ForgeRecipeMaterial(new ItemStack(ModItems.BULLET_TIP), new ItemStack(ModItems.BULLET_TIP_MOLD), bulletTip));

        HashMap<EnumMaterial, Integer> glass = new HashMap();
        glass.put(EnumMaterial.SAND, 1);
        this.addRecipe(new ForgeRecipeMaterial(new ItemStack(ModItems.GLASS_SCRAP), new ItemStack(ModItems.MOLD_INGOT), glass));

        HashMap<EnumMaterial, Integer> ironPipes = new HashMap();
        ironPipes.put(EnumMaterial.IRON, 1);
        this.addRecipe(new ForgeRecipeMaterial(new ItemStack(ModItems.IRON_PIPE, 1), new ItemStack(ModItems.MOLD_INGOT), ironPipes));

        this.addMaterialRecipe(new ItemStack(ModItems.EMPTY_JAR), new ItemStack(ModItems.EMPTY_JAR_MOLD), new EnumMatWrapper(EnumMaterial.GLASS, 3));
        this.addMaterialRecipe(new ItemStack(ModItems.PISTOL_SLIDE), new ItemStack(ModItems.PISTOL_BARREL_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 2));
        this.addMaterialRecipe(new ItemStack(ModItems.PISTOL_TRIGGER), new ItemStack(ModItems.PISTOL_TRIGGER_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 4));
        this.addMaterialRecipe(new ItemStack(ModItems.SNIPER_RIFLE_TRIGGER), new ItemStack(ModItems.SNIPER_RIFLE_TRIGGER_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 6));
        this.addMaterialRecipe(new ItemStack(ModItems.SNIPER_RIFLE_STOCK), new ItemStack(ModItems.SNIPER_RIFLE_STOCK_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 6));
        this.addMaterialRecipe(new ItemStack(ModItems.SHOTGUN_RECEIVER), new ItemStack(ModItems.SHOTGUN_RECEIVER_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 4));
        this.addMaterialRecipe(new ItemStack(ModItems.SHOTGUN_BARREL), new ItemStack(ModItems.SHOTGUN_BARREL_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 3));
        this.addMaterialRecipe(new ItemStack(ModItems.SHOTGUN_BARREL_SHORT), new ItemStack(ModItems.SHOTGUN_SHORT_BARREL_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 2));
        this.addMaterialRecipe(new ItemStack(ModItems.MP5_BARREL), new ItemStack(ModItems.MP5_BARREL_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 4));
        this.addMaterialRecipe(new ItemStack(ModItems.MP5_TRIGGER), new ItemStack(ModItems.MP5_TRIGGER_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 6));
        this.addMaterialRecipe(new ItemStack(ModItems.MP5_STOCK), new ItemStack(ModItems.MP5_STOCK_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 4));
        this.addMaterialRecipe(new ItemStack(ModItems.HUNTING_RIFLE_BARREL), new ItemStack(ModItems.HUNTING_RIFLE_BARREL_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 3));
        this.addMaterialRecipe(new ItemStack(ModItems.HUNTING_RIFLE_BOLT), new ItemStack(ModItems.HUNTING_RIFLE_BOLT_MOLD), new EnumMatWrapper(EnumMaterial.IRON, 4));
        this.addMaterialRecipe(FluidUtil.getFilledBucket(new FluidStack(ModFluids.MERCURY, 1000)), new ItemStack(Items.BUCKET), new EnumMatWrapper(EnumMaterial.MERCURY, 48));
    }

    @Shadow
    public void addRecipe(IForgeRecipe recipe) {
        this.recipes.add(recipe);
    }


    public void addMaterialRecipe(ItemStack result, ItemStack mold, EnumMatWrapper... mats) {
        HashMap<EnumMaterial, Integer> ingredients = new HashMap<>();
        int var6 = mats.length;

        for (EnumMatWrapper mat : mats) {
            ingredients.put(mat.mat, mat.weight);
        }

        this.addRecipe(new ForgeRecipeMaterial(result, mold, ingredients));
    }
}

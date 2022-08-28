package github.xniter.dtmintegrations.mixin.sevendaystomine.item;

import github.xniter.dtmintegrations.handlers.ModGetter;
import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nuparu.sevendaystomine.capability.CapabilityHelper;
import nuparu.sevendaystomine.capability.IExtendedPlayer;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.item.ItemDrink;
import nuparu.sevendaystomine.potions.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import toughasnails.api.stat.capability.IThirst;
import toughasnails.api.thirst.IDrink;
import toughasnails.api.thirst.ThirstHelper;

@Mixin(value = ItemDrink.class, remap = false)
public class MixinItemDrink extends ItemFood {

    @Shadow
    private int thirst;

    @Shadow
    private int stamina;

    public MixinItemDrink(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
    }

    public MixinItemDrink(int amount, boolean isWolfFood) {
        super(amount, isWolfFood);
    }

    /**
     * @author X_Niter
     * @reason TAN Compatibility
     */
    @Overwrite(remap = false)
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        IThirst thirst = ThirstHelper.getThirstData(player);
        if (!worldIn.isRemote) {
            IExtendedPlayer extendedPlayer = CapabilityHelper.getExtendedPlayer(player);

            if (ConfigGetter.getTanIntegration()) {
                if (ModGetter.isTANLoaded()) {
                    thirst.addStats(this.thirst, this.stamina);
                }
            } else {
                if (ModConfig.players.thirstSystem) {
                    extendedPlayer.addThirst(this.thirst);
                }
                if (ModConfig.players.staminaSystem) {
                    extendedPlayer.addThirst(this.thirst);
                    extendedPlayer.addStamina(this.stamina);
                }
            }

            player.removePotionEffect(Potions.thirst);
            if (this.getContainerItem() != null) {
                ItemStack itemStack = new ItemStack(this.getContainerItem());
                if (!player.addItemStackToInventory(itemStack)) {
                    player.dropItem(itemStack, false);
                }
            }

        }
    }
}

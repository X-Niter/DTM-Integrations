package github.xniter.dtmintegrations.mixin.sevendaystomine.item;

import github.xniter.dtmintegrations.handlers.ModGetter;
import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import github.xniter.dtmintegrations.items.TANItemDrink;
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

@Mixin(value = ItemDrink.class, remap = false)
public abstract class MixinItemDrink extends ItemFood {

    @Shadow
    private int thirst;

    @Shadow
    private int stamina;

    public MixinItemDrink(int amount, int thirst, int stamina) {
        super(amount, false);
        this.setAlwaysEdible();
        this.thirst = thirst;
        this.stamina = stamina;
    }

    public MixinItemDrink(int amount, float saturation, int thirst, int stamina) {
        super(amount, saturation, false);
        this.setAlwaysEdible();
        this.thirst = thirst;
        this.stamina = stamina;
    }

    /**
     * @author X_Niter
     * @reason Integrations with Tough As Nails
     */
    @Overwrite(remap = false)
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);


        if (!worldIn.isRemote) {
            IExtendedPlayer extendedPlayer = CapabilityHelper.getExtendedPlayer(player);

            if (ConfigGetter.getTanIntegration()) {
                if (ModGetter.isTANLoaded()) {
                    new TANItemDrink(this.thirst, (float) this.stamina);
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

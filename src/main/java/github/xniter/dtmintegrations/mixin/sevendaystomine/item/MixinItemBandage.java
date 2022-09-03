package github.xniter.dtmintegrations.mixin.sevendaystomine.item;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import nuparu.sevendaystomine.item.ItemBandage;
import nuparu.sevendaystomine.potions.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ItemBandage.class)
public class MixinItemBandage {

    /**
     * @author X_Niter
     * @reason Injecting the return is being dumb, so for now, sense I don't expect anyone else to touch this,
     * I am overwriting this to allow changing the Bandage usage time.
     */
    @Overwrite
    public int getMaxItemUseDuration(ItemStack itemStack) {
        return ConfigGetter.getBandageUseTime() * 20;
    }

    /**
     * @author X_Niter
     * @reason Configuration option to allow general bandages to give healing
     */
    @Overwrite
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            int dur = this.getMaxItemUseDuration(stack) - timeLeft;
            if ((float)dur <= (float)this.getMaxItemUseDuration(stack) * 0.1F) {
                if (!entityplayer.capabilities.isCreativeMode) {
                    stack.shrink(1);
                    if (stack.isEmpty()) {
                        entityplayer.inventory.deleteStack(stack);
                    }
                }

                entityplayer.removePotionEffect(Potions.bleeding);
                if (ConfigGetter.getShouldRegularBandagesHeal()) {
                    entityplayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, ConfigGetter.getRegularBandageHealthDuration(), ConfigGetter.getRegularBandageHealthAmplifier()));
                }
            }
        }

    }

}

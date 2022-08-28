package github.xniter.dtmintegrations.mixin.sevendaystomine.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import nuparu.sevendaystomine.item.ItemAdvancedBandage;
import nuparu.sevendaystomine.item.ItemBandage;
import nuparu.sevendaystomine.potions.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ItemAdvancedBandage.class, remap = false)
public class MixinItemAdvancedBandage extends ItemBandage {

    /**
     * @author X_Niter
     * @reason Adding more healing capability to the Advanced Bandage
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
                entityplayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 3));
            }
        }

    }
}

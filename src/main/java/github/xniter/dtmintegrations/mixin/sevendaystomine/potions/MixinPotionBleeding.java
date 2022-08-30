package github.xniter.dtmintegrations.mixin.sevendaystomine.potions;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.EnumDifficulty;
import nuparu.sevendaystomine.SevenDaysToMine;
import nuparu.sevendaystomine.potions.PotionBleeding;
import nuparu.sevendaystomine.util.DamageSources;
import nuparu.sevendaystomine.util.EnumModParticleType;
import nuparu.sevendaystomine.util.MathUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = PotionBleeding.class, remap = false)
public class MixinPotionBleeding {

    /**
     * @author X_Niter
     * @reason Configurable bleeding damage amount & chance that it will do damage.
     */
    @Overwrite(remap = false)
    public void performEffect(EntityLivingBase entity, int p_76394_2_) {
        if (entity.world.rand.nextInt(ConfigGetter.getBleedingDamageChance()) == 0) {
            entity.attackEntityFrom(DamageSources.bleeding, (float) ConfigGetter.getBleedingAffectDamageAmount());
        }

        if (entity.world.isRemote && entity.world.getDifficulty() != EnumDifficulty.PEACEFUL && entity.world.rand.nextInt(5) == 0) {
            for(int i = 0; i < (int)Math.round(MathUtils.getDoubleInRange(1.0, 5.0) * (double) SevenDaysToMine.proxy.getParticleLevel()); ++i) {
                double x = entity.posX + MathUtils.getDoubleInRange(-1.0, 1.0) * (double)entity.width;
                double y = entity.posY + MathUtils.getDoubleInRange(0.0, 1.0) * (double)entity.height;
                double z = entity.posZ + MathUtils.getDoubleInRange(-1.0, 1.0) * (double)entity.width;
                SevenDaysToMine.proxy.spawnParticle(entity.world, EnumModParticleType.BLOOD, x, y, z, MathUtils.getDoubleInRange(-1.0, 1.0) / 7.0, MathUtils.getDoubleInRange(-0.5, 1.0) / 7.0, MathUtils.getDoubleInRange(-1.0, 1.0) / 7.0);
            }
        }

    }
}

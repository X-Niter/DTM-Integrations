package github.xniter.sdtmintegrations.mixin;

import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.util.MathUtils;
import nuparu.sevendaystomine.world.gen.city.City;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import scala.tools.asm.Opcodes;
import zone.rong.mixinbooter.ILateMixinLoader;
import zone.rong.mixinbooter.MixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin({City.class})
@SuppressWarnings("Dangling Javadoc comment")
public class MixinCity implements ILateMixinLoader {

    /**
     *
     * @author X_Niter
     * @reason Removed the minimum of the maximum of 8 and instead allows min of 1 max of what is set in config by admin/user/client
     */
    @Redirect(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnuparu/sevendaystomine/world/gen/city/EnumCityType;Ljava/util/Random;)V", at = @At(value = "FIELD", target = "Lnuparu/sevendaystomine/world/gen/city/City;roadsLimit:I", opcode = Opcodes.PUTFIELD), remap = false)
    private void roadsLimit(nuparu.sevendaystomine.world.gen.city.City instance, int value)
    {
        Random rand = new Random();
        instance.roadsLimit = MathUtils.getIntInRange(rand, 1, ModConfig.worldGen.maxCitySize);
    }

    @Override
    public List<String> getMixinConfigs()
    {
        return Collections.singletonList("mixins.dtmintegrations.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        return ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
    }

    @Override
    public void onMixinConfigQueued(String mixinConfig)
    {
        ILateMixinLoader.super.onMixinConfigQueued(mixinConfig);
    }
}

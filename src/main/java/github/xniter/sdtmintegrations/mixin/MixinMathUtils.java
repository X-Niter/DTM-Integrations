package github.xniter.sdtmintegrations.mixin;

import nuparu.sevendaystomine.util.MathUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin({MathUtils.class})
public class MixinMathUtils implements ILateMixinLoader {

    /**
     * @author X_Niter
     * @reason Fixes city generation crash when using other mods like lost cities.
     */
    @Redirect(method = "getIntInRange(Ljava/util/Random;II)I", at = @At(value = "RETURN"), remap = false)
    protected static void DTMIntegrations_getIntInRange(Random rand, int min, int max, CallbackInfoReturnable<Integer> cir)
    {
        if (min + rand.nextInt(max - min) <= 0)
        {
            cir.setReturnValue(1);
        }
    }

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.dtmintegrations.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
    }

    @Override
    public void onMixinConfigQueued(String mixinConfig) {
        ILateMixinLoader.super.onMixinConfigQueued(mixinConfig);
    }
}

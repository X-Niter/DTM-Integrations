package github.xniter.dtmintegrations.mixin;

import nuparu.sevendaystomine.util.MathUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin({MathUtils.class})
public class MixinMathUtils implements ILateMixinLoader {

    @Shadow
    private static Random r = new Random();

    /**
     * @author X_Niter
     * @reason Math
     */
    @Overwrite(remap = false)
    public static int getIntInRange(Random rand, int min, int max) {
        int bound = max - min;
        if (bound == 0){
            bound = max - min + 1;
        }
        return min + rand.nextInt(bound);
    }

    /**
     * @author X_Niter
     * @reason Math
     */
    @Overwrite(remap = false)
    public static float getFloatInRange(float min, float max) {
        float bound = max - min;
        if (bound == 0){
            bound = max - min + 1;
        }

        return min + r.nextFloat() * (bound);
    }

    /**
     * @author X_Niter
     * @reason Math
     */
    @Overwrite(remap = false)
    public static double getDoubleInRange(double min, double max) {
        double bound = max - min;
        if (bound == 0){
            bound = max - min + 1;
        }
        return min + r.nextDouble() * (bound);
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

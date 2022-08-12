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
    @Overwrite
    public static int getIntInRange(Random rand, int min, int max) {
        int bound = max - min;
        if (bound == 0){
            bound = max - min + 16 / 4 + max - min;
        }
        return min + rand.nextInt(bound) >= 1 ? min + rand.nextInt(bound) : 4;
    }

    /**
     * @author X_Niter
     * @reason Math
     */
    @Overwrite
    public static float getFloatInRange(float min, float max) {

        return min + r.nextFloat() * (max - min) >= 1 ? min + r.nextFloat() * (max - min) : min + r.nextFloat();
    }

    /**
     * @author X_Niter
     * @reason Math
     */
    @Overwrite
    public static double getDoubleInRange(double min, double max) {
        return min + r.nextDouble() * (max - min) >= 1 ? min + r.nextDouble() * (max - min) : min + r.nextDouble();
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

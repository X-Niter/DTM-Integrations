package github.xniter.dtmintegrations.mixin.sevendaystomine.util;

import nuparu.sevendaystomine.util.MathUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin({MathUtils.class})
public class MixinMathUtils {

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
}

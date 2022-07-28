package github.xniter.sdtmintegrations.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(nuparu.sevendaystomine.util.MathUtils.class)
public class MathUtils {

    /**
     * @author X_Niter
     * @reason Fixing a Math issue that caused random range to go to negative bounds, this fixes it by forcing a bound no less than 1.
     */
    @Overwrite
    public static int getIntInRange(Random rand, int min, int max) {
        if (min + rand.nextInt(max - min) <= 0)
        {
            return min;
        }
        else
        {
            return min + rand.nextInt(max - min);
        }
    }
}

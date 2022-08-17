package github.xniter.dtmintegrations.mixin;

import github.xniter.dtmintegrations.config.DTMIConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.util.MathUtils;
import nuparu.sevendaystomine.world.gen.city.City;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.*;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.*;

@Mixin({City.class})
public class MixinCity implements ILateMixinLoader {



//    @Redirect(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnuparu/sevendaystomine/world/gen/city/EnumCityType;Ljava/util/Random;)V", at = @At(value = "INVOKE", target = "Lnuparu/sevendaystomine/util/MathUtils;getIntInRange(Ljava/util/Random;II)I"))
//    public int roadsLimit(Random rand, int min, int max) {
//        return MathUtils.getIntInRange(rand, 8, Math.max(9, ModConfig.worldGen.maxCitySize));
//    }
//
//
//    /**
//     * @author X_Niter
//     * @reason Gen Fix
//     */
//    @Overwrite(remap = false)
//    public static City foundCity(World world, ChunkPos pos, Random rand) {
//        return City.foundCity(world, new BlockPos(pos.x * 16 + 8, 0, pos.z * 16 + 8), rand);
//    }


    @ModifyConstant(method = "prepareStreets", constant = @Constant(intValue = 8192), remap = false)
    public int prepareStreets(int constant) {

        int genConfig = DTMIConfig.dtmGeneralConfig.streetGenAttempts;

        if (genConfig < 1) {
            genConfig = 1;
        }
        if (genConfig > 100) {
            genConfig = 100;
        }

        return genConfig;
    }

    @ModifyConstant(method = "addIteration", constant = @Constant(intValue = 16), remap = false)
    public int addIteration(int constant) {

        int genConfig = DTMIConfig.dtmGeneralConfig.streetGenAttempts;

        if (genConfig < 1) {
            genConfig = 1;
        }
        if (genConfig > 100) {
            genConfig = 100;
        }

        return genConfig;
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

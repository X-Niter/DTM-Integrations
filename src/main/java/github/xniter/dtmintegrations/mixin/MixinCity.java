package github.xniter.dtmintegrations.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import nuparu.sevendaystomine.world.gen.city.City;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin({City.class})
public class MixinCity implements ILateMixinLoader {



    @Redirect(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnuparu/sevendaystomine/world/gen/city/EnumCityType;Ljava/util/Random;)V", at = @At(value = "INVOKE", target = "Ljava/lang/Math;round(F)I"))
    public int roadsLimit(float v) {
        return v >= 1 ? (int)v : 4;
    }


    /**
     * @author X_Niter
     * @reason Gen Fix
     */
    @Overwrite(remap = false)
    public static City foundCity(World world, ChunkPos pos, Random rand) {
        return City.foundCity(world, new BlockPos(pos.x << 4 + 8, 0, pos.z << 4 + 8), rand);
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

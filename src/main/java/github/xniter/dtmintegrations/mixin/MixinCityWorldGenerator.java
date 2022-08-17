package github.xniter.dtmintegrations.mixin;

import github.xniter.dtmintegrations.config.DTMIConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import nuparu.sevendaystomine.world.gen.CityWorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin({CityWorldGenerator.class})
public class MixinCityWorldGenerator implements ILateMixinLoader {


    // TODO: Make all Generating use the Generating config!!!

    /**
     * @author X_Niter
     * @reason Config control for what world to allow generating in.
     */
    @Overwrite(remap = false)
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int worldProviders = world.provider.getDimension();

        DTMIConfig.getAllowedDimsForGen().forEach(dimToGen -> {
            if (worldProviders == dimToGen) {
                this.generateOverworld(world, random, chunkX, chunkZ);
            }
        });
    }

    /**
     * @author X_Niter
     * @reason Remove world type filter as modpack developers may allow a flat type world dimension for generation.
     */
    @Overwrite(remap = false)
    private void generateOverworld(World world, Random rand, int chunkX, int chunkZ) {
        if (chunkX % 64 == 0 && chunkZ % 64 == 0) {
            int blockX = chunkX * 16;
            int blockZ = chunkZ * 16;
            BlockPos pos = new BlockPos(blockX, 64, blockZ);
            world.getBiomeForCoordsBody(pos);
            world.getChunk(pos);
        }
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

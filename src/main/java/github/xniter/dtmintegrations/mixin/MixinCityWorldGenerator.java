package github.xniter.dtmintegrations.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import nuparu.sevendaystomine.world.gen.CityWorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin({CityWorldGenerator.class})
public class MixinCityWorldGenerator implements ILateMixinLoader, IWorldGenerator {



    /**
     * @author X_Niter
     * @reason f
     */
    @Overwrite(remap = false)
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int blockX = chunkX * 16;
        int blockZ = chunkZ * 16;
        if (world.provider.getDimension() == 0) {
            this.generateOverworld(world, random, blockX, blockZ);
        }

    }

    /**
     * @author X_Niter
     * @reason f
     */
    @Overwrite(remap = false)
    private void generateOverworld(World world, Random rand, int chunkX, int chunkZ) {
        if (world.getWorldType() != WorldType.FLAT) {
            if (chunkX % 16 == 0 && chunkZ % 16 == 0) {
                int blockX = chunkX * 16;
                int blockZ = chunkZ * 16;
                BlockPos pos = new BlockPos(blockX, 64, blockZ);
                world.getBiomeForCoordsBody(pos);
                world.getChunk(pos);
            }
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

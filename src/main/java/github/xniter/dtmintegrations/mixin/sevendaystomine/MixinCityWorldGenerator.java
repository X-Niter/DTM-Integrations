package github.xniter.dtmintegrations.mixin.sevendaystomine;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import nuparu.sevendaystomine.world.gen.CityWorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin({CityWorldGenerator.class})
public class MixinCityWorldGenerator {


    // TODO: Make all Generating use the Generating config!!!

    /**
     * @author X_Niter
     * @reason Config control for what world to allow generating in.
     */
    @Overwrite(remap = false)
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int worldProviders = world.provider.getDimension();

        ConfigGetter.getAllowedDimGen().forEach(dimToGen -> {
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
}

package github.xniter.dtmintegrations.mixin.sevendaystomine.world.gen;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.util.MathUtils;
import nuparu.sevendaystomine.world.gen.CornWorldGenerator;
import nuparu.sevendaystomine.world.gen.feature.WorldGenCorn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(value = CornWorldGenerator.class, remap = false)
public class MixinCornWorldGenerator {

    @Shadow
    WorldGenerator generator = new WorldGenCorn();

    /**
     * @author X_Niter
     * @reason Config control for what world to allow generating in.
     */
    @Overwrite(remap = false)
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int blockX = chunkX * 16;
        int blockZ = chunkZ * 16;
        int worldProviders = world.provider.getDimension();

        ConfigGetter.getAllowedDimGen().forEach(dimToGen -> {
            if (worldProviders == dimToGen) {
                this.generateOverworld(world, random, blockX, blockZ);
            }
        });
    }

    /**
     * @author X_Niter
     * @reason Remove world type filter as modpack developers may allow a flat type world dimension for generation.
     */
    @Overwrite(remap = false)
    private void generateOverworld(World world, Random rand, int blockX, int blockZ) {
        int num = MathUtils.getIntInRange(rand, ModConfig.worldGen.cornGenerationRateMin, ModConfig.worldGen.cornGenerationRateMax + 1);

        for(int i = 0; i < num; ++i) {
            int randX = blockX + rand.nextInt(16);
            int randZ = blockZ + rand.nextInt(16);
            this.generator.generate(world, rand, new BlockPos(randX, 24, randZ));
        }
    }
}

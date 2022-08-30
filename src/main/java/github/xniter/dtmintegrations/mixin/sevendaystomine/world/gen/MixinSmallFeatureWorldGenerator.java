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
import nuparu.sevendaystomine.world.gen.SmallFeatureWorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(value = SmallFeatureWorldGenerator.class, remap = false)
public class MixinSmallFeatureWorldGenerator {
    @Shadow
    WorldGenerator smallRock;

    @Shadow
    WorldGenerator rock;

    @Shadow
    WorldGenerator stick;

    @Shadow
    WorldGenerator bush;

    @Shadow
    WorldGenerator cinder;

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
     * @reason Config generation control.
     */
    @Overwrite(remap = false)
    private void generateOverworld(World world, Random rand, int blockX, int blockZ) {
        if (world.getWorldType() != WorldType.FLAT) {
            int i;
            int randX;
            int randZ;
            for(i = 0; i < MathUtils.getIntInRange(rand, ModConfig.worldGen.smallRockGenerationRateMin, ModConfig.worldGen.smallRockGenerationRateMax + 1); ++i) {
                randX = blockX + rand.nextInt(16);
                randZ = blockZ + rand.nextInt(16);
                this.smallRock.generate(world, rand, new BlockPos(randX, 24, randZ));
            }

            for(i = 0; i < MathUtils.getIntInRange(rand, ModConfig.worldGen.largeRockGenerationRateMin, ModConfig.worldGen.largeRockGenerationRateMax + 1); ++i) {
                randX = blockX + rand.nextInt(16);
                randZ = blockZ + rand.nextInt(16);
                this.rock.generate(world, rand, new BlockPos(randX, 24, randZ));
            }

            for(i = 0; i < MathUtils.getIntInRange(rand, ModConfig.worldGen.stickGenerationRateMin, ModConfig.worldGen.stickGenerationRateMax + 1); ++i) {
                randX = blockX + rand.nextInt(16);
                randZ = blockZ + rand.nextInt(16);
                this.stick.generate(world, rand, new BlockPos(randX, 24, randZ));
            }

            for(i = 0; i < MathUtils.getIntInRange(rand, ModConfig.worldGen.deadBushGenerationRateMin, ModConfig.worldGen.deadBushGenerationRateMax + 1); ++i) {
                randX = blockX + rand.nextInt(16);
                randZ = blockZ + rand.nextInt(16);
                this.bush.generate(world, rand, new BlockPos(randX, 24, randZ));
            }

            for(i = 0; i < MathUtils.getIntInRange(rand, ModConfig.worldGen.cinderBlockGenerationRateMin, ModConfig.worldGen.cinderBlockGenerationRateMax + 1); ++i) {
                randX = blockX + rand.nextInt(16);
                randZ = blockZ + rand.nextInt(16);
                this.cinder.generate(world, rand, new BlockPos(randX, 24, randZ));
            }

        }
    }
}

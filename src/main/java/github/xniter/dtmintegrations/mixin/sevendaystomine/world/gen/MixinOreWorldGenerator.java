package github.xniter.dtmintegrations.mixin.sevendaystomine.world.gen;


import com.google.common.base.Predicate;
import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.init.ModBlocks;
import nuparu.sevendaystomine.world.gen.OreWorldGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(value = OreWorldGenerator.class, remap = false)
public class MixinOreWorldGenerator {

    @Final
    @Shadow
    private final Predicate<IBlockState> stonePredicate = new Predicate<IBlockState>() {
        public boolean apply(IBlockState state) {
            return state.getBlock() == Blocks.STONE && state.getValue(BlockStone.VARIANT) != BlockStone.EnumType.ANDESITE_SMOOTH && state.getValue(BlockStone.VARIANT) != BlockStone.EnumType.DIORITE_SMOOTH && state.getValue(BlockStone.VARIANT) != BlockStone.EnumType.GRANITE_SMOOTH;
        }
    };

    /**
     * @author X_Niter
     * @reason Config control for what world to allow generating in.
     */
    @Overwrite(remap = false)
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int worldProviders = world.provider.getDimension();

        ConfigGetter.getAllowedDimGen().forEach(dimToGen -> {
            if (worldProviders == dimToGen) {
                this.generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
            }
        });

    }

    // TODO ORE GEN CONFIG
    /**
     * @author X_Niter
     * @reason Config generation control.
     */
    @Overwrite(remap = false)
    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        this.addOreSpawn(ModBlocks.ORE_COPPER.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 16, 12, ModConfig.worldGen.copperOreGenerationRate, 10, 138, this.stonePredicate);
        this.addOreSpawn(ModBlocks.ORE_TIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 16, 8, ModConfig.worldGen.tinOreGenerationRate, 6, 96, this.stonePredicate);
        this.addOreSpawn(ModBlocks.ORE_ZINC.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 16, 8, ModConfig.worldGen.zincOreGenerationRate, 0, 90, this.stonePredicate);
        this.addOreSpawn(ModBlocks.ORE_LEAD.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 16, 5, ModConfig.worldGen.leadOreGenerationRate, 0, 76, this.stonePredicate);
        this.addOreSpawn(ModBlocks.ORE_POTASSIUM.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 16, 8, ModConfig.worldGen.potassiumOreGenerationRate, 32, 128, this.stonePredicate);
        this.addOreSpawn(ModBlocks.ORE_CINNABAR.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 16, 4, ModConfig.worldGen.cinnabarOreGenerationRate, 24, 96, this.stonePredicate);
    }

    @Shadow
    private void addOreSpawn(IBlockState block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chance, int minY, int maxY, Predicate<IBlockState> blockToSpawnIn) {
        int diffMinMaxY = maxY - minY;

        for(int x = 0; x < chance; ++x) {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(diffMinMaxY);
            int posZ = blockZPos + random.nextInt(maxZ);
            (new WorldGenMinable(block, maxVeinSize, blockToSpawnIn)).generate(world, random, new BlockPos(posX, posY, posZ));
        }

    }
}

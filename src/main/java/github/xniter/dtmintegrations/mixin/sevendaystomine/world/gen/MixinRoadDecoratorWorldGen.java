package github.xniter.dtmintegrations.mixin.sevendaystomine.world.gen;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.items.IItemHandler;
import nuparu.sevendaystomine.block.BlockGarbage;
import nuparu.sevendaystomine.block.BlockPaper;
import nuparu.sevendaystomine.block.BlockSandLayer;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.entity.EntityBandit;
import nuparu.sevendaystomine.init.ModBlocks;
import nuparu.sevendaystomine.init.ModLootTables;
import nuparu.sevendaystomine.tileentity.TileEntityGarbage;
import nuparu.sevendaystomine.util.ItemUtils;
import nuparu.sevendaystomine.util.SimplexNoise;
import nuparu.sevendaystomine.world.gen.RoadDecoratorWorldGen;
import nuparu.sevendaystomine.world.gen.city.CityHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(value = RoadDecoratorWorldGen.class, remap = false)
public class MixinRoadDecoratorWorldGen {

    @Shadow
    private static SimplexNoise noise;

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
     * @reason Config generation control.
     */
    @Overwrite(remap = false)
    private void generateOverworld(World world, Random rand, int chunkX, int chunkZ) {
        if (ModConfig.worldGen.generateRoads) {
            Chunk chunk = world.getChunk(chunkX, chunkZ);
            ChunkPos chunkPos = chunk.getPos();
            if (noise == null || noise.seed != world.getSeed()) {
                noise = new SimplexNoise(world.getSeed());
            }

            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 16; ++j) {
                    double value = Math.abs(getNoiseValue((chunkPos.x << 4) + i, (chunkPos.z << 4) + j, 0));
                    if (value < 0.005) {
                        for (BlockPos pos = chunkPos.getBlock(i, 255, j); pos.getY() >= 63; pos = pos.down()) {
                            IBlockState state = world.getBlockState(pos);
                            Block block = state.getBlock();
                            if (block == ModBlocks.ASPHALT) {
                                Biome biome = world.getBiome(pos);
                                if (rand.nextInt(400) == 0) {
                                    world.setBlockState(pos.up(), ModBlocks.GARBAGE.getDefaultState().withProperty(BlockGarbage.FACING, EnumFacing.HORIZONTALS[rand.nextInt(4)]));
                                    TileEntity te = world.getTileEntity(pos.up());
                                    if (te instanceof TileEntityGarbage) {
                                        TileEntityGarbage tg = (TileEntityGarbage) te;
                                        ItemUtils.fillWithLoot((IItemHandler) tg.getInventory(), ModLootTables.TRASH, world, rand);
                                    }
                                    break;
                                }

                                if (rand.nextInt(300) == 0) {
                                    CityHelper.placeRandomCar(world, pos.up(), EnumFacing.HORIZONTALS[rand.nextInt(4)], true, rand);
                                    break;
                                }

                                if (ModConfig.worldGen.sandRoadCover && rand.nextInt(2) == 0 && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY) && biome.topBlock.getBlock() == Blocks.SAND) {
                                    IBlockState sand = ModBlocks.SAND_LAYER.getDefaultState();
                                    if (biome.topBlock.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) {
                                        sand = ModBlocks.RED_SAND_LAYER.getDefaultState();
                                    }

                                    world.setBlockState(pos.up(), sand.withProperty(BlockSandLayer.LAYERS, 1 + rand.nextInt(2)));
                                } else if (rand.nextInt(80) == 0) {
                                    world.setBlockState(pos.up(), ModBlocks.PAPER.getDefaultState().withProperty(BlockPaper.FACING, EnumFacing.byHorizontalIndex(rand.nextInt(4))));
                                } else if (rand.nextInt(2048) == 0) {
                                    int count = 2 + world.rand.nextInt(6);

                                    while (count-- > 0) {
                                        EntityBandit bandit = new EntityBandit(world);
                                        bandit.setPositionAndRotation((double) pos.getX(), (double) (pos.getY() + 2), (double) pos.getZ(), world.rand.nextFloat(), world.rand.nextFloat());
                                        if (!world.isRemote) {
                                            world.spawnEntity(bandit);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Shadow
    public static double getNoiseValue(int x, int y, int z) {
        if (noise == null) {
            return 0.0;
        } else {
            return noise.getNoise(x, y, z);
        }
    }
}

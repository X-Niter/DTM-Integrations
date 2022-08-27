package github.xniter.dtmintegrations.mixin.sevendaystomine.world.gen;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import nuparu.sevendaystomine.SevenDaysToMine;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.init.ModBlocks;
import nuparu.sevendaystomine.util.SimplexNoise;
import nuparu.sevendaystomine.util.Utils;
import nuparu.sevendaystomine.world.gen.RoadGenerationWorldGen;
import nuparu.sevendaystomine.world.gen.city.City;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(value = RoadGenerationWorldGen.class, remap = false)
public class MixinRoadGenerationWorldGen {
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
        if (world.getWorldType() != WorldType.FLAT) {
            if (world.getWorldType() != SevenDaysToMine.DEFAULT_WORLD && world.getWorldType() != SevenDaysToMine.WASTELAND) {
                Chunk chunk = world.getChunk(chunkX, chunkZ);
                ChunkPos chunkPos = chunk.getPos();
                if (ModConfig.worldGen.generateRoads) {
                    if (noise == null || noise.seed != world.getSeed()) {
                        noise = new SimplexNoise(world.getSeed());
                    }

                    for(int i = 0; i < 16; ++i) {
                        label101:
                        for(int j = 0; j < 16; ++j) {
                            double value = Math.abs(getNoiseValue((chunkPos.x << 4) + i, (chunkPos.z << 4) + j, 0));
                            if (value < 0.005) {
                                for(int k = ModConfig.worldGen.roadMaxY; k >= ModConfig.worldGen.roadMinY; --k) {
                                    BlockPos pos = chunkPos.getBlock(i, k, j);
                                    Biome biome = world.getBiome(pos);
                                    IBlockState state = world.getBlockState(pos);
                                    Block block = state.getBlock();
                                    if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.BEACH)) {
                                        if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER) && rand.nextInt(4096) == 0) {
                                            break;
                                        }

                                        int k2;
                                        if (Utils.isSolid(world, pos, state) && state.getMaterial() != Material.WOOD) {
                                            for(k2 = 1; k2 < 4; ++k2) {
                                                IBlockState state2 = world.getBlockState(pos.up(k2));
                                                if (state2.getMaterial() == Material.WOOD || state2.getBlock().isFoliage(world, pos.up(k2))) {
                                                    break;
                                                }

                                                world.setBlockState(pos.up(k2), Blocks.AIR.getDefaultState());
                                            }

                                            IBlockState state3 = world.getBlockState(pos.up());
                                            if (state3.getBlock().isReplaceable(world, pos.up())) {
                                                world.setBlockState(pos, ModBlocks.ASPHALT.getDefaultState());
                                            }
                                            break;
                                        }

                                        if (k == ModConfig.worldGen.roadMinY && block == Blocks.AIR) {
                                            world.setBlockState(chunkPos.getBlock(i, k + 1, j), ModBlocks.ASPHALT.getDefaultState());
                                            if (!(value < 0.001) || rand.nextInt(10) != 0) {
                                                break;
                                            }

                                            k2 = k;

                                            while(true) {
                                                if (k2 <= 0) {
                                                    continue label101;
                                                }

                                                BlockPos pos2 = chunkPos.getBlock(i, k2, j);
                                                IBlockState state2 = world.getBlockState(pos2);
                                                if (!state2.getMaterial().isReplaceable()) {
                                                    continue label101;
                                                }

                                                world.setBlockState(pos2, Blocks.COBBLESTONE.getDefaultState());
                                                --k2;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                long k = rand.nextLong() / 2L * 2L + 1L;
                long l = rand.nextLong() / 2L * 2L + 1L;
                Random rand2 = new Random((long)chunkX * k + (long)chunkZ * l ^ world.getSeed());
                if (Utils.canCityBeGeneratedHere(world, chunkX, chunkZ)) {
                    City city = City.foundCity(world, chunkPos, rand2);
                    city.startCityGen();
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

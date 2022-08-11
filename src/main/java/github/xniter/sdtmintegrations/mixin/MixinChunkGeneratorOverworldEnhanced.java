package github.xniter.sdtmintegrations.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.ForgeEventFactory;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.init.ModBlocks;
import nuparu.sevendaystomine.util.SimplexNoise;
import nuparu.sevendaystomine.util.Utils;
import nuparu.sevendaystomine.world.gen.ChunkGeneratorOverworldEnhanced;
import nuparu.sevendaystomine.world.gen.city.City;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@Mixin({ChunkGeneratorOverworldEnhanced.class})
public class MixinChunkGeneratorOverworldEnhanced extends ChunkGeneratorOverworld {

    @Shadow
    private SimplexNoise noise;
    @Shadow
    private final Random rand;

    @Shadow
    private final boolean mapFeaturesEnabled;

    @Shadow
    private Biome[] biomesForGeneration;


    @Shadow
    private final World world;

    @Shadow
    private ChunkGeneratorSettings settings;


    public MixinChunkGeneratorOverworldEnhanced(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions) {
        super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions);
        this.rand = new Random(seed);
        this.noise = new SimplexNoise(seed);
        this.mapFeaturesEnabled = mapFeaturesEnabledIn;
        this.world = worldIn;
    }

    /**
     * @author X_Niter
     * @reason Nothing is changed, fall back to generic method
     */
    @Overwrite(remap = false)
    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        super.setBlocksInChunk(x, z, primer);
    }

    /**
     * @author X_Niter
     * @reason Can we clean up road gen?
     */
    @Overwrite(remap = false)
    public void generateRoads(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
        for(int i = 0; i < 16; ++i) {
            label75:
            for(int j = 0; j < 16; ++j) {
                double value = Math.abs(this.getNoiseValue((x << 4) + i, (z << 4) + j, 0));
                if (value < 0.005) {
                    for(int k = ModConfig.worldGen.roadMaxY; k >= ModConfig.worldGen.roadMinY; --k) {
                        Biome biome = biomesIn[j + i * 16];
                        IBlockState state = primer.getBlockState(i, k, j);
                        Block block = state.getBlock();
                        if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.BEACH)) {
                            if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER) && this.rand.nextInt(4096) == 0) {
                                break;
                            }

                            int k2;
                            if (block == Blocks.STONE) {
                                for(k2 = 1; k2 < 4; ++k2) {
                                    primer.setBlockState(i, k + k2, j, Blocks.AIR.getDefaultState());
                                }

                                primer.setBlockState(i, k, j, ModBlocks.ASPHALT.getDefaultState());
                                break;
                            }

                            if (k == ModConfig.worldGen.roadMinY && block == Blocks.AIR) {
                                primer.setBlockState(i, k + 1, j, ModBlocks.ASPHALT.getDefaultState());
                                if (!(value < 0.001) || this.rand.nextInt(10) != 0) {
                                    break;
                                }

                                k2 = k;

                                while(true) {
                                    if (k2 <= 0) {
                                        continue label75;
                                    }

                                    IBlockState state2 = primer.getBlockState(i, k2, j);
                                    if (!state2.getMaterial().isReplaceable()) {
                                        continue label75;
                                    }

                                    primer.setBlockState(i, k2, j, Blocks.COBBLESTONE.getDefaultState());
                                    --k2;
                                }
                            }
                        }
                    }
                }
            }
        }

    }


    /**
     * @author X_Niter
     * @reason I was honestly lazy so I overwrote this getNoiseValue so I could use it above
     */
    @Overwrite(remap = false)
    public double getNoiseValue(int x, int y, int z) {
        return this.noise.getNoise(x, y, z);
    }

    /**
     * @author X_Niter
     * @reason Something here
     */
    @Overwrite(remap = false)
    public static boolean isPointOnLine(BlockPos pt1, BlockPos pt2, BlockPos pt, double epsilon) {
        if (!((double)(pt.getX() - Math.max(pt1.getX(), pt2.getX())) > epsilon)
                && !((double)(Math.min(pt1.getX(), pt2.getX()) - pt.getX()) > epsilon)
                && !((double)(pt.getZ() - Math.max(pt1.getZ(), pt2.getZ())) > epsilon)
                && !((double)(Math.min(pt1.getZ(), pt2.getZ()) - pt.getZ()) > epsilon))
        {
            if ((double)Math.abs(pt2.getX() - pt1.getX()) < epsilon) {

                return (double)Math.abs(pt1.getX() - pt.getX()) < epsilon || (double)Math.abs(pt2.getX() - pt.getX()) < epsilon;
            }

            else if ((double)Math.abs(pt2.getZ() - pt1.getZ()) < epsilon) {

                return (double)Math.abs(pt1.getZ() - pt.getZ()) < epsilon || (double)Math.abs(pt2.getZ() - pt.getZ()) < epsilon;
            }

            else {
                double x = pt1.getX() + (pt.getZ() - pt1.getZ()) * (pt2.getX() - pt1.getX()) / (pt2.getZ() - pt1.getZ());
                double y = pt1.getZ() + (pt.getX() - pt1.getX()) * (pt2.getZ() - pt1.getZ()) / (pt2.getX() - pt1.getX());
                return Math.abs((double)pt.getX() - x) < epsilon || Math.abs((double)pt.getZ() - y) < epsilon;
            }
        } else {
            return false;
        }
    }

    /**
     * @author X_Niter
     * @reason Nothing is changed, fall back to generic method
     */
    @Overwrite(remap = false)
    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
        super.replaceBiomeBlocks(x, z, primer, biomesIn);
    }

    /**
     * @author X_Niter
     * @reason Nothing is changed, fall back to generic method
     */
    @Overwrite(remap = false)
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);

        if (this.mapFeaturesEnabled && ModConfig.worldGen.generateRoads) {
            this.generateRoads(x, z, chunkprimer, this.biomesForGeneration);
        }

        return super.generateChunk(x, z);
    }

    /**
     * @author X_Niter
     * @reason Fall back to original method
     */
    @Overwrite(remap = false)
    private void generateHeightmap(int x, int p_185978_2_, int z) {}

    /**
     * @author X_Niter
     * @reason Attempt to clean up generation
     */
    @Overwrite(remap = false)
    public void populate(int x, int z) {
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        boolean flag = false;
        ChunkPos chunkpos = new ChunkPos(x, z);

        ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, flag);

        Random rand2 = new Random((long)x * k + (long)z * l ^ this.world.getSeed());
        if (Utils.canCityBeGeneratedHere(this.world, x, z)) {
            City city = City.foundCity(this.world, chunkpos, rand2);
            city.startCityGen();
        }

        super.populate(x, z);
    }

    /**
     * @author X_Niter
     * @reason Super Origin Class
     */
    @Overwrite(remap = false)
    public boolean generateStructures(Chunk chunkIn, int x, int z) {

        return super.generateStructures(chunkIn, x, z);
    }

    /**
     * @author X_Niter
     * @reason Super Origin Class Method
     */
    @Overwrite(remap = false)
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {

        return super.getPossibleCreatures(creatureType, pos);
    }

    /**
     * @author X_Niter
     * @reason Super Origin Class Method
     */
    @Overwrite(remap = false)
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return super.isInsideStructure(worldIn, structureName, pos);
    }

    /**
     * @author X_Niter
     * @reason Super Origin Class Method
     */
    @Overwrite(remap = false)
    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return super.getNearestStructurePos(worldIn, structureName, position, findUnexplored);
    }

    /**
     * @author X_Niter
     * @reason Super Origin Class Method
     */
    @Overwrite(remap = false)
    public void recreateStructures(Chunk chunkIn, int x, int z) {
        super.recreateStructures(chunkIn, x, z);

    }
}
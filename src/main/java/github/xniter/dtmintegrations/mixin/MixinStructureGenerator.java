package github.xniter.dtmintegrations.mixin;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import github.xniter.dtmintegrations.handlers.config.ConfigHandler;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.util.MathUtils;
import nuparu.sevendaystomine.util.Utils;
import nuparu.sevendaystomine.world.gen.StructureGenerator;
import nuparu.sevendaystomine.world.gen.city.CitySavedData;
import nuparu.sevendaystomine.world.gen.city.building.Building;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.*;

@Mixin({StructureGenerator.class})
public class MixinStructureGenerator implements ILateMixinLoader, IWorldGenerator {

    @Shadow public static List<Building> buildings = new ArrayList<>();

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
        int blockX = chunkX * 16;
        int blockZ = chunkZ * 16;
        CitySavedData data = CitySavedData.get(world);
        if (!Utils.isCityInArea(world, chunkX, chunkZ, ModConfig.worldGen.minScatteredDistanceFromCities) && !data.isScatteredNearby(new BlockPos(blockX, 128, blockZ), (long)ModConfig.worldGen.minScatteredDistanceSq)) {
            Building building = getRandomBuilding(rand);
            this.generateStructure(building, world, rand, blockX, blockZ, 20);
        }
    }

    /**
     * @author X_Niter
     * @reason Duplicate Building variable removed, Updates empty Iterator now returns Building as elements.
     */
    @Overwrite(remap = false)
    public static Building getRandomBuilding(Random rand) {
        int total = 0;

        Building building;
        for(Iterator<Building> var2 = buildings.iterator(); var2.hasNext(); total += building.weight) {
            building = var2.next();
        }

        int i = rand.nextInt(total);
        Iterator<Building> var6 = buildings.iterator();

        do {
            if (!var6.hasNext()) {
                return buildings.get(rand.nextInt(buildings.size()));
            }

            building = var6.next();
            i -= building.weight;
        } while(i > 0);

        return building;
    }

    /**
     * @author X_Niter
     * @reason Common IF statement cleanup
     */
    @Overwrite(remap = false)
    private boolean generateStructure(Building building, World world, Random rand, int blockX, int blockZ, int chance) {
        int c = (int)((double)chance * ModConfig.worldGen.scattereedStructureChanceModifier);
        if (c != 0 && rand.nextInt(c) == 0) {
            BlockPos pos = Utils.getTopGroundBlock(new BlockPos(blockX, 64, blockZ), world, true);
            if (pos.getY() >= 0) {
                if (building.allowedBlocks == null || building.allowedBlocks.length == 0 || Arrays.asList(building.allowedBlocks).contains(world.getBlockState(pos).getBlock())) {
                    Biome biome = world.provider.getBiomeForCoords(pos);
                    if (building.allowedBiomes == null || building.allowedBiomes.isEmpty() || building.allowedBiomes.contains(biome)) {
                        EnumFacing facing = EnumFacing.byHorizontalIndex(rand.nextInt(4));
                        boolean mirror = building.canBeMirrored ? rand.nextBoolean() : false;
                        BlockPos dimensions = building.getDimensions(world, facing);
                        BlockPos end = pos.offset(facing, facing.getAxis() == EnumFacing.Axis.Z ? dimensions.getZ() : dimensions.getX()).offset(mirror ? facing.rotateY() : facing.rotateYCCW(), facing.getAxis() == EnumFacing.Axis.X ? dimensions.getZ() : dimensions.getX());
                        end = Utils.getTopGroundBlock(end, world, true);
                        BlockPos pos2 = new BlockPos((double) pos.getX(), end.getY() < 0 ? (double) pos.getY() : MathUtils.lerp((double) pos.getY(), (double) end.getY(), 0.5F), (double) pos.getZ());
                        building.generate(world, pos2, facing, mirror, rand);
                        CitySavedData.get(world).addScattered(pos);
                        return true;
                    }
                }

            }
        }
        return false;
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

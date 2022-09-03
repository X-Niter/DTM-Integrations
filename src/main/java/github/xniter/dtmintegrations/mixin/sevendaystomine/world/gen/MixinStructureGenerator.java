package github.xniter.dtmintegrations.mixin.sevendaystomine.world.gen;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.init.ModBiomes;
import nuparu.sevendaystomine.init.ModBlocks;
import nuparu.sevendaystomine.util.MathUtils;
import nuparu.sevendaystomine.util.Utils;
import nuparu.sevendaystomine.world.gen.StructureGenerator;
import nuparu.sevendaystomine.world.gen.city.CitySavedData;
import nuparu.sevendaystomine.world.gen.city.building.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;

@Mixin({StructureGenerator.class})
public class MixinStructureGenerator implements IWorldGenerator {

    @Shadow public static List<Building> buildings = new ArrayList<>();

    /**
     * @author X_Niter
     * @reason Config options to enable/disable specific structures
     */
    @Overwrite(remap = false)
    public static void loadBuildings() {
        if (!ConfigGetter.getDisableAllStructures()) {

            if (ConfigGetter.getFactoryGarage()) {
                buildings.add((new BuildingFactory(new ResourceLocation("sevendaystomine", "factory_garage"), 30)).setAllowedBiomes(ModBiomes.BURNT_FOREST, ModBiomes.WASTELAND, ModBiomes.WASTELAND_FOREST, ModBiomes.WASTELAND_DESERT));
            }

            if (ConfigGetter.getLandfill()) {
                buildings.add((new BuildingLandfill(30)).setAllowedBiomes(ModBiomes.BURNT_FOREST, ModBiomes.WASTELAND, ModBiomes.WASTELAND_FOREST, ModBiomes.WASTELAND_DESERT));
            }

            if (ConfigGetter.getLookoutBirch()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "lookout_birch"), 280)).setAllowedBiomes(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getLookoutDarkOak()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "lookout_dark_oak"), 280)).setAllowedBiomes(BiomeDictionary.getBiomes(BiomeDictionary.Type.CONIFEROUS).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getLookoutBurnt()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "lookout_burnt"), 280)).setAllowedBiomes(ModBiomes.BURNT_FOREST, ModBiomes.BURNT_JUNGLE, ModBiomes.BURNT_TAIGA, ModBiomes.WASTELAND_FOREST, ModBiomes.WASTELAND).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getBurntHouse()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "burnt_house"), 300, -5)).setAllowedBiomes(ModBiomes.BURNT_FOREST, ModBiomes.BURNT_JUNGLE, ModBiomes.BURNT_TAIGA, ModBiomes.WASTELAND_FOREST, ModBiomes.WASTELAND).setAllowedBlocks(Blocks.GRASS).setPedestal(Blocks.STONE.getDefaultState()));
            }

            if (ConfigGetter.getRuinedHouse()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "ruined_house"), 300)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getShack()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "shack"), 120, 0)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getRuinedHouse1()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "ruined_house_1"), 300)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getBanditCamp()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "bandit_camp"), 80, 0, Blocks.STONE.getDefaultState())).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getRuinedHouse2()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "ruined_house_2"), 300)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getRuinedHouseIcy2()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "ruined_house_icy_2"), 300)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })));
            }

            if (ConfigGetter.getRuinedHouseIcy()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "ruined_house_icy"), 300)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })));
            }

            if (ConfigGetter.getRuinedHouseDesert1()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "ruined_house_desert_1"), 300)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.SANDY)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })));
            }

            if (ConfigGetter.getHelicopter()) {
                buildings.add((new BuildingHelicopter(new ResourceLocation("sevendaystomine", "helicopter"), 50, -3)).setHasPedestal(false).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })));
            }

            if (ConfigGetter.getObservatory()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "observatory"), 150)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getWindTurbine()) {
                buildings.add((new BuildingWindTurbine(200, 0, ModBlocks.MARBLE.getDefaultState())).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getWellBunker()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "well_bunker"), 40, -23)).setHasPedestal(false).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })));
            }

            if (ConfigGetter.getSettlement()) {
                buildings.add((new BuildingSettlement(25, -3)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getTank01()) {
                buildings.add((new BuildingHelicopter(new ResourceLocation("sevendaystomine", "tank_01"), 300, -1)).setHasPedestal(false).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.SWAMP), BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA), BiomeDictionary.getBiomes(BiomeDictionary.Type.COLD), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })));
            }

            if (ConfigGetter.getYacht()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "yacht"), 250, -4)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setHasPedestal(false));
            }

            if (ConfigGetter.getCampsite()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "campsite"), 70, -3)).setPedestal(Blocks.STONE.getDefaultState()).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getRuins0()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "ruins_0"), 150, -1)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (ConfigGetter.getRuins1()) {
                buildings.add((new Building(new ResourceLocation("sevendaystomine", "ruins_1"), 200, -1)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                    return new Biome[x$0];
                })).setAllowedBlocks(Blocks.GRASS));
            }

            if (!ModConfig.worldGen.smallStructuresOnly) {
                if (ConfigGetter.getAirport()) {
                    buildings.add((new BuildingAirport(80)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                        return new Biome[x$0];
                    })).setAllowedBlocks(Blocks.GRASS));
                }

                if (ConfigGetter.getAbandonedSettlementFarm()) {
                    buildings.add((new BuildingSettlement(15, -3, new ResourceLocation("sevendaystomine", "abandoned_settlement_farm"), new ResourceLocation("sevendaystomine", "abandoned_settlement_houses"), new ResourceLocation("sevendaystomine", "abandoned_settlement_barracks"), new ResourceLocation("sevendaystomine", "abandoned_settlement_pub"))).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                        return new Biome[x$0];
                    })).setAllowedBlocks(Blocks.GRASS));
                }

                if (ConfigGetter.getAirplaneTailDesert()) {
                    buildings.add((new BuildingAirplane(40, -4, new ResourceLocation("sevendaystomine", "airplane_tail_desert"), new ResourceLocation("sevendaystomine", "airplane_right_wing_desert"), new ResourceLocation("sevendaystomine", "airplane_left_wing_desert"), new ResourceLocation("sevendaystomine", "airplane_front_desert"))).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY)}).stream().toArray((x$0) -> {
                        return new Biome[x$0];
                    })));                }

                if (ConfigGetter.getAirplaneTail()) {
                    buildings.add((new BuildingAirplane(40, -4, new ResourceLocation("sevendaystomine", "airplane_tail"), new ResourceLocation("sevendaystomine", "airplane_right_wing"), new ResourceLocation("sevendaystomine", "airplane_left_wing"), new ResourceLocation("sevendaystomine", "airplane_front"))).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH), BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH), BiomeDictionary.getBiomes(BiomeDictionary.Type.COLD)}).stream().toArray((x$0) -> {
                        return new Biome[x$0];
                    })));
                }

                if (ConfigGetter.getCargoShip()) {
                    buildings.add((new BuildingCargoShip(60, -4)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN)}).stream().toArray((x$0) -> {
                        return new Biome[x$0];
                    })));
                }

                if (ConfigGetter.getLargeBanditCamp()) {
                    buildings.add((new BuildingLargeBanditCamp(20)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                        return new Biome[x$0];
                    })).setAllowedBlocks(Blocks.GRASS));
                }

                if (ConfigGetter.getMilitaryBase()) {
                    buildings.add((new BuildingMilitaryBase(20, -3)).setAllowedBiomes((Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.LUSH)}).stream().toArray((x$0) -> {
                        return new Biome[x$0];
                    })).setAllowedBlocks(Blocks.GRASS));
                }
            }
        }
    }

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
        if (!ConfigGetter.getDisableAllStructures() && !Utils.isCityInArea(world, chunkX, chunkZ, ModConfig.worldGen.minScatteredDistanceFromCities) && !data.isScatteredNearby(new BlockPos(blockX, 128, blockZ), ModConfig.worldGen.minScatteredDistanceSq)) {
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

        if (!ConfigGetter.getDisableAllStructures()) {

            for (Iterator<Building> var2 = buildings.iterator(); var2.hasNext(); total += building.weight) {
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
            } while (i > 0);

            return building;
        }

        return null;
    }

    /**
     * @author X_Niter
     * @reason Common IF statement cleanup
     */
    @Overwrite(remap = false)
    private boolean generateStructure(Building building, World world, Random rand, int blockX, int blockZ, int chance) {
        int c = (int)((double)chance * ModConfig.worldGen.scattereedStructureChanceModifier);
        if (c != 0 && rand.nextInt(c) == 0 && !ConfigGetter.getDisableAllStructures()) {
            BlockPos pos = Utils.getTopGroundBlock(new BlockPos(blockX, 64, blockZ), world, true);
            if (pos.getY() >= 0) {
                if (building.allowedBlocks == null || building.allowedBlocks.length == 0 || Arrays.asList(building.allowedBlocks).contains(world.getBlockState(pos).getBlock())) {
                    Biome biome = world.provider.getBiomeForCoords(pos);
                    if (building.allowedBiomes == null || building.allowedBiomes.isEmpty() || building.allowedBiomes.contains(biome)) {
                        EnumFacing facing = EnumFacing.byHorizontalIndex(rand.nextInt(4));
                        boolean mirror = building.canBeMirrored && rand.nextBoolean();
                        BlockPos dimensions = building.getDimensions(world, facing);
                        BlockPos end = pos.offset(facing, facing.getAxis() == EnumFacing.Axis.Z ? dimensions.getZ() : dimensions.getX()).offset(mirror ? facing.rotateY() : facing.rotateYCCW(), facing.getAxis() == EnumFacing.Axis.X ? dimensions.getZ() : dimensions.getX());
                        end = Utils.getTopGroundBlock(end, world, true);
                        BlockPos pos2 = new BlockPos(pos.getX(), end.getY() < 0 ? (double) pos.getY() : MathUtils.lerp(pos.getY(), end.getY(), 0.5F), pos.getZ());
                        building.generate(world, pos2, facing, mirror, rand);
                        CitySavedData.get(world).addScattered(pos);
                        return true;
                    }
                }

            }
        }
        return false;
    }
}

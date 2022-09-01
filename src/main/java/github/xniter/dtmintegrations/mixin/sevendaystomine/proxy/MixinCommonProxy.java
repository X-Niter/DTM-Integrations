package github.xniter.dtmintegrations.mixin.sevendaystomine.proxy;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nuparu.sevendaystomine.SevenDaysToMine;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.entity.*;
import nuparu.sevendaystomine.init.ModBiomes;
import nuparu.sevendaystomine.proxy.CommonProxy;
import nuparu.sevendaystomine.util.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.Set;

@Mixin(value = CommonProxy.class, remap = false)
public class MixinCommonProxy {

    @Shadow
    private static int entityID = 500;

    /**
     * @author X_Niter
     * @reason Patching entity ID conflict issues
     */
    @Overwrite(remap = false)
    public void registerEntity(ResourceLocation res, Class clazz, String name, int trackingRange, int updateFrequency, boolean tracking, int primaryColor, int secondaryColor) {

        ++entityID;

        EntityRegistry.registerModEntity(res, clazz, name, entityID, SevenDaysToMine.instance, trackingRange, updateFrequency, tracking, primaryColor, secondaryColor);

    }

    /**
     * @author X_Niter
     * @reason Patching entity ID conflict issues
     */
    @Overwrite(remap = false)
    public void registerEntity(ResourceLocation res, Class clazz, String name, int trackingRange, int updateFrequency, boolean tracking) {

        ++entityID;

        EntityRegistry.registerModEntity(res, clazz, name, entityID, SevenDaysToMine.instance, trackingRange, updateFrequency, tracking);
    }

    @Shadow
    public void registerEntity(Class clazz, String name, int trackingRange, int updateFrequency, boolean tracking) {
        this.registerEntity(new ResourceLocation("sevendaystomine:" + name), clazz, name, trackingRange, updateFrequency, tracking);
    }

    @Shadow
    public void registerEntity(Class clazz, String name, int trackingRange, int updateFrequency, boolean tracking, int primaryColor, int secondaryColor) {
        this.registerEntity(new ResourceLocation("sevendaystomine:" + name), clazz, name, trackingRange, updateFrequency, tracking, primaryColor, secondaryColor);
    }

    @Shadow
    public void addEntitySpawn(Class<? extends EntityLiving> clazz, int weight, int min, int max, EnumCreatureType type, Biome... biomes) {
        EntityRegistry.addSpawn(clazz, weight, min, max, type, biomes);
    }

    /**
     * @author X_Niter
     * @reason Patching entity ID conflict issues
     */
    @Overwrite(remap = false)
    public void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:shot"), EntityShot.class, "shot-dtm", 500, SevenDaysToMine.instance, 128, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:mountable_block"), EntityMountableBlock.class, "mountable_block-dtm", 501, SevenDaysToMine.instance, 64, 20, false);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:reanimated_corpse"), EntityReanimatedCorpse.class, "reanimated_corpse-dtm", 502, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:lootable_corpse"), EntityLootableCorpse.class, "lootable_corpse-dtm", 503, SevenDaysToMine.instance, 64, 2, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:burnt_zombie"), EntityBurntZombie.class, "burnt_zombie-dtm", 504, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:frigid_hunter"), EntityFrigidHunter.class, "frigid_hunter-dtm", 505, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:frostbitten_worker"), EntityFrostbittenWorker.class, "frostbitten_worker-dtm", 506, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:frozen_lumberjack"), EntityFrozenLumberjack.class, "frozen_lumberjack-dtm", 507, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:zombie_soldier"), EntityZombieSoldier.class, "zombie_soldier-dtm", 508, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:survivor"), EntitySurvivor.class, "survivor-dtm", 509, SevenDaysToMine.instance,64, 1, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:minibike"), EntityMinibike.class, "minibike-dtm", 510, SevenDaysToMine.instance,64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:bloated_zombie"), EntityBloatedZombie.class, "bloated_zombie-dtm", 511, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:infected_survivor"), EntityInfectedSurvivor.class, "infected_survivor-dtm", 512, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:spider_zombie"), EntitySpiderZombie.class, "spider_zombie-dtm", 513, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:plagued_nurse"), EntityPlaguedNurse.class, "plagued_nurse-dtm", 514, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:blind_zombie"), EntityBlindZombie.class, "blind_zombie-dtm", 515, SevenDaysToMine.instance,64, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:zombie_crawler"), EntityZombieCrawler.class, "zombie_crawler-dtm", 516, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:bandit"), EntityBandit.class, "bandit-dtm", 517, SevenDaysToMine.instance,64, 1, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:airdrop"), EntityAirdrop.class, "airdrop-dtm", 518, SevenDaysToMine.instance,512, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:zombie_policeman"), EntityZombiePoliceman.class, "zombie_policeman-dtm", 519, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:projectile_vomit"), EntityProjectileVomit.class, "projectile_vomit-dtm", 520, SevenDaysToMine.instance,128, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:flame"), EntityFlame.class, "flame-dtm", 521, SevenDaysToMine.instance,128, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:rocket"), EntityRocket.class, "rocket-dtm", 522, SevenDaysToMine.instance,128, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:zombie_wolf"), EntityZombieWolf.class, "zombie_wolf-dtm", 523, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:zombie_pig"), EntityZombiePig.class, "zombie_pig-dtm", 524, SevenDaysToMine.instance,96, 2, true, 16777215, 16777215);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:airplane"), EntityAirplane.class, "airplane-dtm", 525, SevenDaysToMine.instance,96, 2, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:soldier"), EntitySoldier.class, "soldier-dtm", 526, SevenDaysToMine.instance,64, 1, true, 730699, 2697513);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:chlorine_grenade"), EntityChlorineGrenade.class, "chlorine_grenade-dtm", 527, SevenDaysToMine.instance,32, 30, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:fragmentation_grenade"), EntityFragmentationGrenade.class, "fragmentation_grenade-dtm", 528, SevenDaysToMine.instance,32, 30, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:molotov"), EntityMolotov.class, "molotov-dtm", 529, SevenDaysToMine.instance,32, 30, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:zombie_miner"), EntityZombieMiner.class, "zombie_miner-dtm", 530, SevenDaysToMine.instance,64, 1, true, 5721925, 6837330);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:feral_zombie"), EntityFeralZombie.class, "feral_zombie-dtm", 531, SevenDaysToMine.instance,64, 1, true, 6119999, 7157798);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:car"), EntityCar.class, "car-dtm", 532, SevenDaysToMine.instance,64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation("sevendaystomine:flare"), EntityFlare.class, "flare-dtm", 533, SevenDaysToMine.instance,32, 30, true);

        this.addEntitySpawn(EntityReanimatedCorpse.class, ModConfig.mobs.spawnWeightReanimatedCorpse, ModConfig.mobs.spawnMinReanimatedCorpse, ModConfig.mobs.spawnMaxReanimatedCorpse, EnumCreatureType.MONSTER, (Biome[]) ForgeRegistries.BIOMES.getValuesCollection().stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityBloatedZombie.class, ModConfig.mobs.spawnWeightBloatedZombie, ModConfig.mobs.spawnMinBloatedZombie, ModConfig.mobs.spawnMaxBloatedZombie, EnumCreatureType.MONSTER, (Biome[])ForgeRegistries.BIOMES.getValuesCollection().stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityPlaguedNurse.class, ModConfig.mobs.spawnWeightPlaguedNurse, ModConfig.mobs.spawnMinPlaguedNurse, ModConfig.mobs.spawnMaxPlaguedNurse, EnumCreatureType.MONSTER, (Biome[])ForgeRegistries.BIOMES.getValuesCollection().stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityZombieCrawler.class, ModConfig.mobs.spawnWeightZombieCrawler, ModConfig.mobs.spawnMinZombieCrawler, ModConfig.mobs.spawnMaxZombieCrawler, EnumCreatureType.MONSTER, (Biome[])ForgeRegistries.BIOMES.getValuesCollection().stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityZombiePig.class, ModConfig.mobs.spawnWeightZombiePig, ModConfig.mobs.spawnMinZombiePig, ModConfig.mobs.spawnMaxZombiePig, EnumCreatureType.MONSTER, (Biome[]) Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS), BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST)}).stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntitySpiderZombie.class, ModConfig.mobs.spawnWeightSpiderZombie, ModConfig.mobs.spawnMinSpiderZombie, ModConfig.mobs.spawnMaxSpiderZombie, EnumCreatureType.MONSTER, (Biome[])ForgeRegistries.BIOMES.getValuesCollection().stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityFrozenLumberjack.class, ModConfig.mobs.spawnWeightFrozenLumberjack, ModConfig.mobs.spawnMinFrozenLumberjack, ModConfig.mobs.spawnMaxFrozenLumberjack, EnumCreatureType.MONSTER, (Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY), BiomeDictionary.getBiomes(BiomeDictionary.Type.CONIFEROUS)}).stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityFrigidHunter.class, ModConfig.mobs.spawnWeightFrigidHunter, ModConfig.mobs.spawnMinFrigidHunter, ModConfig.mobs.spawnMaxFrigidHunter, EnumCreatureType.MONSTER, (Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY), BiomeDictionary.getBiomes(BiomeDictionary.Type.COLD)}).stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityFrostbittenWorker.class, ModConfig.mobs.spawnWeightFrostbittenWorker, ModConfig.mobs.spawnMinFrostbittenWorker, ModConfig.mobs.spawnMaxFrostbittenWorker, EnumCreatureType.MONSTER, (Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY), BiomeDictionary.getBiomes(BiomeDictionary.Type.CONIFEROUS)}).stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityZombieWolf.class, ModConfig.mobs.spawnWeightZombieWolf, ModConfig.mobs.spawnMinZombieWolf, ModConfig.mobs.spawnMaxZombieWolf, EnumCreatureType.MONSTER, (Biome[])Utils.combine(new Set[]{BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY), BiomeDictionary.getBiomes(BiomeDictionary.Type.COLD)}).stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityBurntZombie.class, ModConfig.mobs.spawnWeightBurntZombie, ModConfig.mobs.spawnMinBurntZombie, ModConfig.mobs.spawnMaxBurntZombie, EnumCreatureType.MONSTER, (Biome[]) Arrays.asList(ModBiomes.BURNT_FOREST, ModBiomes.BURNT_JUNGLE, ModBiomes.WASTELAND).stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityZombieMiner.class, ModConfig.mobs.spawnWeightZombieMiner, ModConfig.mobs.spawnMinZombieMiner, ModConfig.mobs.spawnMaxZombieMiner, EnumCreatureType.MONSTER, (Biome[])ForgeRegistries.BIOMES.getValuesCollection().stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));

        this.addEntitySpawn(EntityFeralZombie.class, ModConfig.mobs.spawnWeightFeralZombie, ModConfig.mobs.spawnMinFeralZombie, ModConfig.mobs.spawnMaxFeralZombie, EnumCreatureType.MONSTER, (Biome[])ForgeRegistries.BIOMES.getValuesCollection().stream().toArray((x$0) -> {
            return new Biome[x$0];
        }));
    }
}

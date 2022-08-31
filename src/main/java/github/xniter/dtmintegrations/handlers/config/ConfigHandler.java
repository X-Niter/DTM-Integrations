package github.xniter.dtmintegrations.handlers.config;

import github.xniter.dtmintegrations.DTMIntegrations;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;


public class ConfigHandler {

    public static Configuration config;

    protected static boolean FORGE_OPT = true;

    protected static boolean BM_SLEEPING = false;

    protected static int STREET_GEN_ATTEMPTS = 5;

    protected static boolean CHANGE_TORCHES = true;

    protected static int[] ALLOWED_DIM_GEN = new int[]{0};

    protected static boolean FOCUSED_BLOODMOON = true;

    protected static boolean FOCUSED_WOLFHORDE = true;

    protected static boolean USE_LANG_CONFIG = true;

    protected static String AIRDROP_DESPAWNED_MESSAGE = "Airdrop in world %1$s, around X:%2$s Y:%3$s Z:%4$s has de-spawned!";

    protected static int AIRDROP_MAX_HEIGHT = 256;

    protected static boolean AIRDROP_CHAT_MESSAGE_ENABLED = true;

    protected static boolean AIRDROP_CHAT_MESSAGE_EXACT_LOCATION = false;

    protected static int AIRDROP_CHAT_MESSAGE_GENERAL_LOCATION = 32;
    protected static int AIRDROP_DISTANCE_FROM_PLAYER = 128;

    protected static double AIRDROP_FALL_SPEED = 6;

    protected static int AIRDROP_SMOKE_TIME = 60;

    protected static boolean AIRDROP_SOUND_FX = true;

    protected static boolean AIRDROP_GLOWING_ON_GROUND = false;

    protected static boolean DISABLE_VANILLA_BLOCKS_ITEMS_USAGE = false;

    protected static boolean ADD_DISABLED_TOOLTIP = true;

    protected static String TOOLTIP_NOTUSABLE = "%sThis item is not usable by players!%s";

    protected static String TOOLTIP_CRAFTINGONLY = "%sThis can only be used for crafting.%s";

    protected static String TOOLTIP_RECIPE_DISABLED = "%sThis recipe is disabled!%s";

    protected static boolean DISABLE_ALL_STRUCTURES = false;

    protected static boolean FACTORY_GARAGE = true;

    protected static boolean LANDFILL = true;

    protected static boolean LOOKOUT_BIRCH = true;

    protected static boolean LOOKOUT_DARK_OAK = true;

    protected static boolean LOOKOUT_BURNT = true;

    protected static boolean BURNT_HOUSE = true;

    protected static boolean RUINED_HOUSE = true;

    protected static boolean SHACK = true;

    protected static boolean RUINED_HOUSE_1 = true;

    protected static boolean BANDIT_CAMP = true;

    protected static boolean RUINED_HOUSE_2 = true;

    protected static boolean RUINED_HOUSE_ICY_2 = true;

    protected static boolean RUINED_HOUSE_ICY = true;

    protected static boolean RUINED_HOUSE_DESERT_1 = true;

    protected static boolean HELICOPTER = true;

    protected static boolean OBSERVATORY = true;

    protected static boolean WIND_TURBINE = true;

    protected static boolean WELL_BUNKER = true;

    protected static boolean SETTLEMENT = true;

    protected static boolean TANK_01 = true;

    protected static boolean YACHT = true;

    protected static boolean CAMPSITE = true;

    protected static boolean RUINS_0 = true;

    protected static boolean RUINS_1 = true;

    protected static boolean AIRPORT = true;

    protected static boolean ABANDONED_SETTLEMENT_FARM = true;

    protected static boolean AIRPLANE_TAIL_DESERT = true;

    protected static boolean AIRPLANE_TAIL = true;

    protected static boolean CARGO_SHIP = true;

    protected static boolean LARGE_BANDIT_CAMP = true;

    protected static boolean MILITARY_BASE = true;

    protected static int BANDAGE_USE_TIME = 5;

    protected static boolean TAN_INTEGRATIONS = false;

    protected static boolean AIRDROP_GLOWING_IN_AIR = false;

    protected static int BLEEDING_DAMAGE_CHANCE = 12;

    protected static int BLEEDING_AFFECT_DAMAGE_AMOUNT = 1;

    protected static boolean SHOULD_REGULAR_BANDAGES_HEAL = false;

    protected static int REGULAR_BANDAGE_HEALTH_DURATION = 100;

    protected static int REGULAR_BANDAGE_HEALTH_AMPLIFIER = 1;

    protected static boolean SHOULD_ADVANCED_BANDAGES_HEAL = true;

    protected static int ADVANCED_BANDAGE_HEALTH_DURATION = 300;

    protected static int ADVANCED_BANDAGE_HEALTH_AMPLIFIER = 3;

    public static String[] ALLOWED_MOBS_DURING_BLOODMOON = new String[]{
            "sevendaystomine:burnt_zombie",
            "sevendaystomine:frigid_hunter",
            "sevendaystomine:frostbitten_worker",
            "sevendaystomine:frozen_lumberjack",
            "sevendaystomine:zombie_soldier",
            "sevendaystomine:survivor",
            "sevendaystomine:bloated_zombie",
            "sevendaystomine:infected_survivor",
            "sevendaystomine:spider_zombie",
            "sevendaystomine:plagued_nurse",
            "sevendaystomine:blind_zombie",
            "sevendaystomine:zombie_crawler",
            "sevendaystomine:bandit",
            "sevendaystomine:zombie_policeman",
            "sevendaystomine:zombie_wolf",
            "sevendaystomine:zombie_pig",
            "sevendaystomine:soldier",
            "sevendaystomine:zombie_miner",
            "sevendaystomine:feral_zombie",
            "sevendaystomine:soldier"
    };

    public static String[] ALLOWED_MOBS_DURING_WOLFHORDE = new String[]{
            "sevendaystomine:burnt_zombie",
            "sevendaystomine:frigid_hunter",
            "sevendaystomine:frostbitten_worker",
            "sevendaystomine:frozen_lumberjack",
            "sevendaystomine:zombie_soldier",
            "sevendaystomine:survivor",
            "sevendaystomine:bloated_zombie",
            "sevendaystomine:infected_survivor",
            "sevendaystomine:spider_zombie",
            "sevendaystomine:plagued_nurse",
            "sevendaystomine:blind_zombie",
            "sevendaystomine:zombie_crawler",
            "sevendaystomine:bandit",
            "sevendaystomine:zombie_policeman",
            "sevendaystomine:zombie_wolf",
            "sevendaystomine:zombie_pig",
            "sevendaystomine:soldier",
            "sevendaystomine:zombie_miner",
            "sevendaystomine:feral_zombie",
            "sevendaystomine:soldier"
    };


    public static void init(File file){
        config = new Configuration(file);
        String category;

        category = "Forge";
        config.getCategory(category).setLanguageKey("dtmi.forge");
        config.addCustomCategoryComment(category, "The settings below are specific to Forge or FML, be it fancy tools to do things for you instead of you doing it, down to optimizations or more.");
        FORGE_OPT = config.getBoolean("Forge Optimization", category, true, "Automatically sets Forges config options too the most optimal setting for game/modpack healthiness/performance");

        category = "General";
        config.getCategory(category).setLanguageKey("dtmi.daystomine");
        config.addCustomCategoryComment(category, "General Config setting for Seven Days To Mine changes");
        BM_SLEEPING = config.getBoolean("BloodMoon Sleeping", category, false, "[Default: FALSE(No sleeping allowed)]\n[TRUE = Sleeping allowed during Blood Moon]\n[FALSE = Sleeping not allowed during Blood Moon]\nCan players sleep through a Blood Moon?");
        STREET_GEN_ATTEMPTS = config.getInt("Street Gen Attempts", category, 3, 1, 25, "[WARNING: Higher numbers severally decreases performance :USE CAUTION:]\n[SAFE OPTION: 1-10]\n[UNSAFE OPTION: 11+]\n7DTM Originally had this set to 8192 times, THAT'S A LOT, no wonder generation was so heavy!\nWith this option being controllable now, the generation is still heavy but it's much better when this is left at default or lower.\nYou can indeed set this to 1 to generate the roads at a lower increment, meaning the generation will happen in smaller bits but faster, where as the default 3 means that the road generation will repeat three times to potentially generate a longer road instead of a smaller incremented generation.\n\nMax attempts to generate streets?");
        ALLOWED_DIM_GEN = config.get(category,"Allowed Dim Gen", ALLOWED_DIM_GEN, "Dimension ID's to allow seven days to mine generation").getIntList();
        BLEEDING_DAMAGE_CHANCE = config.getInt("Bleeding Damage Chance", category, 12, 2, 500, "The chance that Bleeding affect will damage you\nHigher numbers make the chance of being damaged less likely meaning that you take damage from bleeding less often.");
        BLEEDING_AFFECT_DAMAGE_AMOUNT = config.getInt("Bleeding Damage amount", category, 1, 1, 12, "The amount of damage taken from the Bleeding affect\nHigher numbers mean more damage!");
        SHOULD_REGULAR_BANDAGES_HEAL = config.getBoolean("Regular Bandage heals player", category, false, "[DEFAULT: FALSE(Does not heal)]\nShould regular bandages heal a player or just remove the bleeding?");
        REGULAR_BANDAGE_HEALTH_DURATION = config.getInt("Regular Bandage healing duration", category, 100, 1, 500, "The duration to heal the player when they use a Regular Bandage");
        REGULAR_BANDAGE_HEALTH_AMPLIFIER = config.getInt("Regular Bandage healing amplifier", category, 1, 1, 3, "The healing effect amplifier for Regular Bandages\nThis is equivalent to Potion of Regeneration 1, 2, or 3");
        SHOULD_ADVANCED_BANDAGES_HEAL = config.getBoolean("Advanced Bandages heal", category, true, "[DEFAULT: TRUE(Advanced Bandages do heal player)]\nShould Advanced bandages heal the player on top of removing the bleeding effect");
        ADVANCED_BANDAGE_HEALTH_DURATION = config.getInt("Advanced Bandages healing duration", category, 300, 1, 1200, "The duration to heal the player when they use an Advanced Bandage");
        ADVANCED_BANDAGE_HEALTH_AMPLIFIER = config.getInt("Advanced Bandages healing amplifier", category, 3, 1, 3, "The healing effect amplifier for Advanced Bandages\nThis is equivalent to Potion of Regeneration 1, 2, or 3");


        category = "Blood Moon & Horde control";
        config.addCustomCategoryComment(category, "Options for Blood Moon and WolfHorde");
        FOCUSED_BLOODMOON = config.getBoolean("Custom BloodMoon", category, true, "Blocks all spawns during a Blood Moon that are not listed in the #Allowed Mob Spawns during Blood Moon List");
        FOCUSED_WOLFHORDE = config.getBoolean("Custom WolfHorde", category, true, "Blocks all spawns during a Wolf Horde that are not listed in the #Allowed Mob Spawns during Wolf Horde List");
        ALLOWED_MOBS_DURING_BLOODMOON = config.getStringList("Allowed Mob Spawns during Blood Moon", category, ALLOWED_MOBS_DURING_BLOODMOON, "[Requires "+"\"ModID:EntityName\"]\nList of allowed mobs during a Blood Moon");
        ALLOWED_MOBS_DURING_WOLFHORDE = config.getStringList("Allowed Mob Spawns during Wolf Horde", category, ALLOWED_MOBS_DURING_WOLFHORDE, "[Requires "+"\"ModID:EntityName\"]\nList of allowed mobs during a Wolf Horde");


        category = "Blocks & Items";
        config.addCustomCategoryComment(category, "Configuration for blocks and items");
        DISABLE_VANILLA_BLOCKS_ITEMS_USAGE = config.getBoolean("Disable Vanilla blocks and items usage", category, false, "Allows players to craft vanilla blocks and items needed by other mods, but disables the blocks and items functionality so that the blocks and items are just crafting components.");
        ADD_DISABLED_TOOLTIP = config.getBoolean("Show Disabled Tooltip", category, true, "[DEFAULT: TRUE;Show Disabled Tooltip]\nDisabled usage will show tooltip saying the usage is disabled.\n7DTM Disabled Recipes will show a tooltip saying recipe is Disabled\nEnable/Disable adding tooltip to the disabled items and blocks");
        BANDAGE_USE_TIME = config.getInt("Bandage use time", category, 5, 1, 128, "The time in seconds it takes to consume a Bandage");
        CHANGE_TORCHES = config.getBoolean("Change Torches", category, true, "Can Seven Days To Mine change torches? [Set to False if any other mods installed already make modifications to vanilla torches]");


        category = "Airdrops";
        config.getCategory(category).setLanguageKey("dtmi.aidrops");
        config.addCustomCategoryComment(category, "More in depth, fine controls over Airdrops");
        AIRDROP_MAX_HEIGHT = config.getInt("Max Drop Height", category, 256, 50, 256, "[DEFAULT: 256]\nThe max height the Airdrop can drop from");
        AIRDROP_CHAT_MESSAGE_ENABLED = config.getBoolean("Chat Notification", category, true, "[DEFAULT: TRUE(Enabled)]\nEnable/Disable Airdrop chat notifications");
        AIRDROP_CHAT_MESSAGE_EXACT_LOCATION = config.getBoolean("Notification Exact Location", category, false, "[DEFAULT: FALSE(disabled)]\nIf FALSE, then the below general location will be used\nDoes the Airdrop message in chat give the exact location of the Airdrop?");
        AIRDROP_CHAT_MESSAGE_GENERAL_LOCATION = config.getInt("Notification General Location", category, 32, 1, 256, "[DEFAULT 32 Block Range]\nThe range of the general location given in chat\nThis range controls how RANDOMLY far off the location given in chat can be, so 32 would mean that Airdrop locations given in chat is within 32 blocks of range from the actual Airdrop location\nThe equation used is [Pos x + random number between 0 and (Number you set) - random number between 0 and (Number you set)], the same equation applies to the z coords as well, x was just used for the showing of equation.");
        AIRDROP_DISTANCE_FROM_PLAYER = config.getInt("Drop Distance From Player", category, 128, 16, 256, "[DEFAULT: 128 Blocks]\nThe max range in blocks that Airdrops can be dropped around a player");
        AIRDROP_FALL_SPEED = config.getInt("Fall speed", category, 6, 1, 512, "[DEFAULT: 6]\n[Higher number is slower falling]\n[WARNING: Lower then 4 can cause de-sync and cause airdrops to appear inside the top layer of the ground]\n[5 through 16 is the best range for fast but not to fast or methodically slow enough to follow and not get it lost in sight]\nHow fast the Airdrop falls, higher number is slower falling.");
        AIRDROP_SMOKE_TIME = config.getInt("Smoke Time", category, 60, 1, 36000, "[DEFAULT 60 seconds]\n[MAX is 10 Hours aka 36000 seconds]\nHow many seconds will the Airdrop shoot smoke out the top of the box");
        AIRDROP_SOUND_FX = config.getBoolean("Sound Fx", category, true, "[DEFAULT: TRUE]\nEnable/Disable the plane sound affect from the original 7DTD game when the Airdrop is dropped");
        AIRDROP_GLOWING_IN_AIR = config.getBoolean("Glowing Airdrop In Air", category, false, "[DEFAULT FALSE(Disabled)]\nEnable/Disable the Airdrops glowing in air as they fall");
        AIRDROP_GLOWING_ON_GROUND = config.getBoolean("Glowing Airdrop On Ground", category, false, "[DEFAULT FALSE(Disabled)]\nEnable/Disable the Airdrops glowing while sitting on the ground");


        category = "Structures";
        config.addCustomCategoryComment(category,"Enable/Disable seven days to mine structures to your needs");
        DISABLE_ALL_STRUCTURES = config.getBoolean("Disable all structures", category, false, "Master kill switch to enable/disable all structures from seven days to mine");
        FACTORY_GARAGE = config.getBoolean("Factory Garage", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        LANDFILL = config.getBoolean("Landfill", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        LOOKOUT_BIRCH = config.getBoolean("Lookout Birch", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        LOOKOUT_DARK_OAK = config.getBoolean("Lookout Dark Oak", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        LOOKOUT_BURNT = config.getBoolean("Lookout Burnt", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        BURNT_HOUSE = config.getBoolean("Burnt House", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        RUINED_HOUSE = config.getBoolean("Ruined House", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        SHACK = config.getBoolean("Shack", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        RUINED_HOUSE_1 = config.getBoolean("Ruined House 1", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        BANDIT_CAMP = config.getBoolean("Bandit Camp", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        RUINED_HOUSE_2 = config.getBoolean("Ruined House 2", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        RUINED_HOUSE_ICY_2 = config.getBoolean("Ruined Icy House 2", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        RUINED_HOUSE_ICY = config.getBoolean("Ruined Icy House", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        RUINED_HOUSE_DESERT_1 = config.getBoolean("Ruined House Desert", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        HELICOPTER = config.getBoolean("Helicopter", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        OBSERVATORY = config.getBoolean("Observatory", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        WIND_TURBINE = config.getBoolean("Wing Turbine", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        WELL_BUNKER = config.getBoolean("Well Bunker", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        SETTLEMENT = config.getBoolean("Settlement", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        TANK_01 = config.getBoolean("Tank", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        YACHT = config.getBoolean("Yacht", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        CAMPSITE = config.getBoolean("Campsite", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        RUINS_0 = config.getBoolean("Ruins", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        RUINS_1 = config.getBoolean("Ruins 1", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        AIRPORT = config.getBoolean("Airport", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        ABANDONED_SETTLEMENT_FARM = config.getBoolean("Abandoned Settlement Farm", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        AIRPLANE_TAIL_DESERT = config.getBoolean("Airplane Tail Desert", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        AIRPLANE_TAIL = config.getBoolean("Airplane Tail", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        CARGO_SHIP = config.getBoolean("Cargo Ship", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        LARGE_BANDIT_CAMP = config.getBoolean("Large Bandit Camp", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");
        MILITARY_BASE = config.getBoolean("Military Base", category, true, "[DEFAULT: TRUE(Enabled)]\nenable/disable this structure");

        category = "Integrations";
        config.addCustomCategoryComment(category,"Mod Integrations");
        TAN_INTEGRATIONS = config.getBoolean("TAN Integrations", category, false, "enable/disable Tough As Nails Integration\nThis enabled will turn off 7DTM thirst and stamina and use TAN's system instead");


        category = "Language";
        config.getCategory(category).setLanguageKey("dtmi.language");
        config.addCustomCategoryComment(category, "Message Configurations, I'm not a big fan of lang files, so here you go, you can have your very own set of lang.\nLang File PR's are still welcomed and accepted if that is your preference!");
        USE_LANG_CONFIG = config.getBoolean("Use Language Config", category, true, "[DEFAULT: TRUE(Enabled)]\nEnable/Disable using this config for configuring messages/lang translations");
        AIRDROP_DESPAWNED_MESSAGE = config.getString("Airdrop Despawn Message", category, "Airdrop in world %1$s, around X:%2$s Y:%3$s Z:%4$s has de-spawned!", "The message sent to chat when the AirDrop de-spawns after sitting for too long.");
        TOOLTIP_NOTUSABLE = config.getString("Tooltip Not Usable", category, "%sThis item is not usable by players!%s", "Tooltip shown on all disabled 7DTM items/blocks");
        TOOLTIP_CRAFTINGONLY = config.getString("Tooltip Crafting Only", category, "%sThis can only be used for crafting.%s" , "Tooltip shown on 7DTM Item/blocks that have the usage disabled");
        TOOLTIP_RECIPE_DISABLED = config.getString("Tooltip Recipe Disabled", category, "%sThis recipe is disabled!%s", "Tooltip shown on 7DTM Item/blocks that have the recipe disabled");

        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event) {
        DTMIntegrations.config = new File(event.getModConfigurationDirectory() + "/" + "DTMIntegrations");
        DTMIntegrations.config.mkdir();
        init(new File(DTMIntegrations.config.getPath(), "DTMIntegrations" + ".cfg"));

        if (config.hasChanged()) {
            config.save();
            config.load();
        }
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("dtmintegrations")) {
            ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}

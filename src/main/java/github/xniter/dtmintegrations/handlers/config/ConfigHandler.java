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

    protected static boolean FORCEFUL_FOCUSED_BLOODMOON = false;

    protected static boolean FORCEFUL_FOCUSED_WOLFHORDE = false;

    protected static boolean USE_LANG_CONFIG = true;

    protected static String AIRDROP_DESPAWNED_MESSAGE = "Airdrop in world %1$s, around X:%2$s Y:%3$s Z:%4$s has de-spawned!";

    protected static int AIRDROP_MAX_HEIGHT = 256;

    protected static boolean AIRDROP_CHAT_MESSAGE_ENABLED = true;

    protected static boolean AIRDROP_CHAT_MESSAGE_EXACT_LOCATION = false;

    protected static int AIRDROP_CHAT_MESSAGE_GENERAL_LOCATION = 32;
    protected static int AIRDROP_DISTANCE_FROM_PLAYER = 128;

    protected static double AIRDROP_FALL_SPEED = 0.1;

    protected static int AIRDROP_SMOKE_TIME = 60;

    protected static boolean AIRDROP_REALISTIC_FALLING = false;

    protected static boolean AIRDROP_SOUND_FX = true;

    protected static boolean AIRDROP_GLOWING = true;

    protected static boolean DISABLE_VANILLA_BLOCKS_ITEMS_USAGE = false;

    protected static boolean ADD_DISABLED_TOOLTIP = true;

    protected static String TOOLTIP_NOTUSABLE = "%sThis item is not usable by players!%s";

    protected static String TOOLTIP_CRAFTINGONLY = "%sThis can only be used for crafting.%s";

    protected static String TOOLTIP_RECIPE_DISABLED = "%sThis recipe is disabled!%s";



    public static void init(File file){
        config = new Configuration(file);
        String category;

        category = "Forge";
        config.addCustomCategoryComment(category, "The settings below are specific to Forge or FML, be it fancy tools to do things for you instead of you doing it, down to optimizations or more.");
        FORGE_OPT = config.getBoolean("Forge Optimization", category, true, "Automatically sets Forges config options too the most optimal setting for game/modpack healthiness/performance");

        category = "7 Days To Mine";
        config.addCustomCategoryComment(category, "General Config setting for Seven Days To Mine changes");
        BM_SLEEPING = config.getBoolean("BloodMoon Sleeping", category, false, "[Default: FALSE(No sleeping allowed)]\n[TRUE = Sleeping allowed during Blood Moon]\n[FALSE = Sleeping not allowed during Blood Moon]\nCan players sleep through a Blood Moon?");
        STREET_GEN_ATTEMPTS = config.getInt("Street Gen Attempts", category, 3, 1, 25, "[WARNING: Higher numbers severally decreases performance :USE CAUTION:]\n[SAFE OPTION: 1-10]\n[UNSAFE OPTION: 11+]\n7DTM Originally had this set to 8192 times, THAT'S A LOT, no wonder generation was so heavy!\nWith this option being controllable now, the generation is still heavy but it's much better when this is left at default or lower.\nYou can indeed set this to 1 to generate the roads at a lower increment, meaning the generation will happen in smaller bits but faster, where as the default 3 means that the road generation will repeat three times to potentially generate a longer road instead of a smaller incremented generation.\n\nMax attempts to generate streets?");
        CHANGE_TORCHES = config.getBoolean("Change Torches", category, true, "Can Seven Days To Mine change torches? [Set to False if any other mods installed already make modifications to vanilla torches]");
        ALLOWED_DIM_GEN = config.get(category,"Allowed Dim Gen", ALLOWED_DIM_GEN, "Dimension ID's to allow seven days to mine generation").getIntList();
        FOCUSED_BLOODMOON = config.getBoolean("Focused BloodMoon", category, true, "Attempts to block all spawns during a blood moon that are not seven days to mine zombies");
        FOCUSED_WOLFHORDE = config.getBoolean("Focused WolfHorde", category, true, "Attempts to block all spawns during a wolf horde that are not seven days to mine entities");
        FORCEFUL_FOCUSED_BLOODMOON = config.getBoolean("Forceful Focused BloodMoon", category, false, "During a BloodMoon, ALL mobs that are not apart of the BloodMoon event will be removed forcefully from existence until the event is over");
        FORCEFUL_FOCUSED_WOLFHORDE = config.getBoolean("Forceful Focused WolfHorde", category, false, "During a WolfHorde, ALL mobs that are not apart of the WolfHorde event will be removed forcefully from existence until the event is over");
        DISABLE_VANILLA_BLOCKS_ITEMS_USAGE = config.getBoolean("Disable Vanilla blocks and items usage", category, false, "Allows players to craft vanilla blocks and items needed by other mods, but disables the blocks and items functionality so that the blocks and items are just crafting components.");
        ADD_DISABLED_TOOLTIP = config.getBoolean("Show Disabled Tooltip", category, true, "[DEFAULT: TRUE;Show Disabled Tooltip]\nDisabled usage will show tooltip saying the usage is disabled.\n7DTM Disabled Recipes will show a tooltip saying recipe is Disabled\nEnable/Disable adding tooltip to the disabled items and blocks");

        category = "7 Days To Mine Airdrops";
        config.addCustomCategoryComment(category, "More in depth, fine controls over Airdrops");

        AIRDROP_MAX_HEIGHT = config.getInt("Max Drop Height", category, 256, 50, 256, "[DEFAULT: 256]\nThe max height the Airdrop can drop from");
        AIRDROP_CHAT_MESSAGE_ENABLED = config.getBoolean("Chat Notification", category, true, "[DEFAULT: TRUE(Enabled)]\nEnable/Disable Airdrop chat notifications");
        AIRDROP_CHAT_MESSAGE_EXACT_LOCATION = config.getBoolean("Notification Exact Location", category, false, "[DEFAULT: FALSE(disabled)]\nIf FALSE, then the below general location will be used\nDoes the Airdrop message in chat give the exact location of the Airdrop?");
        AIRDROP_CHAT_MESSAGE_GENERAL_LOCATION = config.getInt("Notification General Location", category, 32, 1, 256, "[DEFAULT 32 Block Range]\nThe range of the general location given in chat\nThis range controls how RANDOMLY far off the location given in chat can be, so 32 would mean that Airdrop locations given in chat is within 32 blocks of range from the actual Airdrop location\nThe equation used is [Pos x + random number between 0 and (Number you set) - random number between 0 and (Number you set)], the same equation applies to the z coords as well, x was just used for the showing of equation.");
        AIRDROP_DISTANCE_FROM_PLAYER = config.getInt("Drop Distance From Player", category, 128, 16, 256, "[DEFAULT: 128 Blocks]\nThe max range in blocks that Airdrops can be dropped around a player");
        AIRDROP_FALL_SPEED = config.getInt("Fall speed", category, 1, 0, 50, "[DEFAULT: 1]\n[0 is Seven Days To Mine Default VERY SLOW falling]\nHow fast the Airdrop falls, higher number is faster falling.");
        AIRDROP_SMOKE_TIME = config.getInt("Smoke Time", category, 60, 1, 36000, "[DEFAULT 60 seconds]\n[MAX is 10 Hours aka 36000 seconds]\nHow many seconds will the Airdrop shoot smoke out the top of the box");
        AIRDROP_REALISTIC_FALLING = config.getBoolean("Realistic Falling", category, false, "[DEFAULT: FALSE]\nEnable/Disable realistic ANGLE falling Airdrop");
        AIRDROP_SOUND_FX = config.getBoolean("Sound Fx", category, true, "[DEFAULT: TRUE]\nEnable/Disable the plane sound affect from the original 7DTD game when the Airdrop is dropped");
        AIRDROP_GLOWING = config.getBoolean("Glowing Airdrop", category, true, "[DEFAULT TRUE]\nEnable/Disable the Airdrops glowing");

        category = "Language";
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




//    private static IntSet DIM_GEN_LIST = null;
//
//    public static IntSet getAllowedDimsForGen() {
//        if (DIM_GEN_LIST == null) {
//            DIM_GEN_LIST = new IntOpenHashSet(ConfigHandler.ALLOWED_DIM_GEN);
//        }
//
//        return DIM_GEN_LIST;
//    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("dtmintegrations")) {
            ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}

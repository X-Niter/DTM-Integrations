package github.xniter.dtmintegrations.handlers.config;

import com.google.common.base.Utf8;
import github.xniter.dtmintegrations.DTMIntegrations;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
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

    public static void init(File file){
        config = new Configuration(file);
        String category;

        category = "Days To Mine Integration Config Introduction";
        config.addCustomCategoryComment(category, "Days To Mine Integration focuses on applying changes, extra features, optimizations, and much more to the 7 Days To Mine Mod.\nIf I have done my job right, this config is capable of becoming very large, therefore, I'll try my best to name categories by the mod that it changes, or integrates into, etc.\nFor example, if I change something in forge, I'll have a Forge or FML category; For 7DTM, it will be 7DTM or the full name, etc, etc");

        category = "Forge";
        config.addCustomCategoryComment(category, "The settings below are specific to Forge or FML, be it fancy tools to do things for you instead of you doing it, down to optimizations or more.");
        FORGE_OPT = config.getBoolean("Forge Optimization", category, true, "Automatically sets Forges config options too the most optimal setting for game/modpack healthiness/performance");

        category = "7 Days To Mine";
        config.addCustomCategoryComment(category, "THE Days To Mine Integration that smashes the stupid out of 7 Days To Mine XD (That's right I said it, don't judge and just enjoy!)");
        BM_SLEEPING = config.getBoolean("BloodMoon Sleeping", category, false, "[Default: FALSE(No sleeping allowed)]\n[TRUE = Sleeping allowed during Blood Moon]\n[FALSE = Sleeping not allowed during Blood Moon]\nCan players sleep through a Blood Moon?");
        STREET_GEN_ATTEMPTS = config.getInt("Street Gen Attempts", category, 3, 1, 25, "[WARNING: Higher numbers severally decreases performance :USE CAUTION:]\n[SAFE OPTION: 1-10]\n[UNSAFE OPTION: 11+]\n7DTM Originally had this set to 8192 times, THAT'S A LOT, no wonder generation was so heavy!\nWith this option being controllable now, the generation is still heavy but it's much better when this is left at default or lower.\nYou can indeed set this to 1 to generate the roads at a lower increment, meaning the generation will happen in smaller bits but faster, where as the default 3 means that the road generation will repeat three times to potentially generate a longer road instead of a smaller incremented generation.\n\nMax attempts to generate streets?");
        CHANGE_TORCHES = config.getBoolean("Change Torches", category, true, "Can Seven Days To Mine change torches? [Set to False if any other mods installed already make modifications to vanilla torches]");
        ALLOWED_DIM_GEN = config.get(category,"Allowed Dim Gen", ALLOWED_DIM_GEN, "Dimension ID's to allow seven days to mine generation").getIntList();
        FOCUSED_BLOODMOON = config.getBoolean("Focused BloodMoon", category, true, "Attempts to block all spawns during a blood moon that are not seven days to mine zombies");
        FOCUSED_WOLFHORDE = config.getBoolean("Focused WolfHorde", category, true, "Attempts to block all spawns during a wolf horde that are not seven days to mine entities");
        FORCEFUL_FOCUSED_BLOODMOON = config.getBoolean("Forceful Focused BloodMoon", category, false, "During a BloodMoon, ALL mobs that are not apart of the BloodMoon event will be removed forcefully from existence until the event is over");
        FORCEFUL_FOCUSED_WOLFHORDE = config.getBoolean("Forceful Focused WolfHorde", category, false, "During a WolfHorde, ALL mobs that are not apart of the WolfHorde event will be removed forcefully from existence until the event is over");


        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event) {
        DTMIntegrations.config = new File(event.getModConfigurationDirectory() + "/" + "DTMIntegrations");
        DTMIntegrations.config.mkdir();
        init(new File(DTMIntegrations.config.getPath(), "DTMIntegrations" + ".cfg"));
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

package github.xniter.dtmintegrations.config;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import jdk.jfr.BooleanFlag;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.common.config.Config.*;

@Config(modid = "dtmintegrations", name = "DTMIntegrations", category = "")
public final class DTMIConfig {

    public static forgeAutoConfig forgeConfig = new forgeAutoConfig();
    public static dtmGeneral dtmGeneralConfig = new dtmGeneral();

    public static class forgeAutoConfig {
        @Name("forgeAutoConfigGameOptimization")
        @RequiresMcRestart
        public boolean forgeAutoConfigGameOptimization = true;
    }

    public static class dtmGeneral {
        @Name("hordeSleeping")
        @Comment("Can players sleep through horde night?")
        @RequiresMcRestart
        public boolean hordeSleeping = false;

        @Name("streetGenAttempts")
        @Comment("How many attempts will be made to generate a street [Bigger numbers will yield major performance loss]")
        @RangeInt(min = 1, max = 100)
        @RequiresMcRestart
        public int streetGenAttempts = 5;

        @Name("changeTorches")
        @Comment("Allow 7DTM to change torches, set to false if another mod changes torches already.")
        @RequiresMcRestart
        public boolean changeTorches = true;

        @Name("allowedDimGen")
        @Comment("[Default 0 meaning Overworld] What Dimensions should generation be allowed in? [WARNING: Added for pack developers, These structures were not meant for all worlds, USE AT YOUR OWN RISK]")
        @RequiresMcRestart
        public int[] allowedDimGen = new int[]{0};
    }

    private static IntSet DIM_GEN_LIST = null;

    public static IntSet getAllowedDimsForGen() {
        if (DIM_GEN_LIST == null) {
            DIM_GEN_LIST = new IntOpenHashSet(dtmGeneralConfig.allowedDimGen);
        }

        return DIM_GEN_LIST;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("dtmintegrations")) {
            ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}

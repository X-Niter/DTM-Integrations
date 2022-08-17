package github.xniter.dtmintegrations.config;

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
        @LangKey("dtmintegrations.Config.forgeAutoConfigGameOptimization")
        @RequiresMcRestart
        public boolean forgeAutoConfigGameOptimization = true;
    }

    public static class dtmGeneral {
        @Name("hordeSleeping")
        @Comment("Can players sleep through horde night?")
        @LangKey("dtmintegrations.Config.hordeSleeping")
        @RequiresMcRestart
        public boolean hordeSleeping = false;

        @Name("streetGenAttempts")
        @Comment("How many attempts will be made to generate a street")
        @LangKey("dtmintegrations.Config.streetGenAttempts")
        @RangeInt(min = 1, max = 100)
        @RequiresMcRestart
        public int streetGenAttempts = 5;

        @Name("changeTorches")
        @Comment("Allow 7DTM to change torches, set to false if another mod changes torches already.")
        @LangKey("dtmintegrations.Config.changeTorches")
        @RequiresMcRestart
        public boolean changeTorches = true;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("dtmintegrations")) {
            ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}

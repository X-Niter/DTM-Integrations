package github.xniter.dtmintegrations.config;

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
        @LangKey("dtmintegrations.Config.hordeSleeping")
        @RequiresMcRestart
        public boolean hordeSleeping = false;

        @Name("streetGenAttempts")
        @LangKey("dtmintegrations.Config.streetGenAttempts")
        @RequiresMcRestart
        public int streetGenAttempts = 100;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("dtmintegrations")) {
            ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}

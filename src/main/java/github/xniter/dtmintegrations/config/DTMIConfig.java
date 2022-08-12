package github.xniter.dtmintegrations.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.common.config.Config.*;

@Config(modid = "dtmintegrations", name = "DTMIntegrations", category = "")
public final class DTMIConfig {

    public static forgeAutoConfig forgeConfig = new forgeAutoConfig();

    public static class forgeAutoConfig {
        @Name("forgeAutoConfigGameOptimization")
        @LangKey("dtmintegrations.fAutoConfig.forgeAutoConfigGameOptimization")
        @RequiresMcRestart
        public boolean forgeAutoConfigGameOptimization = true;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("dtmintegrations")) {
            ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}

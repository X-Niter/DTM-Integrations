package github.xniter.dtmintegrations.features;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import github.xniter.dtmintegrations.handlers.config.ConfigHandler;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class FMLAutoConfig {

    public FMLAutoConfig() {
        ConfigCategory forgeConfig = ForgeModContainer.getConfig().getCategory(Configuration.CATEGORY_GENERAL);

        if (ConfigGetter.getForgeOpt()) {

            if (!forgeConfig.get("alwaysSetupTerrainOffThread").getBoolean()) {
                ForgeModContainer.alwaysSetupTerrainOffThread = true;
            }

            if (!forgeConfig.get("fixVanillaCascading").getBoolean()) {
                ForgeModContainer.fixVanillaCascading = true;
            }

            if (!forgeConfig.get("logCascadingWorldGeneration").getBoolean()) {
                ForgeModContainer.logCascadingWorldGeneration = false;
            }
        }
    }
}

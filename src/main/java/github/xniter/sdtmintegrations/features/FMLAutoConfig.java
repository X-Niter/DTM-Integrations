package github.xniter.sdtmintegrations.features;

import github.xniter.sdtmintegrations.config.DTMIConfig;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Map;

public class FMLAutoConfig {

    public FMLAutoConfig() {
        ConfigCategory forgeConfig = ForgeModContainer.getConfig().getCategory(Configuration.CATEGORY_GENERAL);

        if (DTMIConfig.forgeConfig.forgeAutoConfigGameOptimization) {

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

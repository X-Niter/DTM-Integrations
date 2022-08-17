package github.xniter.dtmintegrations.integration;

import net.minecraftforge.fml.common.Loader;

public class IntegrationHelper {
    public IntegrationHelper() {
    }

    public static boolean isJEILoaded() {
        return Loader.isModLoaded("jei");
    }

    public static boolean isLootrLoaded() {
        return Loader.isModLoaded("lootr");
    }

    public static boolean isBaublesLoaded() {
        return Loader.isModLoaded("baubles");
    }

    public static boolean isSRParasitesLoaded() {
        return Loader.isModLoaded("srparasites");
    }

    public static boolean isSevenDaysToMineLoaded() {
        return Loader.isModLoaded("sevendaystomine");
    }
}

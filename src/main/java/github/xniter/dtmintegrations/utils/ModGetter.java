package github.xniter.dtmintegrations.utils;

import net.minecraftforge.fml.common.Loader;

public class ModGetter {

    public ModGetter() {
    }

    public static boolean isJEILoaded() {
        return Loader.isModLoaded("jei");
    }

    public static boolean isTANLoaded() {
        return Loader.isModLoaded("toughasnails");
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

    public static boolean isSimpleDifficultyLoaded() {
        return Loader.isModLoaded("simpledifficulty");
    }
}

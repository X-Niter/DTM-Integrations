package github.xniter.dtmintegrations.handlers;

import net.minecraftforge.fml.common.Loader;

public class ModGetter {

    public ModGetter() {
    }

    public static boolean isJEILoaded() {
        return Loader.isModLoaded("jei");
    }

    public static boolean isTANLoaded() {
        try{
            if (Loader.isModLoaded("toughasnails")) {
                return true;
            }

        } catch (NoClassDefFoundError error) {
            return false;
        }
        return false;
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

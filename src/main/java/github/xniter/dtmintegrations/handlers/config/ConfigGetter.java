package github.xniter.dtmintegrations.handlers.config;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

public class ConfigGetter extends ConfigHandler{
    public static boolean getForgeOpt(){
        return FORGE_OPT;
    }

    public static boolean getBMSleeping(){
        return BM_SLEEPING;
    }

    public static int getStreetGenAttempts(){
        return STREET_GEN_ATTEMPTS;
    }

    public static boolean getChangeTorches(){
        return CHANGE_TORCHES;
    }

    public static IntSet getAllowedDimGen(){
        return new IntOpenHashSet(ALLOWED_DIM_GEN);
    }

    public static boolean getFocusedBloodMoon(){
        return FOCUSED_BLOODMOON;
    }

    public static boolean getFocusedWolfHorde(){
        return FOCUSED_WOLFHORDE;
    }
}

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
    public static boolean getUseLangConfig(){
        return USE_LANG_CONFIG;
    }

    public static String getAirdropDespawnMessage(){
        return AIRDROP_DESPAWNED_MESSAGE;
    }

    public static int getAirdropMaxHeight(){
        return AIRDROP_MAX_HEIGHT;
    }

     public static boolean getAirdropChatMessageEnabled(){
         return AIRDROP_CHAT_MESSAGE_ENABLED;
     }

    public static boolean getAirdropChatMessageExactLocation(){
        return AIRDROP_CHAT_MESSAGE_EXACT_LOCATION;
    }

    public static int getAirdropChatMessageGeneralLocation(){
        return AIRDROP_CHAT_MESSAGE_GENERAL_LOCATION;
    }

    public static int getAirdropDistanceFromPlayer(){
        return AIRDROP_DISTANCE_FROM_PLAYER;
    }

    public static double getAirdropFallingSpeed(){
        return AIRDROP_FALL_SPEED;
    }

    public static int getAirdropSmokeTime(){
        return AIRDROP_SMOKE_TIME;
    }

    public static boolean getAirdropRealisticFalling(){
        return AIRDROP_REALISTIC_FALLING;
    }
}

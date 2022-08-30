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

    public static boolean getForcefulFocusedBloodMoon(){
        return FORCEFUL_FOCUSED_BLOODMOON;
    }

    public static boolean getForcefulFocusedWolfHorde(){
        return FORCEFUL_FOCUSED_WOLFHORDE;
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

    public static boolean getAirdropSoundFXEnabled(){
        return AIRDROP_SOUND_FX;
    }

    public static boolean getAirdropGlowingInAirEnabled(){
        return AIRDROP_GLOWING_IN_AIR;
    }

    public static boolean getDisableVanillaBlocksAndItemsUsage(){
        return DISABLE_VANILLA_BLOCKS_ITEMS_USAGE;
    }

    public static boolean getAddDisabledTooltip(){
        return ADD_DISABLED_TOOLTIP;
    }

    public static String getTooltipNotUsableString(){
        return TOOLTIP_NOTUSABLE;
    }

    public static String getTooltipCraftingOnlyString(){
        return TOOLTIP_CRAFTINGONLY;
    }

    public static String getTooltipRecipeDisabledString(){
        return TOOLTIP_RECIPE_DISABLED;
    }

    public static boolean getDisableAllStructures(){
        return DISABLE_ALL_STRUCTURES;
    }

    public static boolean getFactoryGarage() {
        return FACTORY_GARAGE;
    }

    public static boolean getLandfill() {
        return LANDFILL;
    }

    public static boolean getLookoutBirch() {
        return LOOKOUT_BIRCH;
    }

    public static boolean getLookoutDarkOak() {
        return LOOKOUT_DARK_OAK;
    }

    public static boolean getLookoutBurnt() {
        return LOOKOUT_BURNT;
    }

    public static boolean getBurntHouse() {
        return BURNT_HOUSE;
    }

    public static boolean getRuinedHouse() {
        return RUINED_HOUSE;
    }

    public static boolean getShack() {
        return SHACK;
    }

    public static boolean getRuinedHouse1() {
        return RUINED_HOUSE_1;
    }

    public static boolean getBanditCamp() {
        return BANDIT_CAMP;
    }

    public static boolean getRuinedHouse2() {
        return RUINED_HOUSE_2;
    }

    public static boolean getRuinedHouseIcy2() {
        return RUINED_HOUSE_ICY_2;
    }

    public static boolean getRuinedHouseIcy() {
        return RUINED_HOUSE_ICY;
    }

    public static boolean getRuinedHouseDesert1() {
        return RUINED_HOUSE_DESERT_1;
    }

    public static boolean getHelicopter() {
        return HELICOPTER;
    }

    public static boolean getObservatory() {
        return OBSERVATORY;
    }

    public static boolean getWindTurbine() {
        return WIND_TURBINE;
    }

    public static boolean getWellBunker() {
        return WELL_BUNKER;
    }

    public static boolean getSettlement() {
        return SETTLEMENT;
    }

    public static boolean getTank01() {
        return TANK_01;
    }

    public static boolean getYacht() {
        return YACHT;
    }

    public static boolean getCampsite() {
        return CAMPSITE;
    }

    public static boolean getRuins0() {
        return RUINS_0;
    }

    public static boolean getRuins1() {
        return RUINS_1;
    }

    public static boolean getAirport() {
        return AIRPORT;
    }
    public static boolean getAbandonedSettlementFarm() {
        return ABANDONED_SETTLEMENT_FARM;
    }
    public static boolean getAirplaneTailDesert() {
        return AIRPLANE_TAIL_DESERT;
    }

    public static boolean getAirplaneTail() {
        return AIRPLANE_TAIL;
    }

    public static boolean getCargoShip() {
        return CARGO_SHIP;
    }

    public static boolean getLargeBanditCamp() {
        return LARGE_BANDIT_CAMP;
    }

    public static boolean getMilitaryBase() {
        return MILITARY_BASE;
    }

    public static int getBandageUseTime() {
        return BANDAGE_USE_TIME;
    }

    public static boolean getTanIntegration() {
        return TAN_INTEGRATIONS;
    }

    public static boolean getAirdropGlowingOnGroundEnabled(){
        return AIRDROP_GLOWING_ON_GROUND;
    }

    public static int getBleedingDamageChance() {
        return BLEEDING_DAMAGE_CHANCE;
    }

    public static int getBleedingAffectDamageAmount() {
        return BLEEDING_AFFECT_DAMAGE_AMOUNT;
    }

    public static boolean getShouldRegularBandagesHeal() {
        return SHOULD_REGULAR_BANDAGES_HEAL;
    }

    public static int getRegularBandageHealthDuration() {
        return REGULAR_BANDAGE_HEALTH_DURATION;
    }

    public static int getRegularBandageHealthAmplifier() {
        return REGULAR_BANDAGE_HEALTH_AMPLIFIER;
    }

    public static boolean getShouldAdvancedBandagesHeal() {
        return SHOULD_ADVANCED_BANDAGES_HEAL;
    }

    public static int getAdvancedBandagesHealthDuration() {
        return ADVANCED_BANDAGE_HEALTH_DURATION;
    }

    public static int getAdvancedBandageHealthAmplifier() {
        return ADVANCED_BANDAGE_HEALTH_AMPLIFIER;
    }

}

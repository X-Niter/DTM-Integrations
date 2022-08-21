package github.xniter.dtmintegrations;

import github.xniter.dtmintegrations.handlers.config.ConfigHandler;
import github.xniter.dtmintegrations.handlers.FMLEvents;
import github.xniter.dtmintegrations.features.FMLAutoConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(
        modid = DTMIntegrations.MOD_ID,
        name = DTMIntegrations.MOD_NAME,
        version = DTMIntegrations.VERSION,
        dependencies = "required-after:sevendaystomine;required-after:mixinbooter"
)
public class DTMIntegrations {
    private static final int CONFIG_VERSION = 2;
    public static final String MOD_ID = "dtmintegrations";
    public static final String MOD_NAME = "DTM Integrations";
    public static final String VERSION = "1.3.0";

    public static File config;

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static DTMIntegrations INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        ConfigHandler.registerConfig(event);

        // Register event listeners
        MinecraftForge.EVENT_BUS.register(ConfigHandler.class);
        MinecraftForge.EVENT_BUS.register(FMLAutoConfig.class);
        MinecraftForge.EVENT_BUS.register(FMLEvents.class);




    }

    /* TODO
        Pipe recipe (recipe?) [1 forged iron in inventory(1 pipe IRL), or 10 iron scrap and clay in forge (1 Pipe IRL)]
        Claw Hammer recipe (Recipe Book?)
        Wrench recipe (Recipe Book?)
        Pipe weapons, comes with perk in irl game
        Airdrop (Wheres the fucking bundles bro?)
        Drinks give tough as nails thirst values
     */

//    /**
//     * This is the second initialization event. Register custom recipes
//     */
//    @Mod.EventHandler
//    public void init(FMLInitializationEvent event) {
//
//    }
//
//    /**
//     * This is the final initialization event. Register actions from other mods here
//     */
//    @Mod.EventHandler
//    public void postinit(FMLPostInitializationEvent event) {
//    }

//    /**
//     * Forge will automatically look up and bind blocks to the fields in this class
//     * based on their registry name.
//     */
//    @GameRegistry.ObjectHolder(MOD_ID)
//    public static class Blocks {
//      /*
//          public static final MySpecialBlock mySpecialBlock = null; // placeholder for special block below
//      */
//    }
//
//    /**
//     * Forge will automatically look up and bind items to the fields in this class
//     * based on their registry name.
//     */
//    @GameRegistry.ObjectHolder(MOD_ID)
//    public static class Items {
//      /*
//          public static final ItemBlock mySpecialBlock = null; // itemblock for the block above
//          public static final MySpecialItem mySpecialItem = null; // placeholder for special item below
//      */
//    }
//
//    /**
//     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
//     */
//    @Mod.EventBusSubscriber
//    public static class ObjectRegistryHandler {
//        /**
//         * Listen for the register event for creating custom items
//         */
//        @SubscribeEvent
//        public static void addItems(RegistryEvent.Register<Item> event) {
//           /*
//             event.getRegistry().register(new ItemBlock(Blocks.myBlock).setRegistryName(MOD_ID, "myBlock"));
//             event.getRegistry().register(new MySpecialItem().setRegistryName(MOD_ID, "mySpecialItem"));
//            */
//        }
//
//        /**
//         * Listen for the register event for creating custom blocks
//         */
//        @SubscribeEvent
//        public static void addBlocks(RegistryEvent.Register<Block> event) {
//           /*
//             event.getRegistry().register(new MySpecialBlock().setRegistryName(MOD_ID, "mySpecialBlock"));
//            */
//        }
//    }
//    /* EXAMPLE ITEM AND BLOCK - you probably want these in separate files
//    public static class MySpecialItem extends Item {
//
//    }
//
//    public static class MySpecialBlock extends Block {
//
//    }
//    */
}

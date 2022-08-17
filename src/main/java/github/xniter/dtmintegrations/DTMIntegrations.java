package github.xniter.dtmintegrations;

import github.xniter.dtmintegrations.config.DTMIConfig;
import github.xniter.dtmintegrations.features.FMLAutoConfig;
import github.xniter.dtmintegrations.integration.IntegrationHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nuparu.sevendaystomine.SevenDaysToMine;
import nuparu.sevendaystomine.util.Utils;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

@Mod(
        modid = DTMIntegrations.MOD_ID,
        name = DTMIntegrations.MOD_NAME,
        version = DTMIntegrations.VERSION,
        dependencies = "required-after:sevendaystomine;required-after:mixinbooter"
)
public class DTMIntegrations {
    private static final int CONFIG_VERSION = 1;

    public static final String MOD_ID = "dtmintegrations";
    public static final String MOD_NAME = "DTM Integrations";
    public static final String VERSION = "1.2.1";

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
        Logger log = event.getModLog();
        File modDir = new File(event.getModConfigurationDirectory(), "DTMIntegrations");
        modDir.mkdirs();

        // Check if config is outdated and needs to be deleted
        boolean configOutdated;
        File configVersionFile = new File(modDir, "config_version");
        if (configVersionFile.exists()) {
            try (FileReader reader = new FileReader(configVersionFile); Scanner scanner = new Scanner(reader)) {
                try {
                    configOutdated = scanner.nextInt() != CONFIG_VERSION;
                } catch (NumberFormatException e) {
                    configOutdated = true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            configOutdated = true;
        }

        if (configOutdated) {
            File configFile = new File(event.getModConfigurationDirectory(), "DTMIntegrations.cfg");
            if (configFile.exists()) {
                log.info("Regenerating outdated config");
                configFile.delete();
            }
            try (FileWriter writer = new FileWriter(configVersionFile)) {
                writer.write(String.valueOf(CONFIG_VERSION));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        ConfigManager.sync("dtmintegrations", Config.Type.INSTANCE);

        // Register event listeners
        MinecraftForge.EVENT_BUS.register(DTMIConfig.class);
        MinecraftForge.EVENT_BUS.register(FMLAutoConfig.class);

        if (IntegrationHelper.isLootrLoaded()) {

            if (!noobanidus.mods.lootr.config.ConfigManager.CONVERT_ALL_LOOTABLES_EXCEPT_BELOW) {

                Utils.getLogger().error("LOOTR is loaded but [CONVERT_ALL_LOOTABLES_EXCEPT_BELOW] is false, EXPECT invisible cars");


            } else {
                Arrays.stream(SevenDaysToMine.BLOCKS).forEach(block -> {
                    if (block instanceof IInventory) {
                        ResourceLocation blockResource = block.getRegistryName();
                        noobanidus.mods.lootr.config.ConfigManager.getAdditionalChests().add(blockResource);
                    }
                });
            }

        }


    }

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

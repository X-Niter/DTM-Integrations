package github.xniter.dtmintegrations.handlers.events;

import github.xniter.dtmintegrations.handlers.ResourceBoolArrayHandler;
import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HoeEvent extends ResourceBoolArrayHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onHoeUse(UseHoeEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack mainHand = player.getHeldItemMainhand();

        if (ConfigGetter.getDisableVanillaBlocksAndItemsUsage()) {
            if (isDisabledItem(mainHand)) {
                event.setCanceled(true);
            }
        }
    }
}

package github.xniter.dtmintegrations.mixin.sevendaystomine;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.events.PlayerEventHandler;
import nuparu.sevendaystomine.item.IQuality;
import nuparu.sevendaystomine.util.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({PlayerEventHandler.class})
public class MixinPlayerEventHandler {


    /**
     * @author X_Niter
     * @reason Only IF immersiveBlockBreaking is true will be bother to go through the code, if it's false, then just don't change anything as vanilla handles it
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (ModConfig.players.immersiveBlockBreaking) {
            float speed = event.getOriginalSpeed() / (event.getState().getMaterial() != Material.CIRCUITS && event.getState().getMaterial() != Material.WEB ? ModConfig.players.immersiveBlockBreakingModifier : 1.0F);

            if (ModConfig.players.qualitySystem) {
                ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
                Item item = stack.getItem();
                if (!stack.isEmpty() && item instanceof IQuality) {
                    speed *= 1.0F + (float) ((IQuality) item).getQuality(stack) / 128.0F;
                }
            }

            event.setNewSpeed(speed);
        }
    }


    /**
     * @author X_Niter
     * @reason Additional config to allow players to sleep through horde or not
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void onPlayerSleepInBed(PlayerSleepInBedEvent event) {
        World world = event.getEntityPlayer().world;
        if (!ConfigGetter.getBMSleeping() && Utils.isBloodmoon(world)) {
            event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);
        }

    }
}

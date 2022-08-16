package github.xniter.dtmintegrations.mixin;

import github.xniter.dtmintegrations.config.DTMIConfig;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.events.PlayerEventHandler;
import nuparu.sevendaystomine.item.IQuality;
import nuparu.sevendaystomine.util.MathUtils;
import nuparu.sevendaystomine.util.Utils;
import nuparu.sevendaystomine.world.gen.city.City;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin({PlayerEventHandler.class})
public class MixinPlayerEventHandler implements ILateMixinLoader {


    /**
     * @author X_Niter
     * @reason Only IF immersiveBlockBreaking is true will be bother to go through the code, if it's false, then just don't change anything as vanilla handles it
     */
    @Overwrite(remap = false)
    @SubscribeEvent
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
    @Overwrite(remap = false)
    @SubscribeEvent
    public void onPlayerSleepInBed(PlayerSleepInBedEvent event) {
        World world = event.getEntityPlayer().world;
        if (!DTMIConfig.dtmEventsConfig.hordeSleeping && Utils.isBloodmoon(world)) {
            event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);
        }

    }


    @Override
    public List<String> getMixinConfigs()
    {
        return Collections.singletonList("mixins.dtmintegrations.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        return ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
    }

    @Override
    public void onMixinConfigQueued(String mixinConfig)
    {
        ILateMixinLoader.super.onMixinConfigQueued(mixinConfig);
    }
}

package github.xniter.dtmintegrations.mixin;

import github.xniter.dtmintegrations.config.DTMIConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import nuparu.sevendaystomine.block.repair.BreakSavedData;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.entity.EntityAirdrop;
import nuparu.sevendaystomine.events.TickHandler;
import nuparu.sevendaystomine.util.Utils;
import nuparu.sevendaystomine.world.MiscSavedData;
import nuparu.sevendaystomine.world.horde.HordeSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin({TickHandler.class})
public class MixinTickHandler implements ILateMixinLoader {


    @Shadow public static float recoil = 0.0F;
    @Shadow public static float antiRecoil = 0.0F;
    @Shadow public static int time = 0;
    @Shadow public static int useCount = 0;
    @Shadow public static int windCounter;
    @Shadow public static int beat;
    @Shadow private static ResourceLocation bleedShaderRes;
    @Shadow private static ResourceLocation nightShaderRes;
    @Shadow public static ResourceLocation drunkShaderRes;
    @Shadow public static Method f_loadShader;
    @Shadow private static Method f_setSize;
    @Shadow private static Minecraft mc;
    @Shadow public static boolean bloodmoon;
    @Shadow public static Field f_MOON_PHASES_TEXTURES;
    @Shadow public static ResourceLocation bloodmoon_texture;
    @Shadow public static ResourceLocation default_moon_texture;
    @Shadow private long nextTorchCheck = 0L;
    @Shadow public static Framebuffer fbo;

    /**
     * @author X_Niter
     * @reason World control config input and air drop changes
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        if (world != null) {
            HordeSavedData.get(world).update(world);
            BreakSavedData.get(world).update(world);

            int worldProviders = world.provider.getDimension();

            DTMIConfig.getAllowedDimsForGen().forEach(dimToGen -> {
                if (worldProviders == dimToGen) {
                    if (!world.isRemote && ModConfig.world.airdropFrequency > 0 && event.phase == TickEvent.Phase.START) {
                        MinecraftServer server = world.getMinecraftServer();
                        if (server != null && server.getPlayerList().getCurrentPlayerCount() != 0) {
                            long time = world.getWorldTime() % 24000L;
                            MiscSavedData miscData = MiscSavedData.get(world);
                            if (time >= 6000L && miscData.getLastAirdrop() != Utils.getDay(world) && Utils.getDay(world) % ModConfig.world.airdropFrequency == 0) {
                                miscData.setLastAirdrop(Utils.getDay(world));
                                BlockPos pos = Utils.getAirdropPos(world);
                                EntityAirdrop airDrop = new EntityAirdrop(world, world.getSpawnPoint().up(255));
                                world.spawnEntity(airDrop);
                                airDrop.setPosition(pos.getX(), pos.getY(), pos.getZ());
                                Random rand = new Random();
                                server.getPlayerList().sendMessage(new TextComponentTranslation("airdrop.message", airDrop.getEntityWorld().getWorldInfo().getWorldName(), pos.getX() + rand.nextInt(32) - rand.nextInt(32), pos.getZ() + rand.nextInt(32) - rand.nextInt(32)));
                            }
                        }
                    }
                }
            });
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

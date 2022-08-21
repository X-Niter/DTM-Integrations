package github.xniter.dtmintegrations.mixin;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import github.xniter.dtmintegrations.handlers.config.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import nuparu.sevendaystomine.advancements.ModTriggers;
import nuparu.sevendaystomine.block.repair.BreakSavedData;
import nuparu.sevendaystomine.capability.CapabilityHelper;
import nuparu.sevendaystomine.capability.IExtendedPlayer;
import nuparu.sevendaystomine.client.sound.SoundHelper;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.entity.EntityAirdrop;
import nuparu.sevendaystomine.events.TickHandler;
import nuparu.sevendaystomine.init.ModBlocks;
import nuparu.sevendaystomine.potions.Potions;
import nuparu.sevendaystomine.util.DamageSources;
import nuparu.sevendaystomine.util.PlayerUtils;
import nuparu.sevendaystomine.util.Utils;
import nuparu.sevendaystomine.world.MiscSavedData;
import nuparu.sevendaystomine.world.gen.city.CityData;
import nuparu.sevendaystomine.world.gen.city.CitySavedData;
import nuparu.sevendaystomine.world.horde.BloodmoonHorde;
import nuparu.sevendaystomine.world.horde.GenericHorde;
import nuparu.sevendaystomine.world.horde.HordeSavedData;
import nuparu.sevendaystomine.world.horde.ZombieWolfHorde;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static nuparu.sevendaystomine.events.TickHandler.handleExtendedPlayer;

@Mixin({TickHandler.class})
public class MixinTickHandler implements ILateMixinLoader {

    @Shadow
    private long nextTorchCheck = 0L;

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

            ConfigGetter.getAllowedDimGen().forEach(dimToGen -> {
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

    /**
     * @author X_Niter
     * @reason World control config input and air drop changes
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            EntityPlayer player = event.player;
            if (player == null || player.isDead) {
                return;
            }

            World world = player.world;
            IExtendedPlayer extendedPlayer = CapabilityHelper.getExtendedPlayer(player);
            if (player instanceof EntityPlayerMP && !player.isCreative() && !player.isSpectator()) {
                if (world.getGameRules().getBoolean("handleThirst")) {
                    handleExtendedPlayer(player, world, extendedPlayer);
                }

                IExtendedPlayer iep = CapabilityHelper.getExtendedPlayer(player);
                EntityPlayerMP playerMP = (EntityPlayerMP)player;
                long time = world.getWorldTime() % 24000L;
                int day = Utils.getDay(world);
                if (!world.isRemote && world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                    if (Utils.isBloodmoon(world) && time > 13000L && time < 23000L) {
                        if (iep.getBloodmoon() != day) {
                            BlockPos pos = new BlockPos(playerMP);
                            BloodmoonHorde horde = new BloodmoonHorde(pos, world, playerMP);
                            horde.addTarget(playerMP);
                            horde.start();
                            iep.setBloodmoon(day);
                            world.playSound(null, pos, SoundHelper.HORDE, SoundCategory.HOSTILE, world.rand.nextFloat() * 0.1F + 0.95F, world.rand.nextFloat() * 0.1F + 0.95F);
                        }
                    } else if (time > 1000L && time < 1060L && iep.getWolfHorde() != day && Utils.isWolfHorde(world)) {
                        ZombieWolfHorde horde = new ZombieWolfHorde(new BlockPos(player), world, player);
                        horde.addTarget(playerMP);
                        horde.start();
                        iep.setWolfHorde(day);
                    } else if (!iep.hasHorde(world)) {
                        CitySavedData csd = CitySavedData.get(world);
                        CityData city = csd.getClosestCity(new BlockPos(player), 100.0);
                        if (world.rand.nextDouble() < ModConfig.world.genericHordeChance * (double)(city == null ? 1.0F : 1.0F + (float)(10 * city.getZombieLevel()) / 1024.0F)) {
                            GenericHorde horde = new GenericHorde(new BlockPos(player), world, player);
                            if (city != null && city.getZombieLevel() > 0) {
                                city.setZombieLevel(city.getZombieLevel() - horde.waves * horde.getZombiesInWave());
                            }

                            horde.addTarget(playerMP);
                            horde.start();
                            iep.setHorde(day);
                        }
                    }
                }

                if (Utils.isBloodmoon(day - 1) && time < 1000L && iep.getLastBloodmoonSurvivalCheck() < day) {
                    ModTriggers.BLOODMOON_SURVIVAL.trigger(playerMP);
                    iep.setLastBloodmoonSurvivalCheck(day);
                }
            }

            int i;
            if (extendedPlayer.isInfected()) {
                i = extendedPlayer.getInfectionTime();
                extendedPlayer.setInfectionTime(i + 1);
                PotionEffect effect = player.getActivePotionEffect(Potions.infection);
                int amplifier = PlayerUtils.getInfectionStage(i);
                int timeLeft = PlayerUtils.getCurrentInectionStageRemainingTime(i);
                if (effect == null || effect.getAmplifier() != amplifier) {
                    player.addPotionEffect(new PotionEffect(Potions.infection, Math.min(24000, timeLeft), amplifier));
                }

                if (amplifier == PlayerUtils.getNumberOfstages() - 1) {
                    player.attackEntityFrom(DamageSources.infection, 1.0F);
                }
            }

            if (ConfigGetter.getChangeTorches() && System.currentTimeMillis() > this.nextTorchCheck) {
                ItemStack s;
                for(i = 0; i < player.inventory.mainInventory.size(); ++i) {
                    s = player.inventory.mainInventory.get(i);
                    if (s.getItem() == Item.getItemFromBlock(Blocks.TORCH)) {
                        player.inventory.mainInventory.set(i, new ItemStack(ModBlocks.TORCH_LIT, s.getCount()));
                    }
                }

                for(i = 0; i < player.inventory.armorInventory.size(); ++i) {
                    s = player.inventory.armorInventory.get(i);
                    if (s.getItem() == Item.getItemFromBlock(Blocks.TORCH)) {
                        player.inventory.armorInventory.set(i, new ItemStack(ModBlocks.TORCH_LIT, s.getCount()));
                    }
                }

                this.nextTorchCheck = System.currentTimeMillis() + 1000L;
            }
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

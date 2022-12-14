package github.xniter.dtmintegrations.mixin.sevendaystomine.events;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import github.xniter.dtmintegrations.utils.GenericHordeUtils;
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

import java.util.ArrayList;
import java.util.Random;

@Mixin({TickHandler.class})
public class MixinTickHandler {

    @Shadow
    private long nextTorchCheck = 0L;

    private static Utils utils;

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
                                EntityAirdrop airDrop = new EntityAirdrop(world, world.getSpawnPoint().up(ConfigGetter.getAirdropMaxHeight()));
                                world.spawnEntity(airDrop);

                                Random rand = new Random();
                                // server.getPlayerList().sendMessage
                                airDrop.setPosition(pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getY(), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()));

                                if (ConfigGetter.getAirdropChatMessageEnabled()) {
                                    if (ConfigGetter.getAirdropChatMessageWorldLocation()) {
                                        if (ConfigGetter.getAirdropChatMessageWorldName()) {
                                            if (ConfigGetter.getAirdropChatMessageExactLocation()) {
                                                if (ConfigGetter.getUseLangConfig()) {
                                                    server.getPlayerList().sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropMessage(), world.getWorldInfo().getWorldName(), pos.getX(), pos.getZ()));
                                                } else {
                                                    server.getPlayerList().sendMessage(new TextComponentTranslation("airdrop.message", world.getWorldInfo().getWorldName(), pos.getX(), pos.getZ()));
                                                }
                                            } else {
                                                if (ConfigGetter.getUseLangConfig()) {
                                                    server.getPlayerList().sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropMessage(), world.getWorldInfo().getWorldName(), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                                } else {
                                                    server.getPlayerList().sendMessage(new TextComponentTranslation("airdrop.message", world.getWorldInfo().getWorldName(), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                                }

                                            }
                                        } else if (ConfigGetter.getAirdropChatMessageWorldID()) {
                                            if (ConfigGetter.getAirdropChatMessageExactLocation()) {
                                                if (ConfigGetter.getUseLangConfig()) {
                                                    server.getPlayerList().sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropMessage(), String.valueOf(world.provider.getDimension()), pos.getX(), pos.getZ()));
                                                } else {
                                                    server.getPlayerList().sendMessage(new TextComponentTranslation("airdrop.message", String.valueOf(world.provider.getDimension()), pos.getX(), pos.getZ()));
                                                }
                                            } else {
                                                if (ConfigGetter.getUseLangConfig()) {
                                                    server.getPlayerList().sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropMessage(), String.valueOf(world.provider.getDimension()), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                                } else {
                                                    server.getPlayerList().sendMessage(new TextComponentTranslation("airdrop.message", String.valueOf(world.provider.getDimension()), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                                }
                                            }
                                        }
                                    } else {
                                        if (ConfigGetter.getAirdropChatMessageExactLocation()) {
                                            if (ConfigGetter.getUseLangConfig()) {
                                                server.getPlayerList().sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropOldSchoolMessage(), pos.getX(), pos.getZ()));
                                            } else {
                                                server.getPlayerList().sendMessage(new TextComponentTranslation("airdrop.message", pos.getX(), pos.getZ()));
                                            }
                                        } else {
                                            if (ConfigGetter.getUseLangConfig()) {
                                                server.getPlayerList().sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropOldSchoolMessage(), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                            } else {
                                                server.getPlayerList().sendMessage(new TextComponentTranslation("airdrop.message", pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                            }
                                        }
                                    }
                                }
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

                    } else if (!iep.hasHorde(world) && GenericHordeUtils.isGenericHorde(world)) {

                        CitySavedData csd = CitySavedData.get(world);
                        CityData city = csd.getClosestCity(new BlockPos(player), 100.0);
                        GenericHorde horde = new GenericHorde(new BlockPos(player), world, player);

                        if (city != null && city.getZombieLevel() > 0) {
                            city.setZombieLevel(city.getZombieLevel() - horde.waves * horde.getZombiesInWave());
                        }

                        horde.addTarget(playerMP);
                        horde.start();
                        iep.setHorde(day);

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

    /**
     * @author X_Niter
     * @reason Thirst and Stamina configurable changes
     */
    @Overwrite
    public static void handleExtendedPlayer(EntityPlayer player, World world, IExtendedPlayer extendedPlayer) {
        if (!world.isRemote) {
            if (world.getDifficulty() == EnumDifficulty.PEACEFUL) {
                extendedPlayer.setThirst(1000);
                extendedPlayer.setStamina(1000);
                extendedPlayer.setDrinkCounter(0);
            } else {
                if (ModConfig.players.thirstSystem) {
                    PotionEffect effect;
                    if (extendedPlayer.getDrinkCounter() >= 20) {
                        extendedPlayer.setDrinkCounter(0);
                        extendedPlayer.addThirst(35);
                        extendedPlayer.addStamina(20);
                        player.removePotionEffect(Potions.thirst);
                        if (world.rand.nextInt(10) == 0) {
                            effect = new PotionEffect(Potions.dysentery, world.rand.nextInt(4000) + 18000, 0, false, false);
                            effect.setCurativeItems(new ArrayList<>());
                            player.addPotionEffect(effect);
                        }
                    } else if (extendedPlayer.getDrinkCounter() > 0) {
                        extendedPlayer.setDrinkCounter(extendedPlayer.getDrinkCounter() - 1);
                    }

                    if (extendedPlayer.getThirst() > 0 && world.rand.nextInt(ConfigGetter.getThirstDecreaseSpeed()) == 0) {
                        extendedPlayer.consumeThirst(1);
                    }

                    if (extendedPlayer.getThirst() <= 0) {
                        effect = new PotionEffect(Potions.thirst, 4, 4, false, false);
                        effect.setCurativeItems(new ArrayList<>());
                        player.addPotionEffect(effect);
                    }
                }

                if (ModConfig.players.staminaSystem) {
                    if (player.isSprinting()) {
                        if (ModConfig.players.staminaSystem && extendedPlayer.getStamina() > 0) {
                            if (world.rand.nextInt(ConfigGetter.getStaminaDecreaseSpeed()) == 0) {
                                extendedPlayer.consumeStamina(2);
                            }

                            if (ModConfig.players.thirstSystem && world.rand.nextInt(ConfigGetter.getThirstDecreaseSpeed()) == 0) {
                                extendedPlayer.consumeThirst(1);
                            }
                        }
                    } else if ((extendedPlayer.getThirst() >= 100 || !ModConfig.players.thirstSystem) && world.rand.nextInt(8) == 0 && (double)(player.distanceWalkedModified - player.prevDistanceWalkedModified) <= 0.05) {
                        extendedPlayer.addStamina(1);
                    }

                    if (extendedPlayer.getStamina() <= 0) {
                        player.setSprinting(false);
                    }
                }

            }
        }
    }
}

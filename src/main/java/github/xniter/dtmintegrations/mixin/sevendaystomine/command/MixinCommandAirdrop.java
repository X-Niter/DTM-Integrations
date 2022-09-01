package github.xniter.dtmintegrations.mixin.sevendaystomine.command;

import github.xniter.dtmintegrations.handlers.SoundHandler;
import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import nuparu.sevendaystomine.command.CommandAirdrop;
import nuparu.sevendaystomine.entity.EntityAirdrop;
import nuparu.sevendaystomine.events.LoudSoundEvent;
import nuparu.sevendaystomine.util.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin({CommandAirdrop.class})
public class MixinCommandAirdrop{

    /**
     * @author X_Niter
     * @reason Modifying the AirDrop method for the Command to match the new way it's handles for natural spawning.
     */
    @Overwrite
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();
        if (!world.isRemote) {
            BlockPos pos = Utils.getAirdropPos(world);
            if (args.length == 3) {
                try {
                    pos = CommandBase.parseBlockPos(sender, args, 0, true);
                } catch (NumberInvalidException var7) {
                    var7.printStackTrace();
                }
            }

            if (args.length == 0 || args.length == 3) {
                EntityAirdrop e = new EntityAirdrop(world, world.getSpawnPoint().up(255));
                world.spawnEntity(e);
                e.setPosition(pos.getX(), pos.getY(), pos.getZ());

                if (ConfigGetter.getAirdropSoundFXEnabled()) {
                    new LoudSoundEvent(world, e.getPosition(), SoundHandler.AIRDROP_EVENT, 2.0F, SoundCategory.AMBIENT);
                }

                Random rand = new Random();
                if (ConfigGetter.getAirdropChatMessageEnabled()) {
                    if (ConfigGetter.getAirdropChatMessageWorldLocation()) {
                        if (ConfigGetter.getAirdropChatMessageWorldName()) {
                            if (ConfigGetter.getAirdropChatMessageExactLocation()) {
                                if (ConfigGetter.getUseLangConfig()) {
                                    sender.sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropMessage(), world.getWorldInfo().getWorldName(), pos.getX(), pos.getZ()));
                                } else {
                                    sender.sendMessage(new TextComponentTranslation("airdrop.message", world.getWorldInfo().getWorldName(), pos.getX(), pos.getZ()));
                                }
                            } else {
                                if (ConfigGetter.getUseLangConfig()) {
                                    sender.sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropMessage(), world.getWorldInfo().getWorldName(), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                } else {
                                    sender.sendMessage(new TextComponentTranslation("airdrop.message", world.getWorldInfo().getWorldName(), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                }

                            }
                        } else if (ConfigGetter.getAirdropChatMessageWorldID()) {
                            if (ConfigGetter.getAirdropChatMessageExactLocation()) {
                                if (ConfigGetter.getUseLangConfig()) {
                                    sender.sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropMessage(), String.valueOf(world.provider.getDimension()), pos.getX(), pos.getZ()));
                                } else {
                                    sender.sendMessage(new TextComponentTranslation("airdrop.message", String.valueOf(world.provider.getDimension()), pos.getX(), pos.getZ()));
                                }
                            } else {
                                if (ConfigGetter.getUseLangConfig()) {
                                    sender.sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropMessage(), String.valueOf(world.provider.getDimension()), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                } else {
                                    sender.sendMessage(new TextComponentTranslation("airdrop.message", String.valueOf(world.provider.getDimension()), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                                }
                            }
                        }
                    } else {
                        if (ConfigGetter.getAirdropChatMessageExactLocation()) {
                            if (ConfigGetter.getUseLangConfig()) {
                                sender.sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropOldSchoolMessage(), pos.getX(), pos.getZ()));
                            } else {
                                sender.sendMessage(new TextComponentTranslation("airdrop.message", pos.getX(), pos.getZ()));
                            }
                        } else {
                            if (ConfigGetter.getUseLangConfig()) {
                                sender.sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropOldSchoolMessage(), pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                            } else {
                                sender.sendMessage(new TextComponentTranslation("airdrop.message", pos.getX() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), pos.getZ() + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation())));
                            }
                        }
                    }
                }
            }
        }
    }
}

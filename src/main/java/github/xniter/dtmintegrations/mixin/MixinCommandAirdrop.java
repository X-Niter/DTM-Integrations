package github.xniter.dtmintegrations.mixin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import nuparu.sevendaystomine.command.CommandAirdrop;
import nuparu.sevendaystomine.entity.EntityAirdrop;
import nuparu.sevendaystomine.util.MathUtils;
import nuparu.sevendaystomine.util.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin({CommandAirdrop.class})
public class MixinCommandAirdrop implements ILateMixinLoader {

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
                Random rand = new Random();
                sender.sendMessage(new TextComponentTranslation("airdrop.message", world.getWorldInfo().getWorldName(), pos.getX() + rand.nextInt(32) - rand.nextInt(32), pos.getZ() + rand.nextInt(32) - rand.nextInt(32)));
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

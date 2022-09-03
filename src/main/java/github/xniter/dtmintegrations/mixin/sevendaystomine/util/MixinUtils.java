package github.xniter.dtmintegrations.mixin.sevendaystomine.util;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nuparu.sevendaystomine.util.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.Random;

@Mixin({Utils.class})
public class MixinUtils {


    /**
     * @author X_Niter
     * @reason Airdrop position changes
     */
    @Overwrite(remap = false)
    public static BlockPos getAirdropPos(World world) {
        List<EntityPlayer> players = world.playerEntities;
        double xSum = 0.0;
        double zSum = 0.0;
        if (players.size() >= 1) {
            Random random = new Random();
            EntityPlayer player = players.get(random.nextInt(players.size()));
            xSum += player.posX;
            zSum += player.posZ;

            double angle = 6.283185307179586 * world.rand.nextDouble();
            double dist = random.nextInt(ConfigGetter.getAirdropDistanceFromPlayer()) + world.rand.nextDouble() * random.nextInt(ConfigGetter.getAirdropDistanceFromPlayer());
            double x = xSum / players.size() + dist * Math.cos(angle);
            double z = zSum / players.size() + dist * Math.sin(angle);
            return new BlockPos(x, ConfigGetter.getAirdropMaxHeight(), z);
        }
        return null;
    }

    /**
     * @author X_Niter
     * @reason Airdrop start point changes
     */
    @Overwrite(remap = false)
    public static BlockPos getAirDropStartPoint(World world, BlockPos centerPoint) {
        List<EntityPlayer> players = world.playerEntities;
        if (players.size() > 0) {

            BlockPos theMostDistant = null;
            double lastDistance = Double.MAX_VALUE;
            if (players.size() == 1) {
                EntityPlayer player = players.get(0);
                theMostDistant = new BlockPos(player.posX + world.rand.nextDouble() * 128.0, ConfigGetter.getAirdropMaxHeight(), player.posZ + world.rand.nextDouble() * 128.0);
                lastDistance = centerPoint.distanceSq(theMostDistant);
            }

            if (theMostDistant == null) {

                for (EntityPlayer player : players) {
                    BlockPos bp = new BlockPos(player.posX, ConfigGetter.getAirdropMaxHeight(), player.posZ);
                    double dist = bp.distanceSq(centerPoint);
                    if (dist < lastDistance) {
                        lastDistance = dist;
                        theMostDistant = bp;
                    }
                }
            }

            double x = 0.0;
            double z = 0.0;
            double d = (lastDistance + 32.0) / lastDistance;
            x = (1.0 - d) * (double)centerPoint.getX() + d * (double)theMostDistant.getX();
            z = (1.0 - d) * (double)centerPoint.getZ() + d * (double)theMostDistant.getY();
            return new BlockPos(x, ConfigGetter.getAirdropMaxHeight(), z);
        }
        return null;
    }
}

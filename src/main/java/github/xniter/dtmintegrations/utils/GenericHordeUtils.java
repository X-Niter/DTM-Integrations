package github.xniter.dtmintegrations.utils;

import net.minecraft.world.World;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.util.Utils;

import static nuparu.sevendaystomine.util.Utils.getDay;

public class GenericHordeUtils {

    public GenericHordeUtils() {

    }

    public static boolean isGenericHorde(World world) {

        return ModConfig.world.genericHordeChance >= 0.1 && getDay(world) % (world.rand.nextDouble() + ModConfig.world.genericHordeChance) == 0 && !Utils.isBloodmoon(world) && !Utils.isWolfHorde(world);

    }
}

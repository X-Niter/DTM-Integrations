package github.xniter.dtmintegrations.handlers.events;

import github.xniter.dtmintegrations.handlers.ResourceBoolArrayHandler;
import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nuparu.sevendaystomine.util.Utils;

public class HordeSpawnEvent extends ResourceBoolArrayHandler {

    @SubscribeEvent
    public void entityHordeSpawnEvent(LivingSpawnEvent event) {
        EntityLiving e = (EntityLiving) event.getEntityLiving();
        World world = event.getWorld();

        if (ConfigGetter.getFocusedBloodMoon() && Utils.isBloodmoon(world)) {
            if (!isAllowedBloodMoonSpawn(e)) {
                e.preventEntitySpawning = true;
                event.setResult(Event.Result.DENY);
                e.setDead();
                event.getEntity().getEntityWorld().removeEntity(e);

            }
        }

        if (ConfigGetter.getFocusedWolfHorde() && Utils.isWolfHorde(world)) {
            if (!isAllowedWolfHordeSpawn(e)) {
                e.preventEntitySpawning = true;
                event.setResult(Event.Result.DENY);
                e.setDead();
                event.getEntity().getEntityWorld().removeEntity(e);

            }
        }
    }
}

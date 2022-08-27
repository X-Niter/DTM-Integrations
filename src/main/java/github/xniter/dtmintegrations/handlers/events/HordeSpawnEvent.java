package github.xniter.dtmintegrations.handlers.events;

import github.xniter.dtmintegrations.handlers.AbstractVanillaHandler;
import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nuparu.sevendaystomine.entity.EntityZombieBase;
import nuparu.sevendaystomine.util.Utils;

public class HordeSpawnEvent extends AbstractVanillaHandler {

    @SubscribeEvent
    public void entityHordeSpawnEvent(LivingSpawnEvent event) {
        EntityLiving e = (EntityLiving) event.getEntityLiving();
        World world = event.getWorld();

        if (!(e instanceof EntityZombieBase)) {

            if (Utils.isBloodmoon(world)) {
                if (ConfigGetter.getFocusedBloodMoon()) {
                    e.preventEntitySpawning = true;
                    event.setResult(Event.Result.DENY);
                } else {
                    if (ConfigGetter.getForcefulFocusedBloodMoon()) {
                        e.preventEntitySpawning = true;
                        event.setResult(Event.Result.DENY);
                        e.setDead();
                        event.getEntity().getEntityWorld().removeEntity(e);
                    }
                }
            }

            if (Utils.isWolfHorde(world)) {
                if (ConfigGetter.getFocusedWolfHorde()) {
                    e.preventEntitySpawning = true;
                    event.setResult(Event.Result.DENY);
                } else {
                    if (ConfigGetter.getForcefulFocusedWolfHorde()) {
                        e.preventEntitySpawning = true;
                        event.setResult(Event.Result.DENY);
                        e.setDead();
                        event.getEntity().getEntityWorld().removeEntity(e);
                    }
                }
            }
        }
    }
}

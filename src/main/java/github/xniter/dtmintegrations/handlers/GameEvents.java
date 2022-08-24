package github.xniter.dtmintegrations.handlers;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nuparu.sevendaystomine.entity.EntityZombieBase;
import nuparu.sevendaystomine.util.Utils;

public class GameEvents {


    @SubscribeEvent
    public void entityHordeSpawnEvent(LivingSpawnEvent event) {
        EntityLiving e = (EntityLiving) event.getEntityLiving();
        World world = event.getWorld();

        if (!(e instanceof EntityZombieBase)) {


           if (ConfigGetter.getFocusedBloodMoon() && Utils.isBloodmoon(event.getWorld())) {
               event.setCanceled(e.forceSpawn);
               event.setCanceled(e.addedToChunk);
               e.setDead();

               if (!e.isDead || e.isAddedToWorld() || e.forceSpawn) {
                   world.removeEntity(e);
               }
           }

           if (ConfigGetter.getFocusedWolfHorde() && Utils.isWolfHorde(event.getWorld())) {
               event.setCanceled(e.forceSpawn);
               event.setCanceled(e.addedToChunk);
               e.setDead();

               if (!e.isDead || e.isAddedToWorld() || e.forceSpawn) {
                   world.removeEntity(e);
               }
           }

        }


        if (e.forceSpawn) {
            world.removeEntity(e);
        }
    }
}

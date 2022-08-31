package github.xniter.dtmintegrations.handlers.network.proxy;

import github.xniter.dtmintegrations.DTMIntegrations;
import github.xniter.dtmintegrations.features.FMLAutoConfig;
import github.xniter.dtmintegrations.handlers.SoundHandler;
import github.xniter.dtmintegrations.handlers.events.AttackEvent;
import github.xniter.dtmintegrations.handlers.events.UseHoeEvent;
import github.xniter.dtmintegrations.handlers.events.SpawnDuringHordeEvent;
import github.xniter.dtmintegrations.handlers.events.PlayerBreakEvent;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void preInit() {
        DTMIntegrations.LOG.debug("Skipping Initializing DTMIntegrations packet handler");
        DTMIntegrations.LOG.debug("No need for it");
       // PacketHandler.init();
    }

    public void init() {
        DTMIntegrations.LOG.debug("Adding various handlers for DTMIntegrations");
        MinecraftForge.EVENT_BUS.register(new AttackEvent());
        MinecraftForge.EVENT_BUS.register(new UseHoeEvent());
        MinecraftForge.EVENT_BUS.register(new SpawnDuringHordeEvent());
        MinecraftForge.EVENT_BUS.register(new PlayerBreakEvent());
        MinecraftForge.EVENT_BUS.register(FMLAutoConfig.class);
        MinecraftForge.EVENT_BUS.register(new SoundHandler.SoundRegisterListener());
    }
}

package github.xniter.dtmintegrations.handlers.network.proxy;

import github.xniter.dtmintegrations.handlers.events.ToolTipEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());
    }
}

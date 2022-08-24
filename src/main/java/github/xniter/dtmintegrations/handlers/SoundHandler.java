package github.xniter.dtmintegrations.handlers;

import github.xniter.dtmintegrations.DTMIntegrations;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(DTMIntegrations.MOD_ID)
public class SoundHandler {

    @GameRegistry.ObjectHolder("dtmi.airdrop.event")
    public static final SoundEvent AIRDROP_EVENT = addSoundsToRegistry("airdrop.event");


    public SoundHandler() {}

    private static SoundEvent addSoundsToRegistry(String soundId) {
        ResourceLocation soundLocation = new ResourceLocation(DTMIntegrations.MOD_ID, soundId);
        SoundEvent soundEvent = new SoundEvent(soundLocation);
        soundEvent.setRegistryName(soundLocation);
        return soundEvent;
    }

    public static class SoundRegisterListener {
        @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
        public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().registerAll(SoundHandler.AIRDROP_EVENT);
        }
    }
}

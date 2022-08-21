package github.xniter.dtmintegrations.mixin;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import github.xniter.dtmintegrations.handlers.config.ConfigHandler;
import nuparu.sevendaystomine.world.gen.city.City;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

@Mixin({City.class})
public class MixinCity implements ILateMixinLoader {



    @ModifyConstant(method = "prepareStreets", constant = @Constant(intValue = 8192), remap = false)
    public int prepareStreets(int constant) {

        int genConfig = ConfigGetter.getStreetGenAttempts();

        if (genConfig < 1) {
            genConfig = 1;
        }
        if (genConfig > 100) {
            genConfig = 100;
        }

        return genConfig;
    }

    @ModifyConstant(method = "addIteration", constant = @Constant(intValue = 16), remap = false)
    public int addIteration(int constant) {

        int genConfig = ConfigGetter.getStreetGenAttempts();

        if (genConfig < 1) {
            genConfig = 1;
        }
        if (genConfig > 100) {
            genConfig = 100;
        }

        return genConfig;
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

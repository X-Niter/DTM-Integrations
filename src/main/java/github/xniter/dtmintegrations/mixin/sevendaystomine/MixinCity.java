package github.xniter.dtmintegrations.mixin.sevendaystomine;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import nuparu.sevendaystomine.world.gen.city.City;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin({City.class})
public class MixinCity {



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
}

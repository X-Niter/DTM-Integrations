package github.xniter.dtmintegrations.mixin.sevendaystomine.world.gen.city;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import nuparu.sevendaystomine.world.gen.city.City;
import nuparu.sevendaystomine.world.gen.city.EnumCityType;
import nuparu.sevendaystomine.world.gen.city.Street;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import zone.rong.mixinextras.injector.WrapWithCondition;

import java.util.ArrayList;
import java.util.List;

@Mixin({City.class})
public abstract class MixinCity  {

    @Shadow
    private List<Street> streets = new ArrayList<>();


    @Shadow
    public EnumCityType type;

    @Shadow public abstract void generate();

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


    @WrapWithCondition(method = "generate", at = @At(value = "INVOKE", target = "Lnuparu/sevendaystomine/world/gen/city/Street;generateBuildings()V"), remap = false)
    public boolean generateCity() {
        return !ConfigGetter.getDisableAllStructures();
    }
}

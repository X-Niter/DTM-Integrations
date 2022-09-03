package github.xniter.dtmintegrations.mixin.sevendaystomine.world.gen.city;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nuparu.sevendaystomine.util.MathUtils;
import nuparu.sevendaystomine.util.Utils;
import nuparu.sevendaystomine.world.gen.city.City;
import nuparu.sevendaystomine.world.gen.city.CitySavedData;
import nuparu.sevendaystomine.world.gen.city.EnumCityType;
import nuparu.sevendaystomine.world.gen.city.Street;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin({City.class})
public abstract class MixinCity  {

    @Shadow
    private List<Street> streets = new ArrayList<>();


    @Shadow
    public EnumCityType type;

    @Shadow
    private boolean allStreetsFound = false;

    @Shadow
    public String name = "Genericville";

    @Shadow
    public int population;

    @Shadow
    public int roadsLimit;

    @Shadow
    public Random rand;

    @Shadow
    public World world;

    @Shadow
    public BlockPos start;

    @Shadow public abstract void generate();

    @ModifyConstant(method = "prepareStreets", constant = @Constant(intValue = 8192), remap = false)
    public int DTMIMixin_prepareStreets(int constant) {

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
    public int DTMIMixin_addIteration(int constant) {

        int genConfig = ConfigGetter.getStreetGenAttempts();

        if (genConfig < 1) {
            genConfig = 1;
        }
        if (genConfig > 100) {
            genConfig = 100;
        }

        return genConfig;
    }

    /**
     * @author X_Niter
     * @reason If all structures are disabled, then do not generate cities
     */
    @Overwrite(remap = false)
    public void startCityGen() {
        if (!ConfigGetter.getDisableAllStructures()) {
            long timeStamp = System.currentTimeMillis();
            if (!this.areAllStreetsFound()) {
                this.prepareStreets();
            }

            if (this.name.equals("Caprica City")) {
                this.population = 50298;
            } else {
                this.population = this.roadsLimit * MathUtils.getIntInRange(this.rand, 128, 1024) * (this.type == EnumCityType.METROPOLIS ? 32 : (this.type == EnumCityType.CITY ? 16 : (this.type == EnumCityType.TOWN ? 2 : 1)));
            }

            this.generate();
            CitySavedData.get(this.world).addCity((City) (Object) this);
            if (!this.world.isRemote) {
                Utils.getLogger().info(this.name + " generated at " + this.start.getX() + " " + this.start.getZ() + " within " + (System.currentTimeMillis() - timeStamp) + "ms with " + this.streets.size() + " streets");
            }
        }
    }

    @Shadow
    public boolean areAllStreetsFound() {
        return this.allStreetsFound;
    }

    @Shadow
    public void prepareStreets() {}


//    @WrapWithCondition(method = "generate", at = @At(value = "INVOKE", target = "Lnuparu/sevendaystomine/world/gen/city/Street;generateBuildings()V"), remap = false)
//    private boolean generateCity(Street instance) {
//        return !ConfigGetter.getDisableAllStructures();
//    }
}

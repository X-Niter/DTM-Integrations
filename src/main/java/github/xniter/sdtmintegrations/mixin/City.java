package github.xniter.sdtmintegrations.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.world.gen.city.EnumCityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import scala.tools.asm.Opcodes;

import java.lang.annotation.Target;
import java.util.Random;

@Mixin(nuparu.sevendaystomine.world.gen.city.City.class)
public class City {

    @Shadow public int roadsLimit;

    @Redirect(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnuparu/sevendaystomine/world/gen/city/EnumCityType;Ljava/util/Random;)V", at = @At(value = "FIELD", target = "Lnuparu/sevendaystomine/world/gen/city/City;roadsLimit:I", opcode = Opcodes.PUTFIELD))
    private void roadsLimit(nuparu.sevendaystomine.world.gen.city.City instance, int value) {
        Random rand = new Random();
        instance.roadsLimit = MathUtils.getIntInRange(rand, 1, ModConfig.worldGen.maxCitySize);
    }


    /**
     * @author X_Niter
     * @reason Remove rand at the end of the return to return the next foundCity function for Block positioning
     */
    @Overwrite
    public static nuparu.sevendaystomine.world.gen.city.City foundCity(World world, ChunkPos pos, Random rand) {
        return nuparu.sevendaystomine.world.gen.city.City.foundCity(world, new BlockPos(pos.x * 16 + 8, 0, pos.z * 16 + 8));
    }
}

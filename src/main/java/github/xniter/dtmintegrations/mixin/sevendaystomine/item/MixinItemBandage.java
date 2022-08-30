package github.xniter.dtmintegrations.mixin.sevendaystomine.item;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.item.ItemStack;
import nuparu.sevendaystomine.item.ItemBandage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ItemBandage.class)
public class MixinItemBandage {

//    @Inject(method = "getMaxItemUseDuration", at = @At("HEAD"), cancellable = true)
//    public void getMaxItemUseDuration(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
//        cir.setReturnValue(ConfigGetter.getBandageUseTime() * 20);
//    }

    /**
     * @author X_Niter
     * @reason Injecting the return is being dumb, so for now, sense I don't expect anyone else to touch this,
     * I am overwriting this to allow changing the Bandage usage time.
     */
    @Overwrite
    public int getMaxItemUseDuration(ItemStack itemStack) {
        return ConfigGetter.getBandageUseTime() * 20;
    }

}

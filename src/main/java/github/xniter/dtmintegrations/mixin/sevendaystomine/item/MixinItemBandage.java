package github.xniter.dtmintegrations.mixin.sevendaystomine.item;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.item.ItemStack;
import nuparu.sevendaystomine.item.ItemBandage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ItemBandage.class, remap = false)
public class MixinItemBandage {

    @ModifyConstant(method = "getMaxItemUseDuration", constant = @Constant(intValue = 82000), remap = false)
    public int getMaxItemUseDuration(int constant) {
        return ConfigGetter.getBandageUseTime() * 20;
    }
}

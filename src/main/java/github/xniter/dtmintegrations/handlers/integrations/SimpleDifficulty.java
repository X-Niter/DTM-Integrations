package github.xniter.dtmintegrations.handlers.integrations;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.config.QuickConfig;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.charles445.simpledifficulty.api.thirst.ThirstUtil;
import com.charles445.simpledifficulty.item.ItemDrinkBase;
import github.xniter.dtmintegrations.utils.ModGetter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import toughasnails.api.TANCapabilities;
import toughasnails.api.stat.capability.IThirst;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.thirst.ThirstHandler;

public class SimpleDifficulty {

    public SimpleDifficulty(int thirst, float saturation, float stamina) {
        this.simpleDifficultyTakeDrink(thirst, saturation, stamina);
    }


    public void simpleDifficultyTakeDrink(int thirst, float saturation, float stamina) {
        EntityPlayer player = Minecraft.getMinecraft().player;

        if (ModGetter.isSimpleDifficultyLoaded()) {
            IThirstCapability capability = SDCapabilities.getThirstData(player);
            if (capability.isThirsty() || !QuickConfig.isThirstEnabled()) {
                ThirstUtil.takeDrink(player, thirst, saturation, stamina);
            }
        }
    }



}

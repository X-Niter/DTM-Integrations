package github.xniter.dtmintegrations.handlers.integrations;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.config.QuickConfig;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.charles445.simpledifficulty.api.thirst.ThirstUtil;
import github.xniter.dtmintegrations.utils.ModGetter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

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

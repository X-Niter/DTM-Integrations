package github.xniter.dtmintegrations.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import toughasnails.api.TANCapabilities;
import toughasnails.api.stat.capability.IThirst;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.thirst.ThirstHandler;

public class TANItemDrink {

    public TANItemDrink(int thirst, float stamina) {
        this.tanAddStats(thirst, stamina);
    }


    public void tanAddStats(int thirst, float stamina) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        IThirst iThirst = ThirstHelper.getThirstData(player) != null ? ThirstHelper.getThirstData(player) : null;
        ThirstHandler thirstStats = (ThirstHandler) player.getCapability(TANCapabilities.THIRST, (EnumFacing) null) != null ? (ThirstHandler) player.getCapability(TANCapabilities.THIRST, (EnumFacing) null) : null;

        if (thirstStats != null) {
            thirstStats.addStats(thirst, stamina);
        } else {
            if (iThirst != null) {
                iThirst.addStats(thirst, stamina);
            }
        }
    }

}

package github.xniter.dtmintegrations.handlers.events;

import github.xniter.dtmintegrations.handlers.SoundHandler;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nuparu.sevendaystomine.entity.EntityAirdrop;
import nuparu.sevendaystomine.util.MathUtils;

@SideOnly(Side.CLIENT)
public class AirdropSoundEvent extends MovingSound {
    private final EntityAirdrop airdrop;
    private float distance = 0.0F;

    public long time;

    public AirdropSoundEvent(EntityAirdrop airdrop) {
        super(SoundHandler.AIRDROP_EVENT, SoundCategory.AMBIENT);
        this.airdrop = airdrop;
        this.repeat = false;
        this.repeatDelay = 0;
    }

    public void update() {

        if (this.airdrop != null && !this.airdrop.isDead) {
            this.pitch = (float) (1.0 + MathUtils.getSpeedKilometersPerHour(this.airdrop) / 100.0);
            this.xPosF = (float) this.airdrop.posX;
            this.yPosF = (float) this.airdrop.posY;
            this.zPosF = (float) this.airdrop.posZ;
            this.distance = 0.0F;
            this.volume = 2.0F;
        } else {
            this.donePlaying = true;
        }
    }
}

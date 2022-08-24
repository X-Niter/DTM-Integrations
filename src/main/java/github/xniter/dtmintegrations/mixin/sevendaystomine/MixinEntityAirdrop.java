package github.xniter.dtmintegrations.mixin.sevendaystomine;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSmokeLarge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import nuparu.sevendaystomine.entity.EntityAirdrop;
import nuparu.sevendaystomine.inventory.itemhandler.AirdropInventoryHandler;
import nuparu.sevendaystomine.util.MathUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityAirdrop.class, remap = false)
public abstract class MixinEntityAirdrop extends Entity {

    @Shadow
    private ItemStackHandler inventory;

    @Shadow
    public long age;

    @Shadow
    @Final
    private static DataParameter<Boolean> LANDED;

    @Shadow
    @Final
    private static DataParameter<Integer> SMOKE_TIME;

    @Shadow
    @Final
    private static DataParameter<Integer> HEALTH;

    public MixinEntityAirdrop(World worldIn) {
        super(worldIn);
    }

    private static EntityAirdrop airDrop;

    private static boolean isSolidBlock = false;

    /**
     * @author X_Niter
     * @reason Airdrop Modifications
     */
    @Overwrite(remap = false)
    public void onUpdate() {
        super.onUpdate();
        ++this.age;
        this.ignoreFrustumCheck = true;
        BlockPos bP1 = this.getPosition().down(1);
        this.onGround = !this.world.isAirBlock(bP1);

        if (!this.getServer().getEntityWorld().getChunk(this.chunkCoordX, this.chunkCoordZ).isLoaded()) {
            this.getServer().getEntityWorld().getChunk(this.chunkCoordX, this.chunkCoordZ).markLoaded(true);
        }

        if (this.age >= 48000L) {
            this.setDead();

            MinecraftServer server = Minecraft.getMinecraft().world.getMinecraftServer();
            if (server != null && server.getPlayerList().getCurrentPlayerCount() != 0) {

                if (ConfigGetter.getUseLangConfig()) {
                    server.getPlayerList().sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropDespawnMessage(), world.getWorldInfo().getWorldName(), this.getPosition().getX(), this.getPosition().getZ()));
                } else {
                    server.getPlayerList().sendMessage(new TextComponentTranslation("airdrop.removed.message", world.getWorldInfo().getWorldName(), this.getPosition().getX(), this.getPosition().getZ()));
                }
            }
        }

        if (this.getLanded() && this.getSmokeTime() > 0) {
            for(int i = 0; i < this.world.rand.nextInt(4) + 1; ++i) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + (double)this.height, this.posZ, (double)MathUtils.getFloatInRange(-0.1F, 0.1F), (double)MathUtils.getFloatInRange(0.2F, 0.5F), (double)MathUtils.getFloatInRange(-0.1F, 0.1F));
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY + (double)this.height, this.posZ, (double)MathUtils.getFloatInRange(-0.1F, 0.1F), (double)MathUtils.getFloatInRange(0.2F, 0.5F), (double)MathUtils.getFloatInRange(-0.1F, 0.1F));
            }

            this.setSmokeTime(this.getSmokeTime() - 1);
        }

        if (!this.world.isRemote) {
            if (!this.onGround) {
                if (!this.getLanded()) {
                    this.motionY = -ConfigGetter.getAirdropFallingSpeed() / 1.5;

                } else {
                    this.motionY = -ConfigGetter.getAirdropFallingSpeed() / 2;

                }

            } else {
                this.motionY = 0.0;

                if (!this.getLanded()) {
                    this.setSmokeTime(1200);
                    this.setLanded(true);
                }

            }

            BlockPos bP16 = this.getPosition().down(16);

            if (!this.world.isAirBlock(bP16)) {
                this.markVelocityChanged();
                this.onEntityUpdate();
                this.motionY = -0.06;
                isSolidBlock = true;
            }

            if (isSolidBlock) {
                this.motionY = -0.06;
            }

            if (this.onGround) {
                this.setPositionAndUpdate(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
                isSolidBlock = false;
            }

            for (int i = 0; i < (int)(10000/age); i++) {
                this.markVelocityChanged();
                this.onEntityUpdate();
                if (ConfigGetter.getAirdropGlowingEnabled()) {
                    this.glowing = true;
                }
            }

            if (this.addedToChunk || this.isAirBorne || this.isAddedToWorld()) {
                if (this.serverPosX != this.posX && this.serverPosZ != this.posZ) {

                    this.serverPosZ = (long) this.posZ;
                    this.serverPosX = (long) this.posX;
                }
                this.getServer().getEntityWorld().updateEntity(this);
            }


            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }
    }

    /**
     * @author X_Niter
     * @reason Render Changes
     */
    @Overwrite(remap = false)
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;

        if (Double.isNaN(d0))
        {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0 * (double)(this.getLanded() ? 1 : 6);
        return distance < d0 * d0;
    }

    @Shadow public boolean getLanded() {
        return (Boolean)this.dataManager.get(LANDED);
    }

    @Shadow public void setSmokeTime(int ticks) {
        this.dataManager.set(SMOKE_TIME, ticks);
    }

    @Shadow public void setLanded(boolean landed) {
        this.dataManager.set(LANDED, landed);
    }

    @Shadow public int getSmokeTime() {
        return (Integer)this.dataManager.get(SMOKE_TIME);
    }

    /**
     * @author X_Niter
     * @reason When the Airdrop is emptied of the loot, then delete the airdrop
     */
    @Overwrite(remap = false)
    public void onInventoryChanged(AirdropInventoryHandler airdropInventoryHandler) {
        for(int j = 0; j < this.inventory.getSlots(); ++j) {
            ItemStack stack = this.inventory.getStackInSlot(j);
            if (!stack.isEmpty()) {
                return;
            } else {
                if (this.onGround && this.getLanded()) {
                    this.setDead();
                    this.setAir(1);
                    this.getServer().getEntityWorld().removeEntity(this);
                    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 1, 1, 1, 1);
                }
            }
        }

        this.age = MathUtils.clamp(this.age, 42000L, 48000L);
    }
}

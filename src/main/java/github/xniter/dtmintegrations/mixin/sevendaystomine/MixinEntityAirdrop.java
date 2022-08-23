package github.xniter.dtmintegrations.mixin.sevendaystomine;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.block.BlockEventData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import nuparu.sevendaystomine.entity.EntityAirdrop;
import nuparu.sevendaystomine.entity.EntityBandit;
import nuparu.sevendaystomine.inventory.itemhandler.AirdropInventoryHandler;
import nuparu.sevendaystomine.util.MathUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.ChoiceFormat;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @author X_Niter
     * @reason Airdrop Modifications
     */
    @Overwrite(remap = false)
    public void onUpdate() {
        super.onUpdate();
        ++this.age;
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
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + (double)this.height, this.posZ, (double)MathUtils.getFloatInRange(-0.1F, 0.1F), (double)MathUtils.getFloatInRange(0.2F, 0.5F), (double)MathUtils.getFloatInRange(-0.1F, 0.1F), new int[0]);
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY + (double)this.height, this.posZ, (double)MathUtils.getFloatInRange(-0.1F, 0.1F), (double)MathUtils.getFloatInRange(0.2F, 0.5F), (double)MathUtils.getFloatInRange(-0.1F, 0.1F), new int[0]);
            }

            this.setSmokeTime(this.getSmokeTime() - 1);
        }

        if (!this.world.isRemote) {


            // If it's not on ground
            if (!this.onGround) {

                // If it's not landed
                if (!this.getLanded()) {
                    // Not landed so fall
                    this.motionY = -ConfigGetter.getAirdropFallingSpeed();

                    if (ConfigGetter.getAirdropRealisticFalling()) {
                        double max = 1.5;
                        double min = -1.5;
                        double range = max - min;
                        double scaled = rand.nextDouble() * range;

                        final double motionZX = scaled + min == 0 ? 0.5 + rand.nextDouble() - rand.nextDouble() : scaled + min;

                        this.motionX = motionZX;
                        this.motionZ = motionZX;
                    }

                } else {
                    // It's not on ground, but might have landed on itself or somewhere odd
                    this.motionY = -ConfigGetter.getAirdropFallingSpeed();

                    // Not on ground, but landed on something it should not, so we drop it to get an actual ground drop
                    this.setPosition(this.posX + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()), ConfigGetter.getAirdropMaxHeight(), this.posZ + rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()) - rand.nextInt(ConfigGetter.getAirdropChatMessageGeneralLocation()));
                }

                this.markVelocityChanged();

            } else {

                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
                this.markVelocityChanged();

                if (!this.getLanded() && this.motionY == 0) {
                    this.motionY = -0.06;
                    this.markVelocityChanged();
                    this.setSmokeTime(ConfigGetter.getAirdropSmokeTime() * 20);
                    this.setLanded(true);
                }
            }


            // END REWRITE

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }

        if (this.isAirBorne || !this.isDead || this.isAddedToWorld()) {
            if (!Minecraft.getMinecraft().world.getChunk(this.getPosition().up()).isLoaded()) {
                Minecraft.getMinecraft().world.getChunk(this.getPosition().up()).markLoaded(true);
            }
        }
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

//    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnuparu/sevendaystomine/entity/EntityAirdrop;setDead()V", shift = At.Shift.AFTER))
//    public void DTMI_onUpdate_AirDropRemoved(CallbackInfo ci) {
//
//        MinecraftServer server = Minecraft.getMinecraft().world.getMinecraftServer();
//        World world = Minecraft.getMinecraft().world;
//        List<EntityAirdrop> airDrop = world.entit
//
//        if (this.age >= 48000L && server != null && server.getPlayerList().getCurrentPlayerCount() != 0) {
//
//            if (ConfigGetter.getUseLangConfig()) {
//
//                server.getPlayerList().sendMessage(new TextComponentTranslation(ConfigGetter.getAirdropDespawnMessage(), world.getWorldInfo().getWorldName(), this.getPosition().getX(), this.getPosition().getZ()));
//            } else {
//                server.getPlayerList().sendMessage(new TextComponentTranslation("airdrop.removed.message", world.getWorldInfo().getWorldName(), this.getPosition().getX(), this.getPosition().getZ()));
//            }
//        }
//
//    }

//    @ModifyConstant(method = "onUpdate", constant = @Constant(doubleValue = -0.0625))
//    public double DTMI_onUpdate_FallSpeed_Top(double constant) {
//
//        double CONFIG_FALL_SPEED = ConfigGetter.getAirdropFallingSpeed();
//        double CFSX2 = CONFIG_FALL_SPEED * 2;
//        double fallSpeed = CONFIG_FALL_SPEED - CFSX2;
//
//        return ConfigGetter.getAirdropFallingSpeed() == 0 ? -0.1 : -CONFIG_FALL_SPEED;
//    }
//
//    @ModifyConstant(method = "onUpdate", constant = @Constant(doubleValue = -0.1911))
//    public double DTMI_onUpdate_FallSpeed_Bottom(double constant) {
//
//        double CONFIG_FALL_SPEED = ConfigGetter.getAirdropFallingSpeed();
//        double CFSX2 = CONFIG_FALL_SPEED * 2;
//        double fallSpeed = CONFIG_FALL_SPEED - CFSX2;
//
//        return ConfigGetter.getAirdropFallingSpeed() == 0 ? -0.1 : -CONFIG_FALL_SPEED;
//    }

//    @Inject(method = "onUpdate", at = @At(value = "TAIL"))
//    public void DTMI_onUpdate_AirDropChunkLoading(CallbackInfo ci) {
//        if ((Entity)this.age > 1 || !this.isDead || this.isAddedToWorld()) {
//            if (!Minecraft.getMinecraft().world.getChunk(this.getPosition().up()).isLoaded()) {
//                Minecraft.getMinecraft().world.getChunk(this.getPosition().up()).markLoaded(true);
//            }
//        }
//
//    }


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
                }
            }
        }

        this.age = MathUtils.clamp(this.age, 42000L, 48000L);
    }
}

package github.xniter.dtmintegrations.mixin.sevendaystomine.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nuparu.sevendaystomine.SevenDaysToMine;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.entity.EntityZombieBase;
import nuparu.sevendaystomine.events.LivingEventHandler;
import nuparu.sevendaystomine.potions.Potions;
import nuparu.sevendaystomine.util.DamageSources;
import nuparu.sevendaystomine.util.EnumModParticleType;
import nuparu.sevendaystomine.util.MathUtils;
import nuparu.sevendaystomine.util.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;

@Mixin(value = LivingEventHandler.class, remap = false)
public class MixinLivingEventHandler {

    /**
     * @author X_Niter
     * @reason Blood changes
     */
    @SubscribeEvent
    @Overwrite
    public void onEntityAttack(LivingAttackEvent event) {
        DamageSource source = event.getSource();
        float amount = event.getAmount();
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.world;

        if (!entity.getIsInvulnerable()) {

            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.isCreative() || player.isSpectator()) {
                    return;
                }

                if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityZombieBase && world.rand.nextInt(ModConfig.players.infectionChanceModifier * (getArmorItemsCount(player) + 1)) == 0) {
                    Utils.infectPlayer(player, 0);
                }
            }

            if (source != DamageSource.DROWN
                    && source != DamageSource.OUT_OF_WORLD
                    && source != DamageSource.STARVE
                    && source != DamageSource.WITHER
                    && source != DamageSource.MAGIC
                    && source != DamageSources.alcoholPoison
                    && source != DamageSources.chlorinePoison
                    && source != DamageSources.thirst
                    && source != DamageSources.mercuryPoison) {
                if (!(entity instanceof AbstractSkeleton)
                        && !(entity instanceof EntitySquid)
                        && !(entity instanceof EntityGuardian)
                        && !(entity instanceof EntityWither)
                        && !(entity instanceof EntityDragon)
                        && !(entity instanceof EntityBlaze)
                        && !(entity instanceof EntityVex)
                        && !(entity instanceof EntitySlime)
                        && !(entity instanceof EntityGolem)
                        && !(entity instanceof EntityEnderman)
                        && !(entity instanceof EntityEndermite)) {
                    if (world.getDifficulty() != EnumDifficulty.PEACEFUL
                            && !world.isRemote && amount >= entity.getMaxHealth() / 100.0F * 20.0F && world.rand.nextInt(ModConfig.mobs.bleedingChanceModifier * (getArmorItemsCount(entity) + 1)) == 0) {
                        PotionEffect effect = new PotionEffect(Potions.bleeding, Integer.MAX_VALUE, 1, false, false);
                        effect.setCurativeItems(new ArrayList<>());
                        entity.addPotionEffect(effect);
                    }

                    if (amount > 0.1F) {
                        for (int i = 0; i < (int) Math.round(MathUtils.getDoubleInRange(1.0, 5.0) * (double) SevenDaysToMine.proxy.getParticleLevel()); ++i) {
                            double x = entity.posX + MathUtils.getDoubleInRange(-1.0, 1.0) * (double) entity.width;
                            double y = entity.posY + MathUtils.getDoubleInRange(0.0, 1.0) * (double) entity.height;
                            double z = entity.posZ + MathUtils.getDoubleInRange(-1.0, 1.0) * (double) entity.width;
                            SevenDaysToMine.proxy.spawnParticle(world, EnumModParticleType.BLOOD, x, y, z, MathUtils.getDoubleInRange(-1.0, 1.0) / 7.0, -0.5, MathUtils.getDoubleInRange(-1.0, 1.0) / 7.0);
                        }
                    }
                }
            }
        }
    }

    @Shadow
    public static int getArmorItemsCount(EntityLivingBase living) {
        return (living.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() ? 0 : 2) + (living.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty() ? 0 : 3) + (living.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty() ? 0 : 2) + (living.getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty() ? 0 : 1);
    }
}

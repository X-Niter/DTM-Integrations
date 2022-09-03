package github.xniter.dtmintegrations.mixin.sevendaystomine.client.gui;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nuparu.sevendaystomine.capability.CapabilityHelper;
import nuparu.sevendaystomine.capability.IExtendedPlayer;
import nuparu.sevendaystomine.client.gui.EnumHudPosition;
import nuparu.sevendaystomine.client.gui.GuiPlayerUI;
import nuparu.sevendaystomine.config.ModConfig;
import nuparu.sevendaystomine.entity.EntityMinibike;
import nuparu.sevendaystomine.item.ItemGun;
import nuparu.sevendaystomine.potions.Potions;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = GuiPlayerUI.class, remap = false)
public class MixinGuiPlayerUI {

    @Final
    @Shadow
    public static final ResourceLocation UI_TEX = new ResourceLocation("sevendaystomine", "textures/gui/hud.png");

    @Final
    @Shadow
    public static final ResourceLocation SCOPE_TEX = new ResourceLocation("sevendaystomine", "textures/misc/scope.png");

    @Shadow
    float minibikeFuelPrev = 0.0F;

    /**
     * @author X_Niter
     * @reason TAN Integration
     */
    @Overwrite
    @SubscribeEvent(
            priority = EventPriority.NORMAL
    )
    @SideOnly(Side.CLIENT)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent event) {
        if (!event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.player;
            if (player == null) {
                return;
            }

            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(770, 771);
            mc.renderEngine.bindTexture(UI_TEX);
            EnumHudPosition pos = ModConfig.client.hudPosition;
            ScaledResolution resolution = event.getResolution();
            double width = resolution.getScaledWidth();
            double height = resolution.getScaledHeight();
            boolean showFuel = player.getRidingEntity() instanceof EntityMinibike;
            int x = (int)(width * pos.getX()) + pos.getXOffset();
            int y = (int)(height * pos.getY()) + pos.getYOffset() + (pos.isTop() && showFuel ? 10 : 0);
            if (!player.isCreative() && !player.isSpectator() && mc.playerController.shouldDrawHUD()) {
                IExtendedPlayer extendedPlayer = CapabilityHelper.getExtendedPlayer(player);
                if (!ConfigGetter.getTanIntegration() && !ConfigGetter.getSimpleDifficultyIntegration()) {

                    if (ModConfig.players.thirstSystem) {
                        mc.ingameGUI.drawTexturedModalRect(x + 1, y - 9, 0, player.isPotionActive(Potions.dysentery) ? 29 : 8, (int) Math.floor((float) extendedPlayer.getThirst() / (10.0F * ((float) extendedPlayer.getMaximumThirst() / 780.0F))), 6);
                        mc.ingameGUI.drawTexturedModalRect(x, y - 10, 0, 0, 81, 8);
                    }

                    if (ModConfig.players.staminaSystem) {
                        mc.ingameGUI.drawTexturedModalRect(x + 1, y, 0, 15, (int) Math.floor((float) extendedPlayer.getStamina() / (10.0F * ((float) extendedPlayer.getMaximumStamina() / 780.0F))), 6);
                        mc.ingameGUI.drawTexturedModalRect(x, y - 1, 0, 0, 81, 8);
                    }
                }
            }

            if (showFuel) {
                EntityMinibike minibike = (EntityMinibike)player.getRidingEntity();
                float fuel = minibike.getFuel();
                mc.ingameGUI.drawTexturedModalRect(x + 1, y - 18, 0, 22, (int)Math.floor((fuel == 0.0F ? this.minibikeFuelPrev : fuel) / 64.10256F), 6);
                mc.ingameGUI.drawTexturedModalRect(x, y - 19, 0, 0, 81, 8);
                this.minibikeFuelPrev = minibike.getFuel();
            }

            ItemStack stack = player.getHeldItemMainhand();
            if (stack.isEmpty() || !(stack.getItem() instanceof ItemGun)) {
                stack = player.getHeldItemOffhand();
                if (stack.isEmpty() || !(stack.getItem() instanceof ItemGun)) {
                    GL11.glPopMatrix();
                    return;
                }
            }

            ItemGun gun = (ItemGun)stack.getItem();
            float factor = gun.getFOVFactor(stack);
            if (factor == 1.0F) {
                GL11.glPopMatrix();
                return;
            }

            if (mc.gameSettings.keyBindAttack.isKeyDown() && gun.getScoped()) {
                int w = resolution.getScaledWidth();
                int h = resolution.getScaledHeight();
                GlStateManager.disableDepth();
                GlStateManager.depthMask(false);
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableAlpha();
                mc.renderEngine.bindTexture(SCOPE_TEX);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                bufferbuilder.pos(w / 2 - 2 * h, h, -90.0).tex(0.0, 1.0).endVertex();
                bufferbuilder.pos(w / 2 + 2 * h, h, -90.0).tex(1.0, 1.0).endVertex();
                bufferbuilder.pos(w / 2 + 2 * h, 0.0, -90.0).tex(1.0, 0.0).endVertex();
                bufferbuilder.pos(w / 2 - 2 * h, 0.0, -90.0).tex(0.0, 0.0).endVertex();
                tessellator.draw();
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
                GlStateManager.enableAlpha();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GL11.glPopMatrix();
        }

    }
}

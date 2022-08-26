package github.xniter.dtmintegrations.handlers;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import github.xniter.dtmintegrations.utils.EnumVanillaHoes;
import github.xniter.dtmintegrations.utils.EnumVanillaItems;
import github.xniter.dtmintegrations.utils.EnumVanillaTools;
import github.xniter.dtmintegrations.utils.EnumVanillaWeapons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nuparu.sevendaystomine.entity.EntityZombieBase;
import nuparu.sevendaystomine.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameEvents {

    @SubscribeEvent
    public void entityHordeSpawnEvent(LivingSpawnEvent event) {
        EntityLiving e = (EntityLiving) event.getEntityLiving();
        World world = event.getWorld();

        if (!(e instanceof EntityZombieBase)) {

            if (Utils.isBloodmoon(world)) {
                if (ConfigGetter.getFocusedBloodMoon()) {
                    e.preventEntitySpawning = true;
                    event.setResult(Event.Result.DENY);
                } else {
                    if (ConfigGetter.getForcefulFocusedBloodMoon()) {
                        e.preventEntitySpawning = true;
                        event.setResult(Event.Result.DENY);
                        e.setDead();
                        event.getEntity().getEntityWorld().removeEntity(e);
                    }
                }
            }

            if (Utils.isWolfHorde(world)) {
                if (ConfigGetter.getFocusedWolfHorde()) {
                    e.preventEntitySpawning = true;
                    event.setResult(Event.Result.DENY);
                } else {
                    if (ConfigGetter.getForcefulFocusedWolfHorde()) {
                        e.preventEntitySpawning = true;
                        event.setResult(Event.Result.DENY);
                        e.setDead();
                        event.getEntity().getEntityWorld().removeEntity(e);
                    }
                }
            }
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onHoeUse(UseHoeEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack mainHand = player.getHeldItemMainhand();

        if (ConfigGetter.getDisableVanillaBlocksAndItemsUsage()) {
            for (EnumVanillaHoes vHoe : EnumVanillaHoes.values()) {
                if (mainHand.getItem().equals(vHoe.getItem())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSwordAttack(AttackEntityEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack mainHand = player.getHeldItemMainhand();

        if (ConfigGetter.getDisableVanillaBlocksAndItemsUsage()) {
            for (EnumVanillaWeapons vWeapons : EnumVanillaWeapons.values()) {
                if (mainHand.getItem().equals(vWeapons.getItem())) {
                    event.setCanceled(true);
                }
                break;
            }

            for (EnumVanillaTools vTools : EnumVanillaTools.values()) {
                if (mainHand.getItem().equals(vTools.getItem())) {
                    event.setCanceled(true);
                }
                break;
            }

            for (EnumVanillaHoes vHoe : EnumVanillaHoes.values()) {
                if (mainHand.getItem().equals(vHoe.getItem())) {
                    event.setCanceled(true);
                }
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onItemUse(PlayerEvent.BreakSpeed event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack mainHand = player.getHeldItemMainhand();

        if (ConfigGetter.getDisableVanillaBlocksAndItemsUsage()) {
            for (EnumVanillaTools vTools : EnumVanillaTools.values()) {
                if (mainHand.getItem().equals(vTools.getItem())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onTooltipCreation(ItemTooltipEvent event) {

        if (ConfigGetter.getAddDisabledTooltip()) {

            for (EnumVanillaHoes vHoe : EnumVanillaHoes.values()) {
                if (event.getItemStack().getItem().equals(vHoe.getItem())) {
                    event.getToolTip().addAll(createTooltip());
                }
                break;
            }

            for (EnumVanillaItems vItems : EnumVanillaItems.values()) {
                if (event.getItemStack().getItem().equals(vItems.getItem())) {
                    event.getToolTip().addAll(createTooltip());
                }
                break;
            }

            for (EnumVanillaTools vTools : EnumVanillaTools.values()) {
                if (event.getItemStack().getItem().equals(vTools.getItem())) {
                    event.getToolTip().addAll(createTooltip());
                }
                break;
            }

            for (EnumVanillaWeapons vWeapons : EnumVanillaWeapons.values()) {
                if (event.getItemStack().getItem().equals(vWeapons.getItem())) {
                    event.getToolTip().addAll(createTooltip());
                }
                break;
            }
        }

    }

    private Collection<String> createTooltip() {
        List<String> list = new ArrayList<>();

        list.add("");
        if (ConfigGetter.getDisableVanillaBlocksAndItemsUsage()) {
            if (ConfigGetter.getUseLangConfig()) {
                list.add(I18n.format(ConfigGetter.getTooltipNotUsableString(), TextFormatting.DARK_RED, TextFormatting.RESET));
                list.add(I18n.format(ConfigGetter.getTooltipCraftingOnlyString(), TextFormatting.DARK_RED, TextFormatting.RESET));
            } else {
                list.add(I18n.format("tooltip.not_usable", TextFormatting.DARK_RED, TextFormatting.RESET));
                list.add(I18n.format("tooltip.crafting_only", TextFormatting.DARK_RED, TextFormatting.RESET));
            }
        } else {
            if (ConfigGetter.getUseLangConfig()) {
                list.add(I18n.format(ConfigGetter.getTooltipNotUsableString(), TextFormatting.DARK_RED, TextFormatting.RESET));
                list.add(I18n.format(ConfigGetter.getTooltipRecipeDisabledString(), TextFormatting.DARK_RED, TextFormatting.RESET));
            } else {
                list.add(I18n.format("tooltip.not_usable", TextFormatting.DARK_RED, TextFormatting.RESET));
                list.add(I18n.format("tooltip.recipe_disabled", TextFormatting.DARK_RED, TextFormatting.RESET));
            }
        }

        return list;
    }
}

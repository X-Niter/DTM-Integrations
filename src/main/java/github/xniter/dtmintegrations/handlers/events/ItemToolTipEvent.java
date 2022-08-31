package github.xniter.dtmintegrations.handlers.events;

import github.xniter.dtmintegrations.handlers.ResourceBoolArrayHandler;
import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nuparu.sevendaystomine.config.ModConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemToolTipEvent extends ResourceBoolArrayHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onTooltipCreation(ItemTooltipEvent event) {

        if (ConfigGetter.getAddDisabledTooltip()) {
            if (isDisabledItem(event.getItemStack())) {
                event.getToolTip().addAll(createTooltip());
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
            if (ModConfig.players.disableVanillaBlocksAndItems) {
                if (ConfigGetter.getUseLangConfig()) {
                    list.add(I18n.format(ConfigGetter.getTooltipNotUsableString(), TextFormatting.DARK_RED, TextFormatting.RESET));
                    list.add(I18n.format(ConfigGetter.getTooltipRecipeDisabledString(), TextFormatting.DARK_RED, TextFormatting.RESET));
                } else {
                    list.add(I18n.format("tooltip.not_usable", TextFormatting.DARK_RED, TextFormatting.RESET));
                    list.add(I18n.format("tooltip.recipe_disabled", TextFormatting.DARK_RED, TextFormatting.RESET));
                }
            }
        }

        return list;
    }
}

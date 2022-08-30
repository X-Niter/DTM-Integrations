package github.xniter.dtmintegrations.handlers;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import github.xniter.dtmintegrations.utils.EnumDisabledVanilla;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AbstractVanillaHandler {
    private final Set<String> disabled = new HashSet<>();

    public AbstractVanillaHandler() {
        updateFields();
    }

    private void updateFields() {
        for(EnumDisabledVanilla c : EnumDisabledVanilla.values()) {
            disabled.add("minecraft:" + c.toString().toLowerCase());
        }

        if (ConfigGetter.getChangeTorches()) {
            disabled.add("minecraft:torch");
        }
    }

    protected boolean isDisabledItem(ItemStack stack) {
        String itemId = Objects.requireNonNull(stack.getItem().getRegistryName()).toString();
        return disabled.contains(itemId);
    }
}

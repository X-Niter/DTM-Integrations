package github.xniter.dtmintegrations.handlers;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
import github.xniter.dtmintegrations.handlers.config.ConfigHandler;
import github.xniter.dtmintegrations.utils.modconstants.EnumDisabledVanilla;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.stream.Collectors;

public class ResourceBoolArrayHandler {
    private final Set<String> disabled = new HashSet<>();
    private Set<String> allowedBloodMoonMobs = new HashSet<>();

    private Set<String> allowedWolfHordeMobs = new HashSet<>();

    private static Set<String> allowedGenericHordeMobs = new HashSet<>();

    public ResourceBoolArrayHandler() {
        updateFields();
    }

    private void updateFields() {
        for(EnumDisabledVanilla c : EnumDisabledVanilla.values()) {
            disabled.add("minecraft:" + c.toString().toLowerCase());
        }

        if (ConfigGetter.getChangeTorches()) {
            disabled.add("minecraft:torch");
        }

        List<String> abmlist = Arrays.asList(ConfigHandler.ALLOWED_MOBS_DURING_BLOODMOON);
        allowedBloodMoonMobs = abmlist.stream().filter(Objects::nonNull).collect(Collectors.toSet());

        List<String> awhlist = Arrays.asList(ConfigHandler.ALLOWED_MOBS_DURING_WOLFHORDE);
        allowedWolfHordeMobs = awhlist.stream().filter(Objects::nonNull).collect(Collectors.toSet());

        List<String> aghlist = Arrays.asList(ConfigHandler.ALLOWED_MOBS_DURING_GENERIC_HORDE);
        allowedGenericHordeMobs = aghlist.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public boolean isDisabledItem(ItemStack stack) {
        String itemId = Objects.requireNonNull(stack.getItem().getRegistryName()).toString();
        return disabled.contains(itemId);
    }

    public boolean isAllowedBloodMoonSpawn(Entity entity) {
        ResourceLocation mobResource = EntityList.getKey(entity.getClass());
        return allowedBloodMoonMobs.contains(mobResource.getNamespace().toLowerCase());
    }

    public boolean isAllowedWolfHordeSpawn(Entity entity) {
        ResourceLocation mobResource = EntityList.getKey(entity.getClass());
        return allowedWolfHordeMobs.contains(mobResource.getNamespace().toLowerCase());
    }

    public static boolean isAllowedGenericHordeSpawn(Entity entity) {
        ResourceLocation mobResource = EntityList.getKey(entity.getClass());
        return allowedGenericHordeMobs.contains(mobResource.getNamespace().toLowerCase());
    }
}

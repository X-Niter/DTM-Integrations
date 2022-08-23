package github.xniter.dtmintegrations.handlers;

import com.google.common.collect.ImmutableList;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;

@SuppressWarnings("unused")
public class DTMILateMixinLoader implements ILateMixinLoader {


    @Override
    public List<String> getMixinConfigs() {
        ImmutableList.Builder<String> list = ImmutableList.builder();
        if(ModGetter.isSevenDaysToMineLoaded()) {
            list.add("mixins.dtmintegrations.json");
        }
        return list.build();
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
    }

    @Override
    public void onMixinConfigQueued(String mixinConfig) {
        ILateMixinLoader.super.onMixinConfigQueued(mixinConfig);
    }
}

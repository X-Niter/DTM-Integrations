package github.xniter.dtmintegrations.mixin;

import github.xniter.dtmintegrations.config.DTMIConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nuparu.sevendaystomine.events.WorldEventHandler;
import nuparu.sevendaystomine.init.ModBlocks;
import nuparu.sevendaystomine.world.gen.city.City;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

@Mixin({WorldEventHandler.class})
public class MixinWorldEventHandler implements ILateMixinLoader {

    /**
     * @author X_Niter
     * @reason Config option change torches as some mods might already do this so being able to turn this off will help with mod compatibility.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    @Overwrite(remap = false)
    public void replaceTorchs(PopulateChunkEvent.Post event) {
        if (DTMIConfig.dtmGeneralConfig.changeTorches) {
            World world = event.getWorld();
            Chunk chunk = world.getChunk(event.getChunkX(), event.getChunkZ());
            Block fromBlock = Blocks.TORCH;
            Block toBlock = ModBlocks.TORCH_LIT;
            int i = chunk.x * 16;
            int j = chunk.z * 16;

            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 256; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        BlockPos pos = new BlockPos(x + i, y, z + j);
                        IBlockState oldState = world.getBlockState(pos);
                        Block oldBlock = oldState.getBlock();
                        if (oldBlock == fromBlock) {
                            world.setBlockState(pos, toBlock.getDefaultState().withProperty(BlockTorch.FACING, oldState.getValue(BlockTorch.FACING)));
                            chunk.markDirty();
                        }
                    }
                }
            }
        }
    }












    @Override
    public List<String> getMixinConfigs()
    {
        return Collections.singletonList("mixins.dtmintegrations.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
    }

    @Override
    public void onMixinConfigQueued(String mixinConfig)
    {
        ILateMixinLoader.super.onMixinConfigQueued(mixinConfig);
    }
}

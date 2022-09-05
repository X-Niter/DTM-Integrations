package github.xniter.dtmintegrations.mixin.sevendaystomine.events;

import github.xniter.dtmintegrations.handlers.config.ConfigGetter;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({WorldEventHandler.class})
public class MixinWorldEventHandler{

    /**
     * @author X_Niter
     * @reason Config Wrapper to allow torches to be Vanilla, if this is enabled, I suggest using something like CraftTweaker to give this torch the same OreDict as Vanilla torches. (May work on giving these torches the same oreDict as vanilla soon:TM)
     */
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    @Overwrite(remap = false)
    public void replaceTorchs(PopulateChunkEvent.Post event) {
        World world = event.getWorld();
        Chunk chunk = world.getChunk(event.getChunkX(), event.getChunkZ());
        Block fromBlock = Blocks.TORCH;
        Block toBlock = ModBlocks.TORCH_LIT;
        int i = chunk.x * 16;
        int j = chunk.z * 16;

        if (ConfigGetter.getChangeTorches()) {
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
        } else {
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 256; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        BlockPos pos = new BlockPos(x + i, y, z + j);
                        IBlockState oldState = world.getBlockState(pos);
                        Block oldBlock = oldState.getBlock();
                        if (oldBlock == toBlock) {
                            world.setBlockState(pos, fromBlock.getDefaultState().withProperty(BlockTorch.FACING, oldState.getValue(BlockTorch.FACING)));
                            chunk.markDirty();
                        }
                    }
                }
            }
        }
    }
}

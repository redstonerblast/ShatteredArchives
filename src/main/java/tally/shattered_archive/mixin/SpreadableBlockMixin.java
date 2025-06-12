package tally.shattered_archive.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpreadableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tally.shattered_archive.blocks.ShatteredBlocks;

@Mixin(SpreadableBlock.class)
public class SpreadableBlockMixin {
    @Redirect(method = "canSurvive(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private static boolean injection(BlockState instance, Block block) {
        return instance.isOf(Blocks.SNOW) || instance.isOf(ShatteredBlocks.BLOOD_STAINED_SNOW);
    }
}

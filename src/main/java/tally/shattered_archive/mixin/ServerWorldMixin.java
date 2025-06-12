package tally.shattered_archive.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tally.shattered_archive.blocks.ShatteredBlocks;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Unique
    ServerWorld thisObj = (ServerWorld) (Object)this;

    @Redirect(method= "tickIceAndSnow(Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;canSetSnow(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean redirect(Biome instance, WorldView world, BlockPos pos) {
        if (instance.canSetSnow(world, pos)) {
            if (instance.getWaterColor() == 0xbd3030) {
                int i = thisObj.getGameRules().getInt(GameRules.SNOW_ACCUMULATION_HEIGHT);
                BlockState blockState = thisObj.getBlockState(pos);
                if (blockState.isOf(ShatteredBlocks.BLOOD_STAINED_SNOW)) {
                    int j = (Integer) blockState.get(SnowBlock.LAYERS);
                    if (j < Math.min(i, 8)) {
                        BlockState blockState2 = blockState.with(SnowBlock.LAYERS, j + 1);
                        Block.pushEntitiesUpBeforeBlockChange(blockState, blockState2, thisObj, pos);
                        thisObj.setBlockState(pos, blockState2);
                    }
                } else {
                    thisObj.setBlockState(pos, ShatteredBlocks.BLOOD_STAINED_SNOW.getDefaultState());
                }
                return false;
            }
        }
        return instance.canSetSnow(world, pos);
    }

    @Redirect(method="tickIceAndSnow(Lnet/minecraft/util/math/BlockPos;)V", at = @At(value="INVOKE",target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", ordinal = 0))
    private boolean injectb(ServerWorld instance, BlockPos blockPos, BlockState blockState, @Local Biome biome) {
        return biome.getWaterColor() == 0xbd3030 ? instance.setBlockState(blockPos, ShatteredBlocks.BLOOD_ICE.getDefaultState()) : instance.setBlockState(blockPos, Blocks.ICE.getDefaultState());
    }
}

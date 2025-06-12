package tally.shattered_archive.blocks.custom;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class AuroraClusterBlock extends AmethystClusterBlock {
    protected final SaplingGenerator generator;
    private float height;
    private boolean full;

    public AuroraClusterBlock(SaplingGenerator generator, float pSize, float pOffset, Settings settings, boolean full) {
        super(pSize, pOffset, settings);
        this.height = pSize;
        this.generator = generator;
        this.full = full;
    }

    public AuroraClusterBlock(SaplingGenerator generator, float pSize, float pOffset, Settings settings) {
        super(pSize, pOffset, settings);
        this.height = pSize;
        this.generator = generator;
        this.full = false;
    }


    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (world.getBlockState(pos.down()).isOf(Blocks.END_STONE) && random.nextInt(4) == 0 && this.full) {
            this.generate(world, pos, state, random);
        }
    }

    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        this.generator.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
    }
}


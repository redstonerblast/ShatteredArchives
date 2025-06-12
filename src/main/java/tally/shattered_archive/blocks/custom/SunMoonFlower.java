package tally.shattered_archive.blocks.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import tally.shattered_archive.blocks.ShatteredBlocks;

public class SunMoonFlower extends FlowerBlock {
    public static final BooleanProperty LIT = Properties.LIT;
    public SunMoonFlower(SuspiciousStewEffectsComponent stewEffects, Settings settings) {
        super(stewEffects, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.getBlockState(sourcePos).isOf(state.getBlock()) && !(world.getBlockState(sourcePos).get(LIT) == state.get(LIT))) {
            world.scheduleBlockTick(pos, this, 2);
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        Boolean lit = (world.isDay() == (state.getBlock() == ShatteredBlocks.SUNDROP_FLOWER));
        world.setBlockState(pos, state.with(LIT, lit), Block.NOTIFY_ALL);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        Boolean lit = (world.isDay() == (state.getBlock() == ShatteredBlocks.SUNDROP_FLOWER));
        world.setBlockState(pos, state.with(LIT, lit), Block.NOTIFY_ALL);
    }
}

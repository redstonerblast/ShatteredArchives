package tally.shattered_archive.blocks.custom;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import tally.shattered_archive.blocks.ShatteredBlocks;
import tally.shattered_archive.blocks.helpers.ModBlockProperties;

import java.util.Random;

public class AuroraBlock extends Block implements Waterloggable {
    public static final BooleanProperty ACTIVE;
    public static final BooleanProperty WATERLOGGED;
    public static final int GROWTH_CHANCE = 5;
    private int type = 0;
    private static final Direction[] DIRECTIONS = Direction.values();

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        ACTIVE = ModBlockProperties.ACTIVE;
    }

    @Override
    protected void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, net.minecraft.util.math.random.Random random) {
        if (pState.get(ACTIVE)) {
            if (random.nextInt(5) == 0) {
                Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
                BlockPos blockPos = pPos.offset(direction);
                BlockState blockState = pLevel.getBlockState(blockPos);
                Block block = null;
                switch (this.type) {
                    case 1:
                        if (canClusterGrowAtState(blockState)) {
                            block = ShatteredBlocks.SMALL_SOL_AURORA_BUD;
                        } else if (blockState.isOf(ShatteredBlocks.SMALL_SOL_AURORA_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                            block = ShatteredBlocks.MEDIUM_SOL_AURORA_BUD;
                        } else if (blockState.isOf(ShatteredBlocks.MEDIUM_SOL_AURORA_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                            block = ShatteredBlocks.LARGE_SOL_AURORA_BUD;
                        } else if (blockState.isOf(ShatteredBlocks.LARGE_SOL_AURORA_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                            block = ShatteredBlocks.SOL_AURORA_CLUSTER;
                        }
                        break;
                }

                if (block != null) {
                    BlockState blockState2 = (BlockState)((BlockState)block.getDefaultState().with(AmethystClusterBlock.FACING, direction)).with(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
                    pLevel.setBlockState(blockPos, blockState2);
                }

            }
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack item = player.getStackInHand(hand);
        if (item.getItem() == Items.CHORUS_FRUIT && !state.get(ACTIVE)) {
            state = state.with(ACTIVE, true);
            world.setBlockState(pos, state, 10);
            item.setCount(item.getCount() - 1);
            item.decrement(1);
            return ItemActionResult.SUCCESS;
        } else if (item.getItem() instanceof PickaxeItem && state.get(ACTIVE)) {
            state = state.with(ACTIVE, false);
            world.setBlockState(pos, state, 10);
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.FAIL;
    }

    public static boolean canClusterGrowAtState(BlockState pState) {
        return pState.isAir() || pState.isOf(Blocks.WATER) && pState.getFluidState().getLevel() == 8;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{ACTIVE, WATERLOGGED});
    }

    protected FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        return (BlockState)((BlockState)this.getDefaultState().with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER));
    }

    public AuroraBlock(AbstractBlock.Settings settings, int type) {
        super(settings);
        this.type = type;
        this.setDefaultState(this.getDefaultState().with(ACTIVE, false).with(WATERLOGGED, false));
    }

    @Override
    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}

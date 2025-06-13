package tally.shattered_archive.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class WallDependentBlock extends HorizontalFacingBlock {
    public static final MapCodec<WallDependentBlock> CODEC = WallDependentBlock.createCodec(WallDependentBlock::new);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(10.0, 5.0, 3.0, 16.0, 11.0, 13.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 5.0, 3.0, 6.0, 11.0, 13.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3.0, 5.0, 10.0, 13.0, 11.0, 16.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(3.0, 5.0, 0.0, 13.0, 11.0, 6.0);

    private VoxelShape getShape(BlockState state) {
        Direction direction = state.get(FACING);
        switch (direction) {
            case SOUTH -> {
                return SOUTH_SHAPE;
            }
            case EAST -> {
                return EAST_SHAPE;
            }
            case WEST -> {
                return WEST_SHAPE;
            }
            case null, default -> {
                return NORTH_SHAPE;
            }
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getShape(state);
    }

    protected WallDependentBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<WallDependentBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return WallDependentBlock.canPlaceAt(world, pos, state.get(FACING).getOpposite());
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockState blockState = (BlockState)((BlockState)this.getDefaultState().with(FACING, direction.getOpposite()));
        if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) return blockState;
        return null;
    }

    public static boolean canPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite());
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(FACING).getOpposite() == direction && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}

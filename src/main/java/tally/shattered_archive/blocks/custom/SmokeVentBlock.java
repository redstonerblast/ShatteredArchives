package tally.shattered_archive.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import tally.shattered_archive.ShatteredArchive;
import tally.shattered_archive.blocks.ShatteredBlocks;
import tally.shattered_archive.particles.custom.SmokeParticleType;

import java.awt.*;

public class SmokeVentBlock extends Block {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final MapCodec<SmokeVentBlock> CODEC = createCodec(SmokeVentBlock::new);
    private static final int ADD_PARTICLE_ATTEMPTS = 14;
    private static final int PARTICLE_XZ_RADIUS = 10;
    private static final int PARTICLE_Y_MAX = 10;

    public SmokeVentBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(OPEN, true).with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING).add(OPEN).add(POWERED);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        World levelaccessor = ctx.getWorld();
        BlockPos blockpos = ctx.getBlockPos();
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
        blockpos$mutableblockpos = blockpos$mutableblockpos.set(blockpos);
        boolean flag = ! (levelaccessor.getBlockState(blockpos$mutableblockpos.up()).getBlock() == ShatteredBlocks.SMOKE_STACK);
        return this.getDefaultState().with(OPEN, flag).with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        boolean flag = world.isReceivingRedstonePower(pos);
        world.setBlockState(pos, state.with(POWERED, flag), 2);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
        blockpos$mutableblockpos = blockpos$mutableblockpos.set(pos);
        boolean flag = ! (world.getBlockState(blockpos$mutableblockpos.offset(state.get(FACING), 1)).getBlock() == ShatteredBlocks.SMOKE_STACK);
        return state.with(OPEN, flag);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
        blockpos$mutableblockpos = blockpos$mutableblockpos.set(pos);

        if (state.get(OPEN) && !(state.get(POWERED))) {
            int stack = 1;
            for (int length = 0; length <= 20; length++) {
                if (!(world.getBlockState(blockpos$mutableblockpos.offset(state.get(FACING).getOpposite(), length)).getBlock() == ShatteredBlocks.SMOKE_STACK)) {
                    stack = length + 1;
                    break;
                }
            }
            float[] color = {100, 100, 100};
            if(world.getBlockState((blockpos$mutableblockpos.offset(state.get(FACING), 1))).getBlock() instanceof Stainable stainable) {
                color = new Color(stainable.getColor().getEntityColor()).getRGBComponents(null);
            }
            double xVel = 0.0;
            double yVel = 0.0;
            double zVel = 0.0;
            switch (state.get(FACING)) {
                case UP:
                    yVel = 0.06;
                    break;
                case DOWN:
                    yVel = -0.06;
                    break;
                case EAST:
                    xVel = 0.06;
                    break;
                case WEST:
                    xVel = -0.06;
                    break;
                case NORTH:
                    zVel = -0.06;
                    break;
                case SOUTH:
                    zVel = 0.06;
                    break;
            }
            ParticleEffect particle = new SmokeParticleType(new Vector3f(color), stack * 20, 1);
            world.addParticle(particle, (double) pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), xVel, yVel, zVel);
        };
    }
}
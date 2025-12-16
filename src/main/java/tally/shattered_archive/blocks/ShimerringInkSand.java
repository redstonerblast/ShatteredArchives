package tally.shattered_archive.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class ShimerringInkSand extends Block {


    public static final MapCodec<ShimerringInkSand> CODEC = ShimerringInkSand.createCodec(ShimerringInkSand::new);
    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    public MapCodec<ShimerringInkSand> getCodec() {return CODEC;}

    public ShimerringInkSand(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
    }
    @Override
    protected void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        ShimerringInkSand.light(state, world, pos, 10);
        super.onBlockBreakStart(state, world, pos, player);
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if(state.get(LIT)){
            return 16;
        }
        return 0;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.bypassesSteppingEffects()) {
            ShimerringInkSand.light(state, world, pos, 60);
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    private static void light(BlockState state, World world, BlockPos pos, int delay) {
        if (!state.get(LIT)) {
            BlockState litState = state.with(LIT, true);
            world.setBlockState(pos, litState, Block.NOTIFY_ALL);

            if (!world.isClient) {
                world.scheduleBlockTick(pos, litState.getBlock(), delay);
            }
        }
    }


    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        Box box = new Box(pos).expand(0.1, 0.5, 0.1);
        List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class, box);

        if (!entities.isEmpty()){
            world.scheduleBlockTick(pos, state.getBlock(), 60);
        } else{
            world.setBlockState(pos, state.with(LIT, false), Block.NOTIFY_ALL);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

}

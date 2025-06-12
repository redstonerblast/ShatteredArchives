package tally.shattered_archive.blocks.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

import static net.minecraft.util.BlockRotation.CLOCKWISE_90;
import static net.minecraft.util.BlockRotation.COUNTERCLOCKWISE_90;

public class GlassPillar extends Block {
    public static final EnumProperty<Direction.Axis> AXIS;

    static {
        AXIS = Properties.AXIS;
    }

    public GlassPillar(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    public BlockState rotate(BlockState pState, VariantSettings.Rotation pRot) {
        return rotatePillar(pState, pRot);
    }

    public static BlockState rotatePillar(BlockState pState, VariantSettings.Rotation pRotation) {
        switch(pRotation) {
            case R270:
            case R90:
                return switch ((Direction.Axis) pState.get(AXIS)) {
                    case X -> pState.with(AXIS, Direction.Axis.Z);
                    case Z -> pState.with(AXIS, Direction.Axis.X);
                    default -> pState;
                };
            default:
                return pState;
        }
    }

    @Override
    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) && stateFrom.get(AXIS) == state.get(AXIS) || super.isSideInvisible(state, stateFrom, direction);
    }
}

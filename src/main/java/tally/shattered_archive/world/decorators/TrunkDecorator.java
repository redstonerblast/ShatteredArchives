package tally.shattered_archive.world.decorators;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.AttachedToLeavesTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import tally.shattered_archive.blocks.WallDependentBlock;

import java.util.List;

public class TrunkDecorator extends TreeDecorator {
    public static final MapCodec<TrunkDecorator> CODEC =  RecordCodecBuilder.mapCodec(instance ->
            fillDecoratorFields(instance).apply(instance, TrunkDecorator::new));

    protected static <P extends TreeDecorator> Products.P2<RecordCodecBuilder.Mu<P>, Float, BlockStateProvider> fillDecoratorFields(RecordCodecBuilder.Instance<P> instance) {
        return instance.group(
                ((MapCodec)Codec.floatRange(0, 1).fieldOf("probability")).forGetter(placer -> ((TrunkDecorator) placer).probability),
                ((MapCodec)BlockStateProvider.TYPE_CODEC.fieldOf("block_provider")).forGetter(placer -> ((TrunkDecorator) placer).blockProvider));
    }

    protected final float probability;
    protected final BlockStateProvider blockProvider;

    public TrunkDecorator(float probability, BlockStateProvider blockProvider) {
        this.probability = probability;
        this.blockProvider = blockProvider;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ShatteredDecorators.ATTACHED_TO_TRUNK;
    }

    @Override
    public void generate(Generator generator) {
        Random random = generator.getRandom();
        if (random.nextFloat() >= this.probability) {
            return;
        }
        ObjectArrayList<BlockPos> list = generator.getLogPositions();
        int i = ((BlockPos)list.get(0)).getY();
        list.stream().filter(pos -> pos.getY() - i <= 2).forEach(pos -> {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockPos blockPos;
                if (!(random.nextFloat() <= 0.25f) || !generator.isAir(blockPos = pos.add(direction.getOffsetX(), 0, direction.getOffsetZ()))) continue;
                generator.replace(blockPos, (BlockState)((BlockState) blockProvider.get(random, blockPos)).with(WallDependentBlock.FACING, direction));
            }
        });
    }
}

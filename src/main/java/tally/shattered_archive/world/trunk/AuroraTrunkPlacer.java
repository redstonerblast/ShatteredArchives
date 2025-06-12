package tally.shattered_archive.world.trunk;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.slf4j.Logger;
import tally.shattered_archive.ShatteredArchive;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class AuroraTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<AuroraTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((p_70261_) -> {
        return fillTrunkPlacerFields(p_70261_).apply(p_70261_, AuroraTrunkPlacer::new);
    });

    public AuroraTrunkPlacer(int p_70248_, int p_70249_, int p_70250_) {
        super(p_70248_, p_70249_, p_70250_);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ShatteredTrunkPlacers.AURORA_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, net.minecraft.util.math.random.Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();

        float j = 0;

        for(int i = 0; i <= height; ++i) {
            BlockPos t = startPos.mutableCopy().up(i);
            if (TreeFeature.canReplace(world, t)) {
                this.getAndSetState(world, replacer, random, t, config);
            }
            if(i >= 5) {
                if (i % 2 == 0 || i == height) {
                    ++j;
                    list.add(new FoliagePlacer.TreeNode(startPos.mutableCopy().up(i + 1), (int) (7 - j), false));
                }
            }
        }

        return list;
    }
}

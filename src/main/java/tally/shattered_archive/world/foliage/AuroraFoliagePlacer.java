package tally.shattered_archive.world.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import tally.shattered_archive.ShatteredArchive;

public class AuroraFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<AuroraFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(auroraFoliagePlacerInstance ->
            fillFoliagePlacerFields(auroraFoliagePlacerInstance).apply(auroraFoliagePlacerInstance, AuroraFoliagePlacer::new));

    public AuroraFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ShatteredFoliagePlacers.AURORA_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        ShatteredArchive.LOGGER.info("gen tree");
        boolean flag = treeNode.isGiantTrunk();
        BlockPos blockpos = treeNode.getCenter().up();
        ShatteredArchive.LOGGER.info("dome");
        this.placeLeavesDome(world, placer, random, config, blockpos, offset, -1 - radius, flag, treeNode);
    }

    protected void placeLeavesDome(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos blockpos, int offset, int rad, boolean flag, TreeNode treeNode) {
        int i = flag ? 1 : 0;
        ShatteredArchive.LOGGER.info("block");
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

        int pRadius = treeNode.getFoliageRadius();

        for(int j = -pRadius; j <= pRadius + i; ++j) {
            for(int k = -pRadius; k <= pRadius + i; ++k) {
                double pow = Math.pow((Math.pow(j, 2) + Math.pow(k, 2)), 0.5);
                if(pow <= pRadius) {
                    if(pow >= (pRadius - 1.0)) {
                        this.startTendril(world, placer, random, config, blockpos, j, offset, k);
                    }
                    blockpos$mutableblockpos.set(blockpos);
                    tryPlaceLeafWater(world, placer, random, config, blockpos$mutableblockpos.add(j, offset, k));
                }
            }
        }
    }

    protected void startTendril(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos blockpos, int j, int offset, int k) {
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

        int i = random.nextInt(6);

        for(int n = 0; n <= i; ++n){
            blockpos$mutableblockpos.set(blockpos);
            tryPlaceLeafWater(world, placer, random, config, blockpos$mutableblockpos.add(j, offset - n, k));
        }
    }

    protected static void tryPlaceLeafWater(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos pPos) {
        if (TreeFeature.canReplace(world, pPos)) {
            placeFoliageBlock(world, placer, random, config, pPos);
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}

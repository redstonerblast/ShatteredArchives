package tally.shattered_archive.world.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import tally.shattered_archive.ShatteredArchive;
import tally.shattered_archive.blocks.ShatteredBlocks;

public class DroopingFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<DroopingFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(auroraFoliagePlacerInstance ->
            fillFoliagePlacerFields(auroraFoliagePlacerInstance).apply(auroraFoliagePlacerInstance, DroopingFoliagePlacer::new));

    public DroopingFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ShatteredFoliagePlacers.DROOPING_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        boolean flag = treeNode.isGiantTrunk();
        BlockPos blockpos = treeNode.getCenter().up();
        this.placeLeavesDome(world, placer, random, config, blockpos, offset, radius, flag, treeNode);
    }

    protected void placeLeavesDome(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos blockpos, int offset, int rad, boolean flag, TreeNode treeNode) {
        int i = flag ? 1 : 0;
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

        int pRadius = rad + random.nextInt(1);

        for(int j = -pRadius; j <= pRadius + i; ++j) {
            for(int k = -pRadius; k <= pRadius + i; ++k) {
                for (int l = 0; l <= pRadius - 1 + i; ++l) {
                    double pow = Math.pow((Math.pow(j, 2) + Math.pow(k, 2) + Math.pow(l, 2)), 0.5);
                    if (pow <= pRadius) {
                        if (pow >= (pRadius - 1.5)) {
                            this.startTendril(world, placer, random, config, blockpos, j, l - 1, k, (float) (pow - (pRadius - 1.5)));
                        }
                        blockpos$mutableblockpos.set(blockpos);
                        tryPlaceLeafWater(world, placer, random, config, blockpos$mutableblockpos.add(j, l - 1, k));
                    }
                }
            }
        }
    }

    protected void startTendril(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos blockpos, int j, int offset, int k, float test) {
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

        int i = random.nextInt(Math.max((int) (2 * (test * 2)), 1));

        for(int n = 0; n <= i; ++n){
            blockpos$mutableblockpos.set(blockpos);
            tryPlaceLeafWater(world, placer, random, config, blockpos$mutableblockpos.add(j, offset - n, k));
        }

        if (random.nextFloat() < 0.3) {
            tryPlaceGlowingWater(world, placer, random, config, blockpos$mutableblockpos.add(j, offset - i, k));
            tryPlaceHangingWater(world, placer, random, config, blockpos$mutableblockpos.add(j, offset - 1 - i, k));
        }
    }

    protected static void tryPlaceLeafWater(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos pPos) {
        if (TreeFeature.canReplace(world, pPos)) {
            placeFoliageBlock(world, placer, random, config, pPos);
        }
    }

    protected static void tryPlaceHangingWater(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos pPos) {
        if (TreeFeature.canReplace(world, pPos)) {
            placeHangingBlock(world, placer, random, config, pPos);
        }
    }

    protected static void tryPlaceGlowingWater(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos pPos) {
        if (TreeFeature.canReplace(world, pPos)) {
            placeGlowingBlock(world, placer, random, config, pPos);
        }
    }

    public static boolean canReplace(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> state.isAir() || state.isReplaceable());
    }

    protected static boolean placeHangingBlock(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos pos) {
        if (!canReplace(world, pos)) {
            return false;
        } else {
            BlockState blockState = config.foliageProvider.get(random, pos);
            blockState = blockState.getBlock() == ShatteredBlocks.ENCHANTED_WILLOW_LEAVES ? ShatteredBlocks.ENCHANTED_WILLOW_DROOPING_LEAVES.getDefaultState() : ShatteredBlocks.BLUE_ENCHANTED_WILLOW_DROOPING_LEAVES.getDefaultState();
            if (blockState.contains(Properties.WATERLOGGED)) {
                blockState = (BlockState)blockState.with(Properties.WATERLOGGED, world.testFluidState(pos, (fluidState) -> fluidState.isEqualAndStill(Fluids.WATER)));
            }

            placer.placeBlock(pos, blockState);
            return true;
        }
    }

    protected static boolean placeGlowingBlock(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, BlockPos pos) {
        if (!TreeFeature.canReplace(world, pos)) {
            return false;
        } else {
            BlockState blockState = config.foliageProvider.get(random, pos);
            blockState = blockState.getBlock() == ShatteredBlocks.ENCHANTED_WILLOW_LEAVES ? ShatteredBlocks.GLOWING_ENCHANTED_WILLOW_LEAVES.getDefaultState() : ShatteredBlocks.GLOWING_BLUE_ENCHANTED_WILLOW_LEAVES.getDefaultState();
            if (blockState.contains(Properties.WATERLOGGED)) {
                blockState = (BlockState)blockState.with(Properties.WATERLOGGED, world.testFluidState(pos, (fluidState) -> fluidState.isEqualAndStill(Fluids.WATER)));
            }

            placer.placeBlock(pos, blockState);
            return true;
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

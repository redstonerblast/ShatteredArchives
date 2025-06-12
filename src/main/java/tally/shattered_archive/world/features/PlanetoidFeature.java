package tally.shattered_archive.world.features;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class PlanetoidFeature extends Feature<PlanetoidFeature.GeyserFeatureConfig> {
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();

    public PlanetoidFeature(Codec<PlanetoidFeature.GeyserFeatureConfig> configCodec) {
        super(configCodec);
    }
    @Override
    public boolean generate(FeatureContext<GeyserFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        GeyserFeatureConfig config = context.getConfig();
        BlockStateProvider highMat = config.higher_mat;
        BlockStateProvider lowMat = config.lower_mat;
        BlockStateProvider surfaceMat = config.top_mat;
        List<Pair<RegistryEntry<PlacedFeature>, Float>> features = config.features;
        int size = random.nextBetween(3, 7);
        for (int xVal = -size; xVal < size; xVal ++) {
            for (int yVal = -size; yVal < size; yVal ++) {
                for (int zVal = -size; zVal < size; zVal ++) {
                    BlockPos newPos = blockPos.add(xVal, yVal, zVal);
                    if (calcDist(xVal, yVal, zVal, size)) {
                        if (yVal >= 0) {
                            if (!calcDist(xVal, yVal+1, zVal, size) && calcDist(xVal, yVal-1, zVal, size)) {
                                structureWorldAccess.setBlockState(newPos, surfaceMat.get(random, newPos), Block. FORCE_STATE);
                            } else {
                                structureWorldAccess.setBlockState(newPos, highMat.get(random, newPos), Block. FORCE_STATE);
                            }
                        } else {
                            structureWorldAccess.setBlockState(newPos, lowMat.get(random, newPos), Block. FORCE_STATE);
                        }
                        for (Direction dir : Direction.values()) {
                            if (!calcDist(dir.getVector().getX() + xVal, dir.getVector().getY() + yVal, dir.getVector().getZ() + zVal, size)) {
                                for (Pair<RegistryEntry<PlacedFeature>, Float> pair : features) {
                                    if (random.nextFloat() <= pair.getSecond()) {
                                        pair.getFirst().value().generateUnregistered(structureWorldAccess, context.getGenerator(), random, newPos.add(dir.getVector()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (random.nextFloat() <= 0.2) {
            structureWorldAccess.setBlockState(blockPos, Blocks.CHEST.getDefaultState(), Block.FORCE_STATE);
            BlockEntity blockEntity = structureWorldAccess.getBlockEntity(blockPos);
            if (blockEntity != null)
            {
                var nbt = new NbtCompound();
                nbt.putString("LootTable", "shatteredarchive:chests/planetoid");
                nbt.putLong("LootTableSeed", blockPos.asLong());
                blockEntity.read(nbt, structureWorldAccess.getRegistryManager());
            }
        }
        return true;
    }

    private boolean calcDist(int x, int y, int z, int size) {
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)) <= size;
    }

    private boolean canReplace(BlockState state) {
        return !state.isIn(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    public record GeyserFeatureConfig(BlockStateProvider lower_mat, BlockStateProvider higher_mat, BlockStateProvider top_mat, List<Pair<RegistryEntry<PlacedFeature>, Float>> features) implements FeatureConfig {
        public static final Codec<GeyserFeatureConfig> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                BlockStateProvider.TYPE_CODEC.fieldOf("lower_mat").forGetter(GeyserFeatureConfig::lower_mat),
                                BlockStateProvider.TYPE_CODEC.fieldOf("higher_mat").forGetter(GeyserFeatureConfig::higher_mat),
                                BlockStateProvider.TYPE_CODEC.fieldOf("top_mat").forGetter(GeyserFeatureConfig::top_mat),
                                Codec.list(Codec.pair(PlacedFeature.REGISTRY_CODEC.fieldOf("feature").codec(), Codec.floatRange(0.0F, 1.0F).fieldOf("chance").codec())).fieldOf("features").forGetter(config -> config.features))
                        .apply(instance, GeyserFeatureConfig::new)
        );
    }
}

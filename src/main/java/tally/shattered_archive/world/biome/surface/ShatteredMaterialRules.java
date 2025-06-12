package tally.shattered_archive.world.biome.surface;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import tally.shattered_archive.blocks.ShatteredBlocks;
import tally.shattered_archive.world.biome.ShatteredBiomes;

public class ShatteredMaterialRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule MUD = makeStateRule(Blocks.MUD);
    private static final MaterialRules.MaterialRule COBBLED_DEEPSLATE = makeStateRule(Blocks.COBBLED_DEEPSLATE);
    private static final MaterialRules.MaterialRule DEEPSLATE_BRICK = makeStateRule(Blocks.DEEPSLATE_TILES);
    private static final MaterialRules.MaterialRule WATER = makeStateRule(Blocks.WATER);
    private static final MaterialRules.MaterialRule BLACK_CONCRETE_POWDER = makeStateRule(ShatteredBlocks.INK_SAND);
    private static final MaterialRules.MaterialRule BLACK_TERRACOTTA = makeStateRule(ShatteredBlocks.INK_SANDSTONE);

    public static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);

        MaterialRules.MaterialRule grassSurface = MaterialRules.sequence(MaterialRules.condition(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        MaterialRules.MaterialRule surfaceMat = MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.OFFSET, -2.0, -0.25), DEEPSLATE_BRICK),
                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.OFFSET, -0.25, 0.0), COBBLED_DEEPSLATE),
                MUD);

        return MaterialRules.sequence(
                MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.biome(ShatteredBiomes.GRAVITY_SPRINGS),
                               MaterialRules.condition(
                                       MaterialRules.surface(),
                                       MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, surfaceMat)
                               )),
                        MaterialRules.condition(MaterialRules.biome(ShatteredBiomes.ABYSSAL_OCEAN),
                                MaterialRules.condition(
                                        MaterialRules.aboveY(YOffset.aboveBottom(5), 1),
                                        WATER
                                )
                        ),
                        MaterialRules.condition(MaterialRules.biome(ShatteredBiomes.INK_DESERT),
                                MaterialRules.condition(
                                        MaterialRules.surface(),
                                    MaterialRules.sequence(
                                            MaterialRules.condition(
                                                    MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                                    BLACK_CONCRETE_POWDER
                                                    ),
                                            MaterialRules.condition(
                                                    MaterialRules.stoneDepth(0, true, 30, VerticalSurfaceType.FLOOR),
                                                    BLACK_TERRACOTTA
                                            )
                                    )
                                )
                        )
                )
        );
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}

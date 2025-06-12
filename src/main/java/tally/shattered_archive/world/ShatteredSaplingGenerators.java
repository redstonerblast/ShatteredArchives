package tally.shattered_archive.world;

import net.minecraft.block.SaplingGenerator;
import tally.shattered_archive.ShatteredArchive;

import java.util.Optional;

public class ShatteredSaplingGenerators {
    public static final SaplingGenerator SOL_AURORA = new SaplingGenerator(ShatteredArchive.MOD_ID + ":sol_aurora",
            Optional.empty(), Optional.of(ShatteredConfiguredFeatures.SOL_AURORA_KEY), Optional.empty());
}

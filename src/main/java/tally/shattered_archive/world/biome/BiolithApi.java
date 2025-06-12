package tally.shattered_archive.world.biome;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import tally.shattered_archive.ShatteredArchive;
import tally.shattered_archive.world.biome.surface.ShatteredMaterialRulesNether;

public class BiolithApi implements Runnable {
    public static final RegistryKey<Biome> FROSTFELL = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(ShatteredArchive.MOD_ID, "frostfell"));
    public static final RegistryKey<Biome> HALLOW = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(ShatteredArchive.MOD_ID, "hallow"));

    public void addBiomes() {
        BiomePlacement.addNether(FROSTFELL, FrostfellBiome.NOISE_POINT);
        BiomePlacement.addNether(HALLOW, HallowBiome.NOISE_POINT);
    }

    // Use Biolith to register our Biome placements.
    // We can't do registration stuff until Cinderscapes' common module is ready.
    // This method will be called when Cinderscapes is done initializing.
    @Override
    public void run() {
        // Register the Cinderscapes surface rules.
        SurfaceGeneration.addNetherSurfaceRules(
                Identifier.of(ShatteredArchive.MOD_ID, "surface_rules"),
                ShatteredMaterialRulesNether.makeRules());

        // Register the Cinderscapes surface builders.

        this.addBiomes();
    }
}

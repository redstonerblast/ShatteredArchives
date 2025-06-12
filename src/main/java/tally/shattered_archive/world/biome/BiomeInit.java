package tally.shattered_archive.world.biome;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import tally.shattered_archive.ShatteredArchive;

import java.util.List;

public class BiomeInit {
    public static final RegistryKey<Biome> FROSTFELL = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(ShatteredArchive.MOD_ID, "frostfell"));
    public static final RegistryKey<Biome> HALLOW = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(ShatteredArchive.MOD_ID, "hallow"));

    @SuppressWarnings("unused")
    public static final List<RegistryKey<Biome>> BIOMES = List.of(
            FROSTFELL,
            HALLOW
    );

    public static void bootstrap(Registerable<Biome> registerable) {
        registerable.register(FROSTFELL, FrostfellBiome.create(registerable));
        registerable.register(HALLOW, HallowBiome.create(registerable));
    }
}

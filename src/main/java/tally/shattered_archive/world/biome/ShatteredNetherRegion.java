package tally.shattered_archive.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import tally.shattered_archive.ShatteredArchive;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.List;
import java.util.function.Consumer;

public class ShatteredNetherRegion {
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

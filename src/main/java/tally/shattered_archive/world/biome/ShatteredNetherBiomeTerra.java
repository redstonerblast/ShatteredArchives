package tally.shattered_archive.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import tally.shattered_archive.ShatteredArchive;
import tally.shattered_archive.world.biome.surface.ShatteredMaterialRulesNether;
import terrablender.api.*;

import java.util.function.Consumer;

public class ShatteredNetherBiomeTerra extends Region {
    public ShatteredNetherBiomeTerra() {
        super(Identifier.of(ShatteredArchive.MOD_ID, "nether"), RegionType.NETHER, 2);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        addBiome(mapper, FrostfellBiome.NOISE_POINT, ShatteredNetherRegion.FROSTFELL);
        addBiome(mapper, HallowBiome.NOISE_POINT, ShatteredNetherRegion.HALLOW);
    }
}
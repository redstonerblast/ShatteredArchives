package tally.shattered_archive.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tally.shattered_archive.ShatteredArchive;
import tally.shattered_archive.particles.custom.SmokeParticleType;

public class ShatteredParticles {
    public static final ParticleType<SmokeParticleType> SMOKE_PARTICLE = FabricParticleTypes.complex(true, SmokeParticleType.CODEC, SmokeParticleType.PACKET_CODEC);
    public static final ParticleType<SimpleParticleType> SMOKE_PARTICLE_SIMPLE = FabricParticleTypes.simple();
    public static final SimpleParticleType WILLOW_LEAVES = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_WILLOW_LEAVES = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ShatteredArchive.MOD_ID, "smoke_particle"), SMOKE_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ShatteredArchive.MOD_ID, "smoke_particle_simple"), SMOKE_PARTICLE_SIMPLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ShatteredArchive.MOD_ID, "willow_leaves"), WILLOW_LEAVES);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ShatteredArchive.MOD_ID, "blue_willow_leaves"), BLUE_WILLOW_LEAVES);
    }
}

package tally.shattered_archive.particles.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.*;
import net.minecraft.util.DyeColor;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import tally.shattered_archive.particles.ShatteredParticles;

public record SmokeParticleType(Vector3f color, int maxAge, float scale) implements ParticleEffect {

    public SmokeParticleType(DyeColor color, int maxAge, float scale) {
        this(Vec3d.unpackRgb(color.getEntityColor()).toVector3f(), maxAge, scale);
    }


    public static final MapCodec<SmokeParticleType> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codecs.VECTOR_3F
                            .fieldOf("color")
                            .forGetter(SmokeParticleType::color),
                    Codec.INT
                            .fieldOf("max_age")
                            .forGetter(SmokeParticleType::maxAge),
                    Codec.FLOAT
                            .fieldOf("scale")
                            .forGetter(SmokeParticleType::scale)
            ).apply(instance, SmokeParticleType::new)
    );

    public static final PacketCodec<RegistryByteBuf, SmokeParticleType> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VECTOR3F, SmokeParticleType::color,
            PacketCodecs.INTEGER, SmokeParticleType::maxAge,
            PacketCodecs.FLOAT, SmokeParticleType::scale,
            SmokeParticleType::new
    );

    @Override
    public ParticleType<?> getType() {
        return ShatteredParticles.SMOKE_PARTICLE;
    }
}
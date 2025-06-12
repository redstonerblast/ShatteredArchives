package tally.shattered_archive.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SmokeParticle extends SpriteBillboardParticle {
    public SmokeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, boolean signal) {
        super(world, x, y, z);
        this.scale(3.0F);
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        if (signal) {
            this.maxAge = this.random.nextInt(10) + 280;
        } else {
            this.maxAge = this.random.nextInt(10) + 80;
        }

        this.gravityStrength = 3.0E-6F;
        this.velocityX = velocityX + this.random.nextFloat() / 500.0F * (this.random.nextBoolean() ? 1 : -1);
        this.velocityY = velocityY + this.random.nextFloat() / 500.0F * (this.random.nextBoolean() ? 1 : -1);
        this.velocityZ = velocityZ + this.random.nextFloat() / 500.0F * (this.random.nextBoolean() ? 1 : -1);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.angle = this.prevAngle + 1;
        if (this.age++ < this.maxAge && !(this.scale <= 0.0F)) {
            this.setPos(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
            if (this.age >= this.maxAge - 40 && this.scale > 0.015F) {
                this.scale -= 0.025F;
            }
        } else {
            this.markDead();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class SmokeParticleFactory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public SmokeParticleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            SmokeParticle campfireSmokeParticle = new SmokeParticle(clientWorld, d, e, f, g, h, i, true);
            campfireSmokeParticle.setAlpha(1f);
            campfireSmokeParticle.setSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
}
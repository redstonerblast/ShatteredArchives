package tally.shattered_archive.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tally.shattered_archive.ShatteredArchive;
import tally.shattered_archive.world.biome.ShatteredBiomes;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    private static final Identifier BLOODY_SNOW = ShatteredArchive.id("textures/environment/bloody_snow.png");
    private static final Identifier ECLIPSED_SUN = ShatteredArchive.id("textures/environment/eclipsed_sun.png");

    @Shadow
    private ClientWorld world;

    @Redirect(method = "renderWeather(Lnet/minecraft/client/render/LightmapTextureManager;FDDD)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V"))
    private void injected(int texture, Identifier id, @Local Biome biome) {
        if (biome.getWaterColor() == 0xbd3030) {
            RenderSystem.setShaderTexture(0, BLOODY_SNOW);
        } else {
            RenderSystem.setShaderTexture(0, id);
        }
    }

    @Redirect(method= "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 0))
    private void injected(int texture, Identifier id, @Local(argsOnly = true) Camera camera) {
        Biome biome = world.getBiome(camera.getBlockPos()).value();
        if (biome.getWaterColor() == 0x1fcc98) {
            RenderSystem.setShaderTexture(0, ECLIPSED_SUN);
        } else {
            RenderSystem.setShaderTexture(0, id);
        }
    }
}
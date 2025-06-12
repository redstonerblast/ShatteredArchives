package tally.shattered_archive.mixin;

import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FreezeTopLayerFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tally.shattered_archive.world.features.BloodFreezeFeature;

@Mixin(FreezeTopLayerFeature.class)
public class FreezeTopLayerMixin {
    @Inject(method = "generate(Lnet/minecraft/world/gen/feature/util/FeatureContext;)Z", at = @At(value="HEAD"), cancellable = true)
    private void inject(FeatureContext<DefaultFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
        BloodFreezeFeature.generateb(context);
        cir.setReturnValue(true);
    }
}

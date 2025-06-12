package tally.shattered_archive.world.foliage;

import net.minecraft.world.gen.foliage.FoliagePlacerType;
import tally.shattered_archive.mixin.ShatteredFoliagePlacerInvoker;

public class ShatteredFoliagePlacers {
    public static final FoliagePlacerType<?> AURORA_FOLIAGE_PLACER = ShatteredFoliagePlacerInvoker.callRegister("aurora_foliage_placer", AuroraFoliagePlacer.CODEC);
    public static final FoliagePlacerType<?> DROOPING_FOLIAGE_PLACER = ShatteredFoliagePlacerInvoker.callRegister("drooping_foliage_placer", DroopingFoliagePlacer.CODEC);

    public static void register() {
    }
}

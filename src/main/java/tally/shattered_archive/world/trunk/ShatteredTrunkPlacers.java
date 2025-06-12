package tally.shattered_archive.world.trunk;

import net.minecraft.world.gen.trunk.TrunkPlacerType;
import tally.shattered_archive.mixin.ShatteredTrunkPlacerInvoker;

public class ShatteredTrunkPlacers {
    public static final TrunkPlacerType<?> AURORA_TRUNK_PLACER = ShatteredTrunkPlacerInvoker.callRegister("aurora_trunk_placer", AuroraTrunkPlacer.CODEC);

    public static void register() {
    }
}

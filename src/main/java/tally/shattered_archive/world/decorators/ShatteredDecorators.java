package tally.shattered_archive.world.decorators;

import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import tally.shattered_archive.mixin.ShatteredDecoratorInvoker;
import tally.shattered_archive.world.foliage.DroopingFoliagePlacer;

public class ShatteredDecorators {
    public static final TreeDecoratorType<?> ATTACHED_TO_TRUNK = ShatteredDecoratorInvoker.callRegister("trunk_attached", TrunkDecorator.CODEC);

    public static void register() {
    }
}

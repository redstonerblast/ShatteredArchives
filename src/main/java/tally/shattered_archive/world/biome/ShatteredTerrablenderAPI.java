package tally.shattered_archive.world.biome;

import net.minecraft.util.Identifier;
import tally.shattered_archive.ShatteredArchive;
import tally.shattered_archive.world.biome.surface.ShatteredMaterialRules;
import tally.shattered_archive.world.biome.surface.ShatteredMaterialRulesNether;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ShatteredTerrablenderAPI implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ShatteredOverworldRegion(Identifier.of(ShatteredArchive.MOD_ID, "overworld"), 7));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, ShatteredArchive.MOD_ID, ShatteredMaterialRules.makeRules());
    }
}

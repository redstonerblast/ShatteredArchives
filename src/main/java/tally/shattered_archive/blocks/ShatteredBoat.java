package tally.shattered_archive.blocks;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import tally.shattered_archive.ShatteredArchive;
import tally.shattered_archive.items.ShatteredItems;

public class ShatteredBoat {
    public static final Identifier PEARLWOOD_BOAT_ID = ShatteredArchive.id("pearlwood_boat");
    public static final Identifier PEARLWOOD_CHEST_BOAT_ID = ShatteredArchive.id("pearlwood_chest_boat");
    public static final RegistryKey<TerraformBoatType> PEARLWOOD_BOAT_KEY = TerraformBoatTypeRegistry.createKey(PEARLWOOD_BOAT_ID);

    public static final Identifier ENCHANTED_WILLOW_BOAT_ID = ShatteredArchive.id("enchanted_willow_boat");
    public static final Identifier ENCHANTED_WILLOW_CHEST_BOAT_ID = ShatteredArchive.id("enchanted_willow_chest_boat");
    public static final RegistryKey<TerraformBoatType> ENCHANTED_WILLOW_BOAT_KEY = TerraformBoatTypeRegistry.createKey(ENCHANTED_WILLOW_BOAT_ID);

    public static TerraformBoatType PEARLWOOD_TYPE;
    public static TerraformBoatType ENCHANTED_WILLOW_TYPE;

    public static TerraformBoatType register(RegistryKey<TerraformBoatType> key, TerraformBoatType type) {
        return Registry.register(TerraformBoatTypeRegistry.INSTANCE, key, type);
    }

    public static void load() {
        PEARLWOOD_TYPE = register(PEARLWOOD_BOAT_KEY, new TerraformBoatType.Builder()
                .item(ShatteredItems.PEARLWOOD_BOAT)
                .chestItem(ShatteredItems.PEARLWOOD_CHEST_BOAT)
                .planks(ShatteredBlocks.PEARLWOOD_PLANKS.asItem())
                .build());
        ENCHANTED_WILLOW_TYPE = register(ENCHANTED_WILLOW_BOAT_KEY, new TerraformBoatType.Builder()
                .item(ShatteredItems.ENCHANTED_WILLOW_BOAT)
                .chestItem(ShatteredItems.ENCHANTED_WILLOW_CHEST_BOAT)
                .planks(ShatteredBlocks.ENCHANTED_WILLOW_PLANKS.asItem())
                .build());
    }
}

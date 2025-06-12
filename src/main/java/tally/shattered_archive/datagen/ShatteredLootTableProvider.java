package tally.shattered_archive.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import tally.shattered_archive.ShatteredArchive;
import tally.shattered_archive.items.ShatteredItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ShatteredLootTableProvider extends SimpleFabricLootTableProvider {
    public ShatteredLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.CHEST);
    }

    public static final RegistryKey<LootTable> PLANETOID_CHEST = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ShatteredArchive.MOD_ID, "chests/planetoid"));
    public static final RegistryKey<LootTable> ABYSS = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ShatteredArchive.MOD_ID, "chests/abyss_treasure"));
    public static final RegistryKey<LootTable> ABYSS_LAB = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ShatteredArchive.MOD_ID, "chests/abyss_lab"));

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(PLANETOID_CHEST, LootTable.builder()
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(ShatteredItems.THE_CORE_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.IN_THE_INBETWEEN_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.MYTHICAL_WORLDS_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.STARDUST_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.INSOMNIA_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.GLIMMERING_HOPE_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.AURORA_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.ANOMALOUS_CHILD_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.RIFT_MUSIC_DISC))
                )
        );
        lootTableBiConsumer.accept(ABYSS, LootTable.builder()
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0F))
                        .with(ItemEntry.builder(ShatteredItems.NIGHT_TERRORS_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.FLOW_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.TIDEBORN_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.BUBBLES_AND_PEARLS_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.DECAY_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.OMEN_MUSIC_DISC))

                )
        );
        lootTableBiConsumer.accept(ABYSS_LAB, LootTable.builder()
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(ShatteredItems.SECTOR_0_DISC))
                        .with(ItemEntry.builder(ShatteredItems.SILLINESS_OVERLOAD_MUSIC_DISC))
                        .with(ItemEntry.builder(ShatteredItems.SILLY_NOTE_MUSIC_DISC))
                )
        );
    }
}

package tally.shattered_archive.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import tally.shattered_archive.datagen.ShatteredItemTagGen;

public class LootTableMods {
    private static final Identifier WITCH_ID
            = Identifier.of("minecraft", "entities/witch");
    private static final Identifier WITHER_ID
            = Identifier.of("minecraft", "entities/wither_skeleton");
    private static final Identifier GUARDIAN_ID
            = Identifier.of("minecraft", "entities/guardian");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registry) -> {
            if(WITCH_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.ATTACKER, EntityPredicate.Builder.create().type(EntityTypeTags.SKELETONS)))
                        .with(TagEntry.expandBuilder(ShatteredItemTagGen.WITCH_DISCS));

                tableBuilder.pool(poolBuilder.build());
            }
            if(WITHER_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.ATTACKER, EntityPredicate.Builder.create().type(EntityType.PIGLIN)))
                        .with(TagEntry.expandBuilder(ShatteredItemTagGen.WITHER_DISCS));

                tableBuilder.pool(poolBuilder.build());
            }
            if(GUARDIAN_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.ATTACKER, EntityPredicate.Builder.create().type(EntityType.DROWNED)))
                        .with(TagEntry.expandBuilder(ShatteredItemTagGen.GUARDIAN_DISCS));

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
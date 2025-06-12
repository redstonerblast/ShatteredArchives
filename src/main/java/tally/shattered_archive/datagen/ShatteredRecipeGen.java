package tally.shattered_archive.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import tally.shattered_archive.blocks.ShatteredBlocks;
import tally.shattered_archive.items.ShatteredItems;

import java.util.concurrent.CompletableFuture;

public class ShatteredRecipeGen extends FabricRecipeProvider {
    public ShatteredRecipeGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        FabricRecipeProvider.offerSingleOutputShapelessRecipe(recipeExporter, Items.RED_DYE, ShatteredBlocks.SPIDER_LILY.asItem(), "red_dye");
        FabricRecipeProvider.offerSingleOutputShapelessRecipe(recipeExporter, Items.GREEN_DYE, ShatteredBlocks.FIDDLE_FERN.asItem(), "green_dye");
        FabricRecipeProvider.offerSingleOutputShapelessRecipe(recipeExporter, Items.PURPLE_DYE, ShatteredBlocks.DWARF_LAVENDER.asItem(), "purple_dye");
        FabricRecipeProvider.offerSingleOutputShapelessRecipe(recipeExporter, Items.LIGHT_BLUE_DYE, ShatteredBlocks.MANABLOOM.asItem(), "light_blue_dye");
        FabricRecipeProvider.offerSingleOutputShapelessRecipe(recipeExporter, Items.YELLOW_DYE, ShatteredBlocks.SUNDROP_FLOWER.asItem(), "yellow_dye");
        FabricRecipeProvider.offerSingleOutputShapelessRecipe(recipeExporter, Items.MAGENTA_DYE, ShatteredBlocks.MOONDROP_FLOWER.asItem(), "red_dye");

        FabricRecipeProvider.createChiseledBlockRecipe(RecipeCategory.BUILDING_BLOCKS, ShatteredBlocks.CHISELED_INK_SANDSTONE, Ingredient.ofItems(ShatteredBlocks.INK_SANDSTONE_SLAB)).criterion("has_ink_sand", conditionsFromTag(ShatteredItemTagGen.INK_SAND)).offerTo(recipeExporter);
        FabricRecipeProvider.createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ShatteredBlocks.CUT_INK_SANDSTONE_SLAB, Ingredient.ofItems(ShatteredBlocks.CHISELED_INK_SANDSTONE)).criterion("has_ink_sand", conditionsFromTag(ShatteredItemTagGen.INK_SAND)).offerTo(recipeExporter);
        FabricRecipeProvider.createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ShatteredBlocks.SMOOTH_INK_SANDSTONE_SLAB, Ingredient.ofItems(ShatteredBlocks.SMOOTH_INK_SANDSTONE)).criterion("has_ink_sand", conditionsFromTag(ShatteredItemTagGen.INK_SAND)).offerTo(recipeExporter);
        FabricRecipeProvider.createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ShatteredBlocks.INK_SANDSTONE_SLAB, Ingredient.ofItems(ShatteredBlocks.INK_SANDSTONE)).criterion("has_ink_sand", conditionsFromTag(ShatteredItemTagGen.INK_SAND)).offerTo(recipeExporter);
        FabricRecipeProvider.createStairsRecipe(ShatteredBlocks.INK_SANDSTONE_STAIRS, Ingredient.ofItems(ShatteredBlocks.INK_SANDSTONE)).criterion("has_ink_sand", conditionsFromTag(ShatteredItemTagGen.INK_SAND)).offerTo(recipeExporter);
        FabricRecipeProvider.createStairsRecipe(ShatteredBlocks.SMOOTH_INK_SANDSTONE_STAIRS, Ingredient.ofItems(ShatteredBlocks.SMOOTH_INK_SANDSTONE)).criterion("has_ink_sand", conditionsFromTag(ShatteredItemTagGen.INK_SAND)).offerTo(recipeExporter);
        FabricRecipeProvider.offer2x2CompactingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, ShatteredBlocks.INK_SANDSTONE, ShatteredBlocks.INK_SAND);
        FabricRecipeProvider.offerWallRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, ShatteredBlocks.INK_SANDSTONE_WALL, ShatteredBlocks.INK_SANDSTONE);

        FabricRecipeProvider.generateFamily(recipeExporter, ShatteredBlocks.PEARLWOOD_FAMILY, FeatureSet.of(FeatureFlags.VANILLA));
        offerPlanksRecipe(recipeExporter, ShatteredBlocks.PEARLWOOD_PLANKS, ShatteredItemTagGen.PEARLWOOD_LOGS, 4);

        FabricRecipeProvider.offerBoatRecipe(recipeExporter, ShatteredItems.PEARLWOOD_BOAT, ShatteredBlocks.PEARLWOOD_PLANKS);
        FabricRecipeProvider.offerChestBoatRecipe(recipeExporter, ShatteredItems.PEARLWOOD_CHEST_BOAT, ShatteredBlocks.PEARLWOOD_PLANKS);
        FabricRecipeProvider.offerBarkBlockRecipe(recipeExporter, ShatteredBlocks.PEARLWOOD_WOOD, ShatteredBlocks.PEARLWOOD_LOG);
        FabricRecipeProvider.offerBarkBlockRecipe(recipeExporter, ShatteredBlocks.STRIPPED_PEARLWOOD_WOOD, ShatteredBlocks.STRIPPED_PEARLWOOD_LOG);

        FabricRecipeProvider.generateFamily(recipeExporter, ShatteredBlocks.ENCHANTED_WILLOW_FAMILY, FeatureSet.of(FeatureFlags.VANILLA));
        offerPlanksRecipe(recipeExporter, ShatteredBlocks.ENCHANTED_WILLOW_PLANKS, ShatteredItemTagGen.ENCHANTED_WILLOW_LOGS, 4);

        FabricRecipeProvider.offerBoatRecipe(recipeExporter, ShatteredItems.ENCHANTED_WILLOW_BOAT, ShatteredBlocks.ENCHANTED_WILLOW_PLANKS);
        FabricRecipeProvider.offerChestBoatRecipe(recipeExporter, ShatteredItems.ENCHANTED_WILLOW_CHEST_BOAT, ShatteredBlocks.ENCHANTED_WILLOW_PLANKS);
        FabricRecipeProvider.offerBarkBlockRecipe(recipeExporter, ShatteredBlocks.ENCHANTED_WILLOW_WOOD, ShatteredBlocks.ENCHANTED_WILLOW_LOG);
        FabricRecipeProvider.offerBarkBlockRecipe(recipeExporter, ShatteredBlocks.STRIPPED_ENCHANTED_WILLOW_WOOD, ShatteredBlocks.STRIPPED_ENCHANTED_WILLOW_LOG);

        offerDroopingLeavesRecipe(recipeExporter, ShatteredBlocks.BLUE_ENCHANTED_WILLOW_DROOPING_LEAVES, ShatteredBlocks.BLUE_ENCHANTED_WILLOW_LEAVES);
        offerDroopingLeavesRecipe(recipeExporter, ShatteredBlocks.ENCHANTED_WILLOW_DROOPING_LEAVES, ShatteredBlocks.ENCHANTED_WILLOW_LEAVES);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ShatteredBlocks.SMOKE_STACK, 1)
                .input('#', Blocks.BASALT)
                .input('X', Ingredient.ofItems(Items.COAL, Items.CHARCOAL))
                .pattern("X")
                .pattern("#")
                .criterion("has_basalt", conditionsFromItem(Blocks.BASALT))
                .offerTo(recipeExporter);
    }

    public static void offerDroopingLeavesRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 2)
                .input('#', input)
                .pattern("#")
                .pattern("#")

                .group("drooping_leaves")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter);
    }
}

package com.hiimjustin000.colorblocks.data.provider;

import com.hiimjustin000.colorblocks.init.BlockInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ColorBlocksRecipeProvider extends FabricRecipeProvider
{
    public ColorBlocksRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockInit.COLOR_BLOCK)
                .input('#', Items.WHITE_CONCRETE)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .criterion(hasItem(Items.WHITE_CONCRETE), conditionsFromItem(Items.WHITE_CONCRETE))
                .offerTo(exporter);
    }
}

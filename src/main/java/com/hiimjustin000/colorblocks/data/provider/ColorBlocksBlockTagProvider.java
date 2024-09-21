package com.hiimjustin000.colorblocks.data.provider;

import com.hiimjustin000.colorblocks.init.BlockInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ColorBlocksBlockTagProvider extends FabricTagProvider.BlockTagProvider
{
    public ColorBlocksBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup)
    {
        super(output, registryLookup);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registryLookup)
    {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(BlockInit.COLOR_BLOCK).setReplace(false);
    }
}

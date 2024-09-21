package com.hiimjustin000.colorblocks.data.provider;

import com.hiimjustin000.colorblocks.init.BlockInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ColorBlocksBlockLootTableProvider extends FabricBlockLootTableProvider
{
    public ColorBlocksBlockLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup)
    {
        super(output, registryLookup);
    }

    @Override
    public void generate()
    {
        addDrop(BlockInit.COLOR_BLOCK);
    }
}

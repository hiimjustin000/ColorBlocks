package com.hiimjustin000.colorblocks;

import com.hiimjustin000.colorblocks.data.provider.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ColorBlocksDataGenerator implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ColorBlocksBlockLootTableProvider::new);
        pack.addProvider(ColorBlocksBlockTagProvider::new);
        pack.addProvider(ColorBlocksModelProvider::new);
        pack.addProvider(ColorBlocksRecipeProvider::new);
    }
}

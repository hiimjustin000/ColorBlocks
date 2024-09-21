package com.hiimjustin000.colorblocks.init;

import com.hiimjustin000.colorblocks.ColorBlocks;
import com.hiimjustin000.colorblocks.block.entity.ColorBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BlockEntityTypeInit
{
    public static final BlockEntityType<ColorBlockEntity> COLOR_BLOCK_ENTITY = register("color_block_entity",
            BlockEntityType.Builder.create(ColorBlockEntity::new, BlockInit.COLOR_BLOCK).build());

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> blockEntityType)
    {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, ColorBlocks.id(name), blockEntityType);
    }

    public static void init()
    {
    }
}

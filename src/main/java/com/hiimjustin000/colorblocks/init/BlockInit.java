package com.hiimjustin000.colorblocks.init;

import com.hiimjustin000.colorblocks.ColorBlocks;
import com.hiimjustin000.colorblocks.block.ColorBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BlockInit
{
    public static final ColorBlock COLOR_BLOCK = register("color_block", new ColorBlock());

    public static <T extends Block> T register(String name, T block)
    {
        return Registry.register(Registries.BLOCK, ColorBlocks.id(name), block);
    }

    public static void init()
    {
    }
}

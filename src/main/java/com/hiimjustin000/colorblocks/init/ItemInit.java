package com.hiimjustin000.colorblocks.init;

import com.hiimjustin000.colorblocks.ColorBlocks;
import com.hiimjustin000.colorblocks.item.ColorBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemInit
{
    public static final ColorBlockItem COLOR_BLOCK = register("color_block", new ColorBlockItem(BlockInit.COLOR_BLOCK));

    public static <T extends Item> T register(String name, T item)
    {
        return Registry.register(Registries.ITEM, ColorBlocks.id(name), item);
    }

    public static void init()
    {
    }
}

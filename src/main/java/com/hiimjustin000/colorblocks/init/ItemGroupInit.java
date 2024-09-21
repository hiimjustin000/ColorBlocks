package com.hiimjustin000.colorblocks.init;

import com.hiimjustin000.colorblocks.ColorBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;

public class ItemGroupInit
{
    public static final ItemGroup CSS_COLOR_BLOCKS = registerDefault("css_color_blocks", ColorBlocks.CSS);
    public static final ItemGroup DYE_COLOR_BLOCKS = registerDefault("dye_color_blocks", ColorBlocks.DYES);

    public static ItemGroup registerDefault(String name, Map<String, Integer> colors)
    {
        String id = "itemGroup." + ColorBlocks.MOD_ID + "." + name;
        return register(name, FabricItemGroup.builder()
                .displayName(Text.translatable(id))
                .icon(() -> {
                    ItemStack stack = new ItemStack(ItemInit.COLOR_BLOCK);
                    stack.set(ComponentTypeInit.COLORS, List.of(
                            0xFF0000, 0xFF2A00, 0xFF5500, 0xFF7F00, 0xFFAA00, 0xFFD400,
                            0xFFFF00, 0xD4FF00, 0xAAFF00, 0x7FFF00, 0x54FF00, 0x2AFF00,
                            0x00FF00, 0x00FF2A, 0x00FF55, 0x00FF7F, 0x00FFA9, 0x00FFD4,
                            0x00FFFF, 0x00D4FF, 0x00A9FF, 0x007FFF, 0x0055FF, 0x002AFF,
                            0x0000FF, 0x2A00FF, 0x5400FF, 0x7F00FF, 0xAA00FF, 0xD400FF,
                            0xFF00FF, 0xFF00D4, 0xFF00AA, 0xFF007F, 0xFF0054, 0xFF002A
                    ));
                    stack.set(ComponentTypeInit.SPEED, 20);
                    return stack;
                })
                .entries((displayContext, entries) -> colors.forEach((n, c) -> {
                    ItemStack stack = new ItemStack(ItemInit.COLOR_BLOCK);
                    stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(id + "." + n)
                            .fillStyle(Style.EMPTY.withItalic(false)));
                    stack.set(ComponentTypeInit.COLOR, c);
                    entries.add(stack);
                }))
                .build());
    }

    public static <T extends ItemGroup> T register(String name, T itemGroup)
    {
        return Registry.register(Registries.ITEM_GROUP, ColorBlocks.id(name), itemGroup);
    }

    public static void init()
    {
    }
}

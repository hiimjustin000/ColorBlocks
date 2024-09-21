package com.hiimjustin000.colorblocks.item;

import com.hiimjustin000.colorblocks.block.ColorBlock;
import com.hiimjustin000.colorblocks.init.ComponentTypeInit;
import com.hiimjustin000.colorblocks.init.ItemInit;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class ColorBlockItem extends BlockItem
{
    public ColorBlockItem(ColorBlock block)
    {
        super(block, new Item.Settings());
    }

    @Override
    public ItemStack getDefaultStack()
    {
        ItemStack stack = super.getDefaultStack();
        stack.set(ComponentTypeInit.COLOR, 16777215);
        stack.set(ComponentTypeInit.COLORS, new ArrayList<>());
        stack.set(ComponentTypeInit.SPEED, 1);
        return stack;
    }

    public static ItemStack withColor(int color)
    {
        ItemStack stack = new ItemStack(ItemInit.COLOR_BLOCK);
        stack.set(ComponentTypeInit.COLOR, color);
        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type)
    {
        List<Integer> colors = stack.getOrDefault(ComponentTypeInit.COLORS, new ArrayList<>());
        if (colors.isEmpty())
        {
            int color = stack.getOrDefault(ComponentTypeInit.COLOR, 16777215);
            tooltip.add(Text.literal(String.format("#%06X", color)).fillStyle(Style.EMPTY.withColor(color)));
        }

        int size = Math.min(colors.size(), 11);
        for (int i = 0; i < size; i++)
        {
            if (i == 10)
                tooltip.add(Text.translatable(getTranslationKey() + ".remaining", colors.size() - 10).formatted(Formatting.GRAY));
            else
            {
                int color = colors.get(i);
                tooltip.add(Text.literal(String.format("#%06X", color)).fillStyle(Style.EMPTY.withColor(color)));
            }
        }
    }
}

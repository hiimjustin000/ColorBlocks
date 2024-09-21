package com.hiimjustin000.colorblocks.handler;

import com.hiimjustin000.colorblocks.block.entity.ColorBlockEntity;
import com.hiimjustin000.colorblocks.init.BlockInit;
import com.hiimjustin000.colorblocks.init.ScreenHandlerTypeInit;
import com.hiimjustin000.colorblocks.item.ColorBlockItem;
import com.hiimjustin000.colorblocks.network.BlockPosPayload;
import com.hiimjustin000.colorblocks.network.ColorData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public class ColorBlockScreenHandler extends ScreenHandler
{
    private final Inventory colorsInventory = new SimpleInventory(100);
    private final Inventory colorInventory = new SimpleInventory(1);
    private final ColorBlockEntity entity;
    private final ScreenHandlerContext context;
    private int page = 0;
    private int color;
    private final List<Integer> colors;
    private int speed;

    public ColorBlockScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload)
    {
        this(syncId, playerInventory, (ColorBlockEntity)playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }

    public ColorBlockScreenHandler(int syncId, PlayerInventory playerInventory, ColorBlockEntity entity)
    {
        super(ScreenHandlerTypeInit.COLOR_BLOCK, syncId);
        this.entity = entity;
        this.context = ScreenHandlerContext.create(entity.getWorld(), entity.getPos());
        this.color = entity.getColor();
        this.colors = new ArrayList<>(entity.getColors());
        this.speed = entity.getSpeed();
        colorsInventory.onOpen(playerInventory.player);
        checkSize(colorsInventory, 100);
        colorInventory.onOpen(playerInventory.player);
        checkSize(colorInventory, 1);

        for (int row = 0; row < 10; row++)
        {
            for (int column = 0; column < 10; column++)
            {
                addSlot(new Slot(colorsInventory, column + row * 10, 8 + column * 18, 18 + row * 18));
            }
        }

        addSlot(new Slot(colorInventory, 0, 89, 200));

        updateColor();
        updateColors();
    }

    @Override
    public ScreenHandlerType<?> getType()
    {
        return super.getType();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return canUse(context, player, BlockInit.COLOR_BLOCK);
    }

    @Override
    public void onClosed(PlayerEntity player)
    {
        super.onClosed(player);
        colorsInventory.onClose(player);
        colorInventory.onClose(player);
    }

    public ColorData toData()
    {
        ColorData data = new ColorData();
        data.pos = entity.getPos();
        data.color = color;
        data.colors = colors;
        data.speed = speed;
        return data;
    }

    public ColorBlockEntity getEntity()
    {
        return entity;
    }

    public int getPage()
    {
        return page;
    }

    public int getMaxPage()
    {
        return (colors.isEmpty() ? 0 : colors.size() - 1) / 100;
    }

    public void onPrevPage()
    {
        if (page > 0)
        {
            page--;
            updateColors();
        }
    }

    public void onNextPage()
    {
        if (page < getMaxPage())
        {
            page++;
            updateColors();
        }
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
        updateColor();
    }

    public List<Integer> getColors()
    {
        return colors;
    }

    public void updateColor()
    {
        getSlot(100).setStack(ColorBlockItem.withColor(color));
    }

    public void updateColors()
    {
        int size = colors.size();
        for (int i = page * 100; i < page * 100 + 100; i++)
            getSlot(i - page * 100).setStack(i < size ? ColorBlockItem.withColor(colors.get(i)) : ItemStack.EMPTY);
    }

    public void removeColor(int slot)
    {
        if (slot < colors.size())
            colors.remove(slot);
        updateColors();
    }

    public void changeColor(int slot, int color)
    {
        if (slot >= colors.size())
            colors.add(color);
        else
            colors.set(slot, color);
        updateColors();
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public Inventory getColorInventory()
    {
        return colorInventory;
    }

    public Inventory getColorsInventory()
    {
        return colorsInventory;
    }
}

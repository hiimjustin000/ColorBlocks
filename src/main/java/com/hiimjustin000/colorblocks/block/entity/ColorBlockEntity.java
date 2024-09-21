package com.hiimjustin000.colorblocks.block.entity;

import com.hiimjustin000.colorblocks.ColorBlocks;
import com.hiimjustin000.colorblocks.handler.ColorBlockScreenHandler;
import com.hiimjustin000.colorblocks.init.BlockEntityTypeInit;
import com.hiimjustin000.colorblocks.init.ComponentTypeInit;
import com.hiimjustin000.colorblocks.network.BlockPosPayload;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosPayload>
{
    public static final Text TITLE = Text.translatable("container." + ColorBlocks.MOD_ID + ".color_block");

    private int color = 16777215;
    private List<Integer> colors = new ArrayList<>();
    private int speed = 1;
    private int step = 20;
    private int ticks = 0;
    private float tickDelta = 0f;
    private int frame = 0;

    public ColorBlockEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntityTypeInit.COLOR_BLOCK_ENTITY, pos, state);
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int col)
    {
        color = col;
        markDirty();
    }

    public int getDisplayColor()
    {
        return colors.isEmpty() ? color : colors.get(frame);
    }

    public List<Integer> getColors()
    {
        return colors;
    }

    public void setColors(List<Integer> cols)
    {
        if (!colors.equals(cols))
            frame = 0;
        colors = cols;
        markDirty();
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int s)
    {
        speed = s;
        step = Math.round(20f / speed);
        markDirty();
    }

    public void checkTick(float td)
    {
        if (tickDelta > td)
            tick();
        tickDelta = td;
    }

    public void tick()
    {
        ticks++;
        if (ticks >= 420) // LCM of 1, 2, 3, 4, 5, 7, 10, 20 (All possible step values)
            ticks = 0;

        if (ticks % step == 0)
        {
            frame++;
            if (frame >= colors.size())
                frame = 0;
        }
    }

    @Override
    protected void readComponents(ComponentsAccess components)
    {
        color = MathHelper.clamp(components.getOrDefault(ComponentTypeInit.COLOR, 16777215), 0, 16777215);
        colors = components.getOrDefault(ComponentTypeInit.COLORS, new ArrayList<>());
        speed = MathHelper.clamp(components.getOrDefault(ComponentTypeInit.SPEED, 1), 1, 20);
        step = Math.round(20f / speed);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        nbt.putInt("color", color);
        nbt.putIntArray("colors", colors);
        nbt.putInt("speed", speed);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        color = nbt.contains("color") ? nbt.getInt("color") : 16777215;
        colors = Arrays.stream(nbt.getIntArray("colors")).collect(ArrayList::new, List::add, List::addAll);
        speed = nbt.contains("speed") ? nbt.getInt("speed") : 1;
        step = Math.round(20f / speed);
    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity player)
    {
        return new BlockPosPayload(pos);
    }

    @Override
    public Text getDisplayName()
    {
        return TITLE;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player)
    {
        return new ColorBlockScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup)
    {
        return createNbt(registryLookup);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket()
    {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}

package com.hiimjustin000.colorblocks.network;

import com.hiimjustin000.colorblocks.block.entity.ColorBlockEntity;
import com.hiimjustin000.colorblocks.init.BlockEntityTypeInit;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ColorData
{
    public static PacketCodec<ByteBuf, ColorData> PACKET_CODEC = new PacketCodec<>()
    {
        public ColorData decode(ByteBuf buf)
        {
            ColorData data = new ColorData();
            data.pos = BlockPos.fromLong(buf.readLong());
            data.color = buf.readInt();
            int size = buf.readInt();
            data.colors = new ArrayList<>();
            for (int i = 0; i < size; i++)
                data.colors.add(buf.readInt());
            data.speed = buf.readInt();
            return data;
        }

        public void encode(ByteBuf buf, ColorData data)
        {
            buf.writeLong(data.pos.asLong());
            buf.writeInt(data.color);
            buf.writeInt(data.colors.size());
            for (int color : data.colors)
                buf.writeInt(color);
            buf.writeInt(data.speed);
        }
    };

    public BlockPos pos;
    public int color;
    public List<Integer> colors;
    public int speed;

    public void saveToEntity(World world)
    {
        world.getBlockEntity(pos, BlockEntityTypeInit.COLOR_BLOCK_ENTITY).ifPresent(entity -> {
            entity.setColor(color);
            entity.setColors(colors);
            entity.setSpeed(speed);
        });
    }

    public void saveToEntity(ColorBlockEntity entity)
    {
        entity.setColor(color);
        entity.setColors(colors);
        entity.setSpeed(speed);
    }
}
